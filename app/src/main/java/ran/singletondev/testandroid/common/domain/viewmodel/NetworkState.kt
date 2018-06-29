package ran.singletondev.testandroid.common.domain.viewmodel

/**
 * Created by ran on 6/15/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

sealed class NetworkState {
    object loading : NetworkState()
    object success : NetworkState()
    data class error(val msg : String?) : NetworkState()

}