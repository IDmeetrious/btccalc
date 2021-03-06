package github.idmeetrious.btccalc.data

import github.idmeetrious.btccalc.domain.Repository
import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import github.idmeetrious.btccalc.network.AuthRequest
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(private val api: AuthRequest): Repository {
    override fun getCurrency(id: String, convert: String): Single<List<Currency>> =
        api.getCurrency(ids = id, convert = convert)

    override fun getExchangeRates(): Single<List<Exchange>> =
        api.getExchangeRates()
}