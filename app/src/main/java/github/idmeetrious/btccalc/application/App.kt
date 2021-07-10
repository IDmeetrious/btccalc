package github.idmeetrious.btccalc.application

import android.app.Application
import javax.inject.Singleton

@Singleton
class App: Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }
}