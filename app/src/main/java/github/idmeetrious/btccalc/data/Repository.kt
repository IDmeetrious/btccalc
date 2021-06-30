package github.idmeetrious.btccalc.data

import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import github.idmeetrious.btccalc.network.ApiClient
import io.reactivex.rxjava3.core.Single

class Repository {
    private val api = ApiClient().coinApi

    fun getCurrency(id: String, convert: String): Single<List<Currency>> =
        api.getCurrency(ids = id, convert = convert)
    fun getExchangeRates(): Single<List<Exchange>> = api.getExchangeRates()
}