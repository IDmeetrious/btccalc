package github.idmeetrious.btccalc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import github.idmeetrious.btccalc.data.Repository
import github.idmeetrious.btccalc.domain.model.Btc
import github.idmeetrious.btccalc.domain.model.Rub
import github.idmeetrious.btccalc.domain.model.Usd
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "CalcViewModel"

class CalcViewModel : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var repository: Repository? = null
    private var disposable: Disposable? = null

    init {
        if (repository == null) repository = Repository()
        getUsdToRub()
        getRubToUsd()
        getBtcToUsd()
//        getBtcToRub()
    }

    private var _btcRate: MutableStateFlow<Btc?> = MutableStateFlow(null)
    val btcRate: StateFlow<Btc?> = _btcRate

    private var _usdRate: MutableStateFlow<Usd?> = MutableStateFlow(null)
    val usdRate: StateFlow<Usd?> = _usdRate

    private var _rubRate: MutableStateFlow<Rub?> = MutableStateFlow(null)
    val rubRate: StateFlow<Rub?> = _rubRate
    var rubFlow: Flow<Rub>? = null

    fun getBtcToUsd() {
        disposable = repository?.getBtcToUsd()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc ->
                btc?.let {
                    ioScope.launch {
                        _btcRate.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getBtcToUsd: ${it.message}")
            })
    }

    fun getBtcToRub() {
        disposable = repository?.getBtcToRub()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc ->
                btc?.let {
                    ioScope.launch {
                        _btcRate.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getBtcToUsd: ${it.message}")
            })
    }

    fun getUsdToRub() {
        disposable = repository?.getUsdToRub()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc ->
                btc?.let {
                    Log.i(TAG, "--> getUsdToRub: $it")
                    ioScope.launch {
                        _usdRate.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getBtcToUsd: ${it.message}")
            })
    }

    fun getRubToUsd() {
        disposable = repository?.getRubToUsd()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc ->
                btc?.let {
                    Log.i(TAG, "--> getRubToUsd: $it")
                    ioScope.launch {
                        _rubRate.emit(it)
                    }
                    rubFlow = flowOf(it)
                        .flowOn(Dispatchers.IO)
                }
            }, {
                Log.e(TAG, "--> getBtcToUsd: ${it.message}")
            })
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}