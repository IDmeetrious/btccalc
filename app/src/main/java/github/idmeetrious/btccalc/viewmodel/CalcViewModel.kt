package github.idmeetrious.btccalc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.btccalc.application.App
import github.idmeetrious.btccalc.domain.Repository
import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val TAG = "CalcViewModel"

class CalcViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private var disposable: Disposable? = null

    private var _currencyRate: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currencyRate get() = _currencyRate

    private var _exchangeRates: MutableStateFlow<List<Exchange>> = MutableStateFlow(emptyList())
    val exchangeRates get() = _exchangeRates

    private var _currency: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currency get() = _currency

    private var _exchange: MutableStateFlow<Exchange?> = MutableStateFlow(null)
    val exchange get() = _exchange

    private var _fromBtnFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val fromBtnFlow get() = _fromBtnFlow

    private var _toBtnFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val toBtnFlow get() = _toBtnFlow

    init {
        App.appComponent.inject(this)
        getExchangeRates()
    }

    fun getCurrency(id: String = "BTC", convert: String = "USD") {
        disposable = repository?.getCurrency(id, convert)
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ currency ->
                currency?.let {
                    viewModelScope.launch {
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
                    viewModelScope.launch {
                        Log.i(TAG, "--> getExchangeRates: ${it.size}")
                        _exchangeRates.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getExchangeRates: ${it.message}")
            })
    }

    fun emitCurrency(curr: Currency){
        viewModelScope.launch {
            _currency.emit(curr)
        }
    }

    fun emitExchange(exch: Exchange){
        viewModelScope.launch {
            _exchange.value = exch
        }
    }

    fun emitFromBtn(state: Boolean){
        viewModelScope.launch {
            _fromBtnFlow.value = state
        }
    }
    fun emitToBtn(state: Boolean){
        viewModelScope.launch {
            _toBtnFlow.value = state
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}