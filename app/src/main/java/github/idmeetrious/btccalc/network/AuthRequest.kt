package github.idmeetrious.btccalc.network

import github.idmeetrious.btccalc.domain.model.Btc
import github.idmeetrious.btccalc.domain.model.Rub
import github.idmeetrious.btccalc.domain.model.Usd
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AuthRequest {
    @Headers("X-CoinAPI-Key: 8A18F7F7-28FC-4FBF-9479-922B43B5A748")
    @GET("/v1/exchangerate/BTC/USD")
    fun btcToUsd(): Single<Btc>

    @Headers("X-CoinAPI-Key: 8A18F7F7-28FC-4FBF-9479-922B43B5A748")
    @GET("/v1/exchangerate/BTC/RUB")
    fun btcToRub(): Single<Btc>

    @GET("/latest")
    fun usdToRub(
//        @Query("access_key") key: String = "08d3ecaa80c855861b016b0d5ef5790f",
        @Query("base") base: String = "USD",
        @Query("symbols") quote: String = "RUB"
    ): Single<Usd>

    @GET("/latest")
    fun rubToUsd(
//        @Query("access_key") key: String = "08d3ecaa80c855861b016b0d5ef5790f",
        @Query("base") base: String = "RUB",
        @Query("symbols") quote: String = "USD"
    ): Single<Rub>
}