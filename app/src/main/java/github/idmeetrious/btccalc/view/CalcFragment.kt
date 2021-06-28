package github.idmeetrious.btccalc.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.idmeetrious.btccalc.databinding.FragmentCalcBinding
import github.idmeetrious.btccalc.viewmodel.CalcViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

private const val TAG = "CalcFragment"
class CalcFragment: Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(CalcViewModel::class.java)
    }
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!

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
        binding.calcUsdEt.showSoftInputOnFocus = false
        binding.calcRubEt.showSoftInputOnFocus = false
        binding.calcBtcEt.showSoftInputOnFocus = false

        // Fetch Btc rate from view model

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.rubRate.collect{
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
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("0")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("0")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("0")
        }
        binding.oneBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("1")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("1")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("1")
        }
        binding.twoBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("2")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("2")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("2")
        }
        binding.threeBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("3")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("3")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("3")
        }
        binding.fourBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("4")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("4")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("4")
        }
        binding.fiveBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("5")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("5")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("5")
        }
        binding.sixBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("6")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("6")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("6")
        }
        binding.sevenBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("7")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("7")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("7")
        }
        binding.eightBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("8")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("8")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("8")
        }
        binding.nineBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("9")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("9")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("9")
        }
        binding.doubleZeroBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append("00")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append("00")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append("00")
        }
        binding.comaBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.append(".")
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.append(".")
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.append(".")
        }
        binding.clearBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.text?.clear()
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.text?.clear()
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.text?.clear()
        }
        binding.backSpaceBtn.setOnClickListener {
            if (binding.calcUsdEt.isFocused)
                binding.calcUsdEt.setText(binding.calcUsdEt.text?.dropLast(1))
            else if (binding.calcRubEt.isFocused)
                binding.calcRubEt.setText(binding.calcRubEt.text?.dropLast(1))
            else if (binding.calcBtcEt.isFocused)
                binding.calcBtcEt.setText(binding.calcBtcEt.text?.dropLast(1))
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