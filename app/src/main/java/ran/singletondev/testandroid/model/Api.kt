package ran.singletondev.testandroid.model

import io.reactivex.Observable

/**
 * Created by ran on 6/8/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class Api {
    fun login(userName : String, password : String) : Observable<Boolean> =
            Observable.just(true)
}