package github.idmeetrious.btccalc.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.idmeetrious.btccalc.databinding.FragmentCalcBinding
import github.idmeetrious.btccalc.viewmodel.CalcViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import layout.CurrencyDialogFragment

private const val TAG = "CalcFragment"

class CalcFragment : Fragment() {
    private var currencyDialog: CurrencyDialogFragment? = null
    private val viewModel by lazy {
        ViewModelProvider(this).get(CalcViewModel::class.java)
    }
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (currencyDialog == null) currencyDialog = CurrencyDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Turn of showing keyboard
        binding.calcToCurrencyEt.apply {
            showSoftInputOnFocus = false
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    hint = ""
                    text?.length?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            setSelection(it)
                        }
                    }
                } else hint = "0"
            }
        }

        binding.calcFromCurrencyEt.apply {
            showSoftInputOnFocus = false
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    hint = ""
                    text?.length?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            setSelection(it)
                        }
                    }
                } else hint = "0"
            }
        }

        // Fetch Btc rate from view model

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.rubRate.collect {
                it?.rates?.value?.let { double ->
                    Log.i(TAG, "--> onViewCreated: ${double}")
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.btcRate.collect {
                it?.let {
                    binding.calcInfoDateTv.text = "${it.time}"
                }
                it?.rate?.let { double ->
                    binding.calcInfoBtcRateTv.text = "1 BTC = $double USD, "
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.usdRate.collect {
                it?.rates?.value.let { double ->
                    Log.i(TAG, "--> onViewCreated: $double")
                    binding.calcInfoUsdRateTv.text = "1 USD = ${double} RUB"
                }
            }
        }

        binding.zeroBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("0")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("0")
        }
        binding.oneBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("1")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("1")
        }
        binding.twoBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("2")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("2")
        }
        binding.threeBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("3")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("3")
        }
        binding.fourBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("4")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("4")
        }
        binding.fiveBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("5")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("5")
        }
        binding.sixBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("6")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("6")
        }
        binding.sevenBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("7")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("7")
        }
        binding.eightBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("8")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("8")
        }
        binding.nineBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("9")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("9")
        }
        binding.doubleZeroBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append("00")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append("00")
        }
        binding.comaBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.append(".")
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.append(".")
        }
        binding.clearBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused)
                binding.calcToCurrencyEt.text?.clear()
            else if (binding.calcFromCurrencyEt.isFocused)
                binding.calcFromCurrencyEt.text?.clear()
        }
        binding.backSpaceBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused) {
                binding.calcToCurrencyEt.setText(binding.calcToCurrencyEt.text?.dropLast(1))
                binding.calcToCurrencyEt.let { et ->
                    et.text?.let {
                        et.setSelection(it.length)
                    }
                }
            } else if (binding.calcFromCurrencyEt.isFocused) {
                binding.calcFromCurrencyEt.setText(binding.calcFromCurrencyEt.text?.dropLast(1))
                binding.calcFromCurrencyEt.let { et ->
                    et.text?.let {
                        et.setSelection(it.length)
                    }
                }
            }
        }
        binding.swapCurrencyBtn.setOnClickListener {
            /** Created by ID
             * date: 28-Jun-21, 2:22 PM
             * TODO: swap usd and btc views
             */
        }
        binding.calcDialogBtn.setOnClickListener {
            /** Created by ID
             * date: 28-Jun-21, 2:22 PM
             * TODO: show calculator dialog
             */
        }
        binding.calcUsdBtn.setOnClickListener {
            currencyDialog?.show(childFragmentManager, "CurrencyDialogFragment")
        }
        binding.calcBtcBtn.setOnClickListener {
            currencyDialog?.show(childFragmentManager, "CurrencyDialogFragment")
        }


    }

//    override fun onStart() {
//        super.onStart()
//        viewModel.getBtcToUsd()
//        viewModel.getRubToUsd()
//        viewModel.getUsdToRub()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}