package layout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayout
import github.idmeetrious.btccalc.R
import github.idmeetrious.btccalc.databinding.FragmentDialogCurrencyBinding

private const val TAG = "CurrencyDialogFragment"

class CurrencyDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogCurrencyBinding? = null
    private val binding get() = _binding!!

    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Widget_DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogCurrencyBinding.inflate(inflater, container, false)
        tabLayout = binding.currencyDialogTl
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout?.let {
            var selectedTab = "Currency"
            binding.currencyDialogEditBtn.setOnClickListener {
                Toast.makeText(requireContext(), "Edit $selectedTab list", Toast.LENGTH_SHORT)
                    .show()
            }
            it.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.text.let {
                        selectedTab = it as String
                    }
                    Log.i(TAG, "--> onTabSelected: ${tab?.text}")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.i(TAG, "--> onTabUnselected: ")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.i(TAG, "--> onTabReselected: ")
                }
            })
        }
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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}