package ran.singletondev.testandroid.ui.livedata

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import ran.singletondev.testandroid.common.domain.model.RedditPost
import ran.singletondev.testandroid.common.domain.viewmodel.NetworkState
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by ran on 6/15/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class ItemKeySubredditDataSource (
        private val redditApi : ApiService,
        private val subRedditName : String,
        private val retryExecutor : Executor,
        private val schedulersFacade: SchedulersFacade
) : ItemKeyedDataSource<String, RedditPost>() {

    private var retry : (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private val compositeDisposable = CompositeDisposable()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {
        compositeDisposable.add(
                redditApi.getHot(subreddit = subRedditName, limit = params.requestedLoadSize)
                        .subscribeOn(schedulersFacade.io())
                        .observeOn(schedulersFacade.ui())
                        .doOnSubscribe { networkState.postValue(NetworkState.loading); initialLoad.postValue(NetworkState.loading)}
                        .subscribe({respons -> callback.onResult(respons.data.children.map { it.data })
                        retry = null; networkState.postValue(NetworkState.success); initialLoad.postValue(NetworkState.success)}) {
                            throwable -> retry = {loadInitial(params, callback)}
                            networkState.postValue(NetworkState.error(throwable.message))
                            initialLoad.postValue(NetworkState.error(throwable.message))
                        }
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

        //Set loading in network value
        compositeDisposable.add(
                redditApi.getHotAfter(subreddit = subRedditName,
                        after = params.key,
                        limit = params.requestedLoadSize)
                        .subscribeOn(schedulersFacade.io())
                        .observeOn(schedulersFacade.io())
                        .doOnSubscribe { networkState.postValue(NetworkState.loading) }
                        .subscribe({respons -> callback.onResult(respons.data.children.map { it.data })
                            retry = null; networkState.postValue(NetworkState.success) }) {
                            throwable -> retry = {loadAfter(params, callback)}; networkState.postValue(NetworkState.error(throwable.message))
                        }

        )

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {



    }

    override fun getKey(item: RedditPost): String = item.name

}