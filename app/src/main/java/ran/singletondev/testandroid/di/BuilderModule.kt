package ran.singletondev.testandroid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ran.singletondev.testandroid.ui.RedditActivity

/**
 * Created by ran on 6/29/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Module
abstract class BuilderModule {
    @ContributesAndroidInjector(modules = arrayOf(ServiceLocatorModule::class))
    abstract fun bindRedditActivity() : RedditActivity
}