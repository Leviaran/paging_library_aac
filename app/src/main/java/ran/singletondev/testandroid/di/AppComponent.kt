package ran.singletondev.testandroid.di

import android.os.Build
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ran.singletondev.testandroid.App
import ran.singletondev.testandroid.ui.BaseActivity
import ran.singletondev.testandroid.ui.LoginActivity
import ran.singletondev.testandroid.ui.RedditActivity
import javax.inject.Singleton
import ran.singletondev.testandroid.MainActivity
import dagger.android.AndroidInjector
import dagger.Subcomponent



/**
 * Created by ran on 6/8/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Singleton
@Component(modules = arrayOf(NetworkModule::class, AndroidInjectionModule::class, AppModule::class, BuilderModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application : App) : Builder
        fun build() : AppComponent
    }

    fun inject(app : App)

    fun inject(redditActivity: RedditActivity)

}
