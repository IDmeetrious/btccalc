package github.idmeetrious.btccalc.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
class AppModule(private val context: Context) {

    @[Singleton Provides]
    fun provideApplicationContext() = context
}