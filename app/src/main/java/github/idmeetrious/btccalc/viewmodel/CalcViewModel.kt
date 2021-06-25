package github.idmeetrious.btccalc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import github.idmeetrious.btccalc.data.Repository
import github.idmeetrious.btccalc.domain.model.Btc
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "CalcViewModel"
class CalcViewModel: ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var repository: Repository? = null
    private var disposable: Disposable? = null

    init {
        if (repository == null) repository = Repository()
    }

    private var _btcRate: MutableStateFlow<Btc?> = MutableStateFlow(null)
    val btcRate: StateFlow<Btc?> = _btcRate

    fun getBtcToUsd(){
        disposable = repository?.getBtcToUsd()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ btc->
                       btc?.let {
                           ioScope.launch {
                               _btcRate.emit(it)
                           }
                       }
            },{
                Log.e(TAG, "--> getBtcToUsd: ${it.message}")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}