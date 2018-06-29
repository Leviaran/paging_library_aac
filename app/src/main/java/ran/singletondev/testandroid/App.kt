package ran.singletondev.testandroid

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ran.singletondev.testandroid.di.AppComponent
import ran.singletondev.testandroid.di.DaggerAppComponent
import ran.singletondev.testandroid.ui.RedditActivity
import javax.inject.Inject

/**
 * Created by ran on 6/8/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class App : Application(), HasActivityInjector {

    companion object {
        lateinit var appComponent : AppComponent
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

     appComponent = DaggerAppComponent
             .builder()
             .application(this)
             .build()

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)


    }


}