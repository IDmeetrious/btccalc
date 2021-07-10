package github.idmeetrious.btccalc.domain

import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getCurrency(id: String, convert: String): Single<List<Currency>>
    fun getExchangeRates(): Single<List<Exchange>>
}