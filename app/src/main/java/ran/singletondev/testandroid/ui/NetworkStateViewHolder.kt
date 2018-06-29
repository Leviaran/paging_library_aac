package ran.singletondev.testandroid.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ran.singletondev.testandroid.R
import ran.singletondev.testandroid.common.domain.viewmodel.NetworkState

/**
 * Created by ran on 6/17/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class NetworkStateViewHolder (view : View, private val retryCallback : () -> Unit ) : RecyclerView.ViewHolder(view){

    private val progressBar = view.findViewById<View>(R.id.progress_bar)
    private val retry = view.findViewById<View>(R.id.retry_button)
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback
        }
    }

    fun bindTo(networkState : NetworkState?) {
        when (networkState) {
            is NetworkState.loading -> progressBar.visibility = toVisible(true)
            is NetworkState.error -> {
                retry.visibility = toVisible(true)
                errorMsg.visibility = toVisible(networkState.msg != null)
                errorMsg.text = networkState.msg
            }
        }

//        progressBar.visibility = toVisible(networkState == NetworkState.loading)
//        retry.visibility = toVisible(networkState == NetworkState.error())
//        errorMsg.visibility = toVisible(networkState.)
//        errorMsg.text = networkState
    }

    companion object {

        fun create(parent : ViewGroup, retryCallback: () -> Unit) : NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }

        fun toVisible(constraint : Boolean) : Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}