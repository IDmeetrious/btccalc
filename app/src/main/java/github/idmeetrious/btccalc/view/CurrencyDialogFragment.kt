package layout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import github.idmeetrious.btccalc.R
import github.idmeetrious.btccalc.databinding.FragmentDialogCurrencyBinding
import github.idmeetrious.btccalc.view.CurrencyAdapter
import github.idmeetrious.btccalc.viewmodel.CalcViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

private const val TAG = "CurrencyDialogFragment"

class CurrencyDialogFragment : DialogFragment() {

    private val viewModel: CalcViewModel by activityViewModels()
    private val defaultScope = CoroutineScope(Dispatchers.Default)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    private var _binding: FragmentDialogCurrencyBinding? = null
    private val binding get() = _binding!!

    private var tabLayout: TabLayout? = null
    private var rv: RecyclerView? = null
    private var adapter: CurrencyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Widget_DialogTheme)
        adapter = CurrencyAdapter(emptyList<Any>())
        viewModel.getExchangeRates()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogCurrencyBinding.inflate(inflater, container, false)
        tabLayout = binding.currencyDialogTl
        rv = binding.currencyDialogRv
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exchangeRates.value.let {
            adapter?.updateList(it)
        }

        job = mainScope.launch {
            viewModel.exchangeRates.collect {
                adapter?.updateList(it)
            }
        }

//        defaultScope.launch {
//            adapter?.currency?.collect {
//                it?.let {
//                    viewModel.emitCurrency(it)
//                    Log.i(TAG, "--> onViewCreated: emitCurrency(${it.id})")
//                    dismiss()
//                }
//            }
//        }
        job = defaultScope.launch {
            adapter?.exchange?.collect {
                it?.let {
                    viewModel.emitExchange(it)
                    Log.i(TAG, "--> onViewCreated: emitExchange(${it.currency})")
                    dismiss()
                }
            }
            Log.i(TAG, "--> onViewCreated: defaultScope: Running")
        }

        tabLayout?.let {
            var selectedTab = "Currency"
            binding.currencyDialogEditBtn.setOnClickListener {
                Toast.makeText(requireContext(), "Edit $selectedTab list", Toast.LENGTH_SHORT)
                    .show()
            }
            it.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.i(TAG, "--> onTabSelected: ${tab?.text}")
                    tab?.text.let {
                        selectedTab = it as String
                    }
                    when (selectedTab) {
                        "Currency" -> {
                            job = mainScope.launch {
                                viewModel.exchangeRates.collect {
                                    Log.i(TAG, "--> onTabSelected: ${it.size}")
                                    adapter?.updateList(it)
                                }
                            }
                        }
                        "Crypto currency" -> {
//                            CoroutineScope(Dispatchers.IO).launch {
//                                viewModel.exchangeRates.collect {
//                                    adapter?.updateList(it)
//                                }
//                            }
                            Log.i(TAG, "--> onTabSelected: ")
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.i(TAG, "--> onTabUnselected: ")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.i(TAG, "--> onTabReselected: ")
                }
            })
        }

        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.setHasFixedSize(true)
        rv?.adapter = adapter



        binding.currencyDialogCancelBtn.setOnClickListener {
            dismiss()
        }


    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.apply {
                window?.setLayout(width, height)
            }
        }
        Log.i(TAG, "--> onStart: defaultScope.isActive(${defaultScope.isActive})")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "--> onResume: defaultScope.isActive(${defaultScope.isActive})")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "--> onStop: defaultScope.isActive(${defaultScope.isActive})")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "--> onDestroyView: ")
        _binding = null
        job?.cancel()
    }
}