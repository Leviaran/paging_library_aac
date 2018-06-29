package ran.singletondev.testandroid.di

import dagger.Module
import dagger.Provides
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.util.DefaultServiceLocator

/**
 * Created by ran on 6/29/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Module
class ServiceLocatorModule {
    @Provides
    fun providesDefaultServiceLocator(apiService: ApiService, schedulersFacade: SchedulersFacade) : DefaultServiceLocator {
        return DefaultServiceLocator(apiService, schedulersFacade)
    }
}