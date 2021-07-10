package github.idmeetrious.btccalc.application

import dagger.Component
import github.idmeetrious.btccalc.viewmodel.CalcViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(calcViewModel: CalcViewModel)
}