package ran.singletondev.testandroid.ui.livedata

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import ran.singletondev.testandroid.common.domain.viewmodel.NetworkState

/**
 * Created by ran on 6/15/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

data class Listing<T> (

        //Observe live data from page list UI
         val pageList : LiveData<PagedList<T>>,

        //Present Network Status to user
        val networkState : LiveData<NetworkState>,

        //Present network status to user
        val refreshState : LiveData<NetworkState>,

        //Refresh all data from
        val refresh : () -> Unit,

         //retry every failed request
        val retry : () -> Unit

)