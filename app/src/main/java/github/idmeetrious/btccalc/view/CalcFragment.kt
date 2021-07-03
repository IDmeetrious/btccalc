package github.idmeetrious.btccalc.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import github.idmeetrious.btccalc.databinding.FragmentCalcBinding
import github.idmeetrious.btccalc.domain.model.Exchange
import github.idmeetrious.btccalc.viewmodel.CalcViewModel
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import layout.CurrencyDialogFragment
import java.text.SimpleDateFormat
import kotlin.math.floor

private const val TAG = "CalcFragment"

class CalcFragment : Fragment() {
    private var currencyDialog: CurrencyDialogFragment? = null

    private val viewModel: CalcViewModel by activityViewModels()
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!

    private val defaultScope = CoroutineScope(Dispatchers.Default + Job())
    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    

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
                        mainScope.launch {
                            setSelection(it)
                        }
                    }
                } else hint = "0"
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i(TAG, "--> beforeTextChanged: $s")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i(TAG, "--> onTextChanged: $s")

                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i(TAG, "--> afterTextChanged: $s")
                    if (s != null) {
                        if (s.length > 1 && s[0].toString() == "0" && s[1].toString() != ".") {
                            setText(s[1].toString())
                            setSelection(s[1].toString().length)
                        }
                    }
                }
            })
            addTextChangedListener { et ->
                viewModel.currencyRate.value?.price?.let { price ->
                    et?.let {
                        if (it.isNotEmpty() && !it.endsWith("0.", true)) {
                            if (!(it.length == 1 && it.endsWith("0"))) {
                                val value = floor("$et".toDouble() * price)
                                Log.i(TAG, "--> onViewCreated: $value")
                                binding.calcToCurrencyEt.setText("$value")
                            }
                        } else binding.calcToCurrencyEt.text?.clear()
                    }
                }
            }
        }

        mainScope.launch {
            viewModel.exchangeRates.collect { list ->

                if (list.isNotEmpty()) {
                    Log.i(TAG, "--> onViewCreated: ${list.size}")
                    binding.calcInfoDateTv.text = today()
                    binding.calcInfoBtcRateTv.text = formatRate(list.first {
                        it.currency == getFromBtnSuffix()
                    }) + " ${getToBtnSuffix()}"
                    binding.calcInfoUsdRateTv.text = formatRuble(list.first {
                        it.currency == "RUB"
                    })
                }
            }
        }

        binding.zeroBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused) {

                binding.calcToCurrencyEt.text?.apply {
                    if (!startsWith("0")) append("0")
                    else if (startsWith("0.")) append("0")

                }
            } else if (binding.calcFromCurrencyEt.isFocused) {
                binding.calcFromCurrencyEt.text?.apply {
                    if (!startsWith("0")) append("0")
                    else if (startsWith("0.")) append("0")

                }
            }
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
            if (binding.calcToCurrencyEt.isFocused) {

                binding.calcToCurrencyEt.text?.apply {
                    if (!startsWith("0")) append("00")
                    else if (startsWith("0.")) append("00")
                }
            } else if (binding.calcFromCurrencyEt.isFocused) {
                binding.calcFromCurrencyEt.text?.apply {
                    if (!startsWith("0")) append("00")
                    else if (startsWith("0.")) append("00")
                }
            }
        }
        binding.comaBtn.setOnClickListener {
            if (binding.calcToCurrencyEt.isFocused) {
                binding.calcToCurrencyEt.text?.apply {
                    if (isNotEmpty() && !contains(".")) append(".")
                    else if (isEmpty()) append("0.")
                }
            } else if (binding.calcFromCurrencyEt.isFocused) {
                binding.calcFromCurrencyEt.text?.apply {
                    if (isNotEmpty() && !contains(".")) append(".")
                    else if (isEmpty()) append("0.")
                }
            }
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
                val etText = binding.calcFromCurrencyEt.text
                if (!etText.isNullOrEmpty()) {
                    val lastLetter = etText.dropLast(1)
                    if (lastLetter.length > 0 && lastLetter != "" && lastLetter.isNotEmpty()) {
                        binding.calcFromCurrencyEt.setText(lastLetter)
                        binding.calcFromCurrencyEt.setSelection(lastLetter.length)
                    } else {
                        binding.calcFromCurrencyEt.text?.clear()
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
        binding.calcFromBtn.apply {
            setOnClickListener {
                currencyDialog?.show(childFragmentManager, "CurrencyDialogFragment")
                viewModel.emitToBtn(false)
                viewModel.emitFromBtn(true)
            }
        }
        binding.calcToBtn.apply {
            setOnClickListener {
                currencyDialog?.show(childFragmentManager, "CurrencyDialogFragment")
                viewModel.emitFromBtn(false)
                viewModel.emitToBtn(true)
            }
        }
        mainScope.launch {
            viewModel.exchange.collect {
                it?.let {
                    if (viewModel.fromBtnFlow.value){
                        binding.calcFromBtn.text = it.currency
                    } else if (viewModel.toBtnFlow.value)
                        binding.calcToBtn.text = it.currency
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "--> onPause: ")
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "--> onResume: ")
    }
    
    private fun formatRate(exch: Exchange): String {
        return "1 ${exch.currency} = ${exch.rate}"
    }

    private fun formatRuble(exch: Exchange): String {
        val ruble = (1 / exch.rate.toDouble()).toString()
        return "1 USD = $ruble RUB"
    }

    private fun getFromBtnSuffix(): String {
        var suffix = ""
        binding.calcFromBtn.text.let {
            suffix = it.subSequence(it.length - 3, it.length) as String
        }
        return suffix
    }

    private fun getToBtnSuffix(): String {
        var suffix = ""
        binding.calcToBtn.text.let {
            suffix = it.subSequence(it.length - 3, it.length) as String
        }
        return suffix
    }

    @SuppressLint("SimpleDateFormat")
    private fun today(): String {
        val sdf = SimpleDateFormat("dd-MMM-yyyy, hh:mm:ss")
        return sdf.format(System.currentTimeMillis())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        defaultScope.cancel()
        mainScope.cancel()
    }
}