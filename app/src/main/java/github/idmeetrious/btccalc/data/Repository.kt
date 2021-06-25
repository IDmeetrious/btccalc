package github.idmeetrious.btccalc.data

import github.idmeetrious.btccalc.domain.model.Btc
import github.idmeetrious.btccalc.network.ApiClient
import io.reactivex.rxjava3.core.Single

class Repository {
    private val api = ApiClient().api

    fun getBtcToUsd(): Single<Btc> = api.btcToUsd()
}