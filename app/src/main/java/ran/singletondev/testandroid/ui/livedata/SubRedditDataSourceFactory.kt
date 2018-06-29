package ran.singletondev.testandroid.ui.livedata

import android.arch.core.util.Function
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import ran.singletondev.testandroid.common.domain.model.RedditPost
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

class SubRedditDataSourceFactory(
        val redditApi : ApiService,
        val subRedditName : String,
        val retryExecutor: Executor,
        val schedulersFacade: SchedulersFacade
) : DataSource.Factory<String, RedditPost>(){

    @Inject
    lateinit var source : ItemKeySubredditDataSource

    val sourceLiveData = MutableLiveData<ItemKeySubredditDataSource>()
    override fun create(): DataSource<String, RedditPost> {
        val source = ItemKeySubredditDataSource(redditApi, subRedditName, retryExecutor, schedulersFacade)
        sourceLiveData.postValue(source)
        return source
    }

}