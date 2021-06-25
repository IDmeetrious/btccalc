package github.idmeetrious.btccalc.network

import github.idmeetrious.btccalc.domain.model.Btc
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface AuthRequest {
    @Headers("X-CoinAPI-Key: 8A18F7F7-28FC-4FBF-9479-922B43B5A748")
    @GET("/v1/exchangerate/BTC/USD")
    fun btcToUsd(): Single<Btc>
}