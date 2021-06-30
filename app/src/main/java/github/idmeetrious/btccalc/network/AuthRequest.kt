package github.idmeetrious.btccalc.network

import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthRequest {
    @GET("/v1/currencies/ticker")
    fun getCurrency(
        @Query("key") apiKey: String = "41a4f1763afe4f54231c1b1548844b413809bed7",
        @Query("ids") ids: String = "BTC",
        @Query("interval") interval: String = "1d",
        @Query("convert") convert: String = "USD"
    ): Single<List<Currency>>

    @GET("/v1/exchange-rates")
    fun getExchangeRates(
        @Query("key") apiKey: String = "41a4f1763afe4f54231c1b1548844b413809bed7"
    ): Single<List<Exchange>>
}