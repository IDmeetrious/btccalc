package github.idmeetrious.btccalc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import github.idmeetrious.btccalc.data.Repository
import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import io.reactivex.rxjava3.core.Single
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
        getExchangeRates()
    }

    private var _currencyRate: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currencyRate get() = _currencyRate

    private var _exchangeRates: MutableStateFlow<List<Exchange>> = MutableStateFlow(emptyList())
    val exchangeRates get() = _exchangeRates

    private var _currency: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currency get() = _currency

//    private var _exchange: MutableStateFlow<Exchange?> = MutableStateFlow(null)
//    val exchange get() = _exchange
    private var _exchange: Single<Exchange>? = null
    val exchange get() = _exchange



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
            ?.subscribe({ list ->
                list?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.i(TAG, "--> getExchangeRates: ${it.size}")
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

    fun emitCurrency(curr: Currency){
        CoroutineScope(Dispatchers.IO).launch {
            _currency.emit(curr)
        }
    }

//    fun emitExchange(exch: Exchange){
//        CoroutineScope(Dispatchers.IO).launch {
//            _exchange.emit(exch)
//        }
//    }
    fun emitExchange(exch: Exchange){
//        CoroutineScope(Dispatchers.IO).launch {
//            _exchange.emit(exch)
//        }
        _exchange = Single.just(exch)
            .subscribeOn(Schedulers.io())
    }
}