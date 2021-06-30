package github.idmeetrious.btccalc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import github.idmeetrious.btccalc.data.Repository
import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "CalcViewModel"

class CalcViewModel : ViewModel() {
    private var repository: Repository? = null
    private var disposable: Disposable? = null

    init {
        if (repository == null) repository = Repository()
        getCurrency()
    }

    private var _currencyRate: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currencyRate get() = _currencyRate

    private var _exchangeRates: MutableStateFlow<List<Exchange>> = MutableStateFlow(emptyList())
    val exchangeRates get() = _exchangeRates

    fun getCurrency(id: String = "BTC", convert: String = "USD") {
        disposable = repository?.getCurrency(id, convert)
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ currency ->
                currency?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        _currencyRate.emit(it[0])
                    }
                }
            }, {
                Log.e(TAG, "--> getCurrency: ${it.message}")
            })
    }

    fun getExchangeRates() {
        disposable = repository?.getExchangeRates()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc ->
                btc?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        _exchangeRates.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getExchangeRates: ${it.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}