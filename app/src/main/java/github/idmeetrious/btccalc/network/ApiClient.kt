package github.idmeetrious.btccalc.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val coin = Retrofit.Builder()
        .baseUrl("https://rest.coinapi.io")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val exchange = Retrofit.Builder()
        .baseUrl("https://api.exchangerate.host")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val coinApi: AuthRequest = coin.create(AuthRequest::class.java)
    val exchApi: AuthRequest = exchange.create(AuthRequest::class.java)

}