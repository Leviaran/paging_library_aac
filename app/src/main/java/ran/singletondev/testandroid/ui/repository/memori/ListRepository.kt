package ran.singletondev.testandroid.ui.repository.memori

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import dagger.Module
import dagger.Provides
import ran.singletondev.testandroid.common.domain.model.RedditPost
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.ui.livedata.ItemKeySubredditDataSource
import ran.singletondev.testandroid.ui.livedata.Listing
import ran.singletondev.testandroid.ui.livedata.SubRedditDataSourceFactory
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by ran on 6/15/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class ListRepository (
        private val apiService: ApiService,
        private val networkExecutor : Executor,
        private val schedulersFacade: SchedulersFacade
) : RedditPostRepsitory {


    override fun poststOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {
        val sourceFactory = SubRedditDataSourceFactory(apiService, subReddit, networkExecutor, schedulersFacade )
        val pageListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build()

        val pagedList = LivePagedListBuilder(sourceFactory, pageListConfig)
                .setFetchExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {it.initialLoad}

        return Listing(
                pagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData, {it.networkState}),
                retry = {sourceFactory.sourceLiveData.value?.retryAllFailed()},
                refresh = {sourceFactory.sourceLiveData.value?.invalidate()},
                refreshState = refreshState
        )
    }

}