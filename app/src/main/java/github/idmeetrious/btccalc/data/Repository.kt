package github.idmeetrious.btccalc.data

import github.idmeetrious.btccalc.domain.model.Btc
import github.idmeetrious.btccalc.domain.model.Rub
import github.idmeetrious.btccalc.domain.model.Usd
import github.idmeetrious.btccalc.network.ApiClient
import io.reactivex.rxjava3.core.Single

class Repository {
    private val coinApi = ApiClient().coinApi
    private val exchApi = ApiClient().exchApi

    fun getBtcToUsd(): Single<Btc> = coinApi.btcToUsd()
    fun getBtcToRub(): Single<Btc> = coinApi.btcToRub()
    fun getUsdToRub(): Single<Usd> = exchApi.usdToRub()
    fun getRubToUsd(): Single<Rub> = exchApi.rubToUsd()
}