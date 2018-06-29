package ran.singletondev.testandroid.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.util.DefaultServiceLocator
import javax.inject.Singleton
import ran.singletondev.testandroid.MainActivity



/**
 * Created by ran on 6/28/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application) : Context = application.applicationContext


}