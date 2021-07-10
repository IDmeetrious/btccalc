package github.idmeetrious.btccalc.application

import dagger.Module
import dagger.Provides
import github.idmeetrious.btccalc.data.RepositoryImpl
import github.idmeetrious.btccalc.domain.Repository
import github.idmeetrious.btccalc.network.AuthRequest
import javax.inject.Singleton

@Module
class RepositoryModule {
    @[Singleton Provides]
    fun provideRepository(api: AuthRequest): Repository = RepositoryImpl(api)
}