package ran.singletondev.testandroid.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ran.singletondev.testandroid.R
import ran.singletondev.testandroid.common.domain.model.RedditPost
import ran.singletondev.testandroid.common.domain.viewmodel.NetworkState

/**
 * Created by ran on 6/28/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class PostAdapter (
        private val glide : GlideRequests,
        private val retryCallback : () -> Unit
) : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState : NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.reddti_post_item -> RedditPostViewHolder.create(parent, glide)
            R.layout.network_state_item -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("Salah menginflate layout $viewType")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            R.layout.reddti_post_item -> (holder as RedditPostViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()){
            val item = getItem(position)
            (holder as RedditPostViewHolder).updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.loading

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1){
            R.layout.network_state_item
        } else {
            R.layout.reddti_post_item
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    fun setNetworkState(newNetworkSate : NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkSate
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow()) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkSate) {
            notifyItemChanged(itemCount -1)
        }
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditPost>() {

            override fun areContentsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean =
                    oldItem == newItem


            override fun areItemsTheSame(oldItem: RedditPost?, newItem: RedditPost?): Boolean =
                    oldItem?.name == newItem?.name

            override fun getChangePayload(oldItem: RedditPost?, newItem: RedditPost?): Any? {
                return if (sameExceptScore(oldItem, newItem)){
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem : RedditPost?, newItem : RedditPost?) : Boolean =
                oldItem?.copy(score = newItem!!.score) == newItem
    }

}