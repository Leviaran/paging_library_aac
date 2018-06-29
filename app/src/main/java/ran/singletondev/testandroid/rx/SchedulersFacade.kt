package ran.singletondev.testandroid.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ran on 6/15/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class SchedulersFacade @Inject constructor () {
    fun io () : Scheduler = Schedulers.io()

    fun computation () : Scheduler = Schedulers.computation()

    fun ui () : Scheduler = AndroidSchedulers.mainThread()
}