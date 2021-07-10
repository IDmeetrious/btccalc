package github.idmeetrious.btccalc.application

import dagger.Module
import dagger.Provides
import github.idmeetrious.btccalc.network.AuthRequest
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @[Singleton Provides]
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nomics.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @[Singleton Provides]
    fun provideCoinApi(retrofit: Retrofit): AuthRequest = retrofit.create(AuthRequest::class.java)
}