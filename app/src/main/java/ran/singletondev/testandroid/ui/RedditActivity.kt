package ran.singletondev.testandroid.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dagger.android.AndroidInjection
import ran.singletondev.testandroid.R
import ran.singletondev.testandroid.ui.repository.memori.RedditPostRepsitory
import ran.singletondev.testandroid.util.ServiceLocator
import kotlinx.android.synthetic.main.activity_reddit.*
import ran.singletondev.testandroid.App
import ran.singletondev.testandroid.common.domain.model.RedditPost
import ran.singletondev.testandroid.common.domain.viewmodel.NetworkState
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.ui.livedata.SubRedditDataSourceFactory
import ran.singletondev.testandroid.util.DefaultServiceLocator
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by ran on 6/28/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class RedditActivity: AppCompatActivity() {

    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = "androiddev"
        const val KEY_REPOSITORY_TYPE = "repository_type"

        fun intentFor(context: Context, type : RedditPostRepsitory.Type) : Intent {
            return Intent(context, RedditActivity::class.java).putExtra(KEY_REPOSITORY_TYPE, type.ordinal)
        }
    }

    @Inject
    lateinit var defaultServiceLocator : DefaultServiceLocator

    private lateinit var model : SubRedditPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        App.appComponent.inject(this)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_reddit)



        model = getViewModel()

        initAdapter()
        initSwipeToRefresh()

        val subreddit = savedInstanceState?.getString(KEY_SUBREDDIT) ?: DEFAULT_SUBREDDIT
        model.showSubReddit(subreddit)
    }

    private fun getViewModel() : SubRedditPostViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repoTypeParam = intent.getIntExtra(KEY_REPOSITORY_TYPE, 0)
                val repoType = RedditPostRepsitory.Type.values()[repoTypeParam]
                val repo = defaultServiceLocator.getRepository(repoType)
//                val repo = ServiceLocator.instance(this@RedditActivity)
//                        .getRepository(repoType)
                @Suppress("UNCHECKED_CAST")
                return SubRedditPostViewModel(repo) as T
            }
        })[SubRedditPostViewModel::class.java]
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val adapter = PostAdapter(glide){
            model.retry()
        }
        list.adapter = adapter
        model.posts.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)
        })

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

    }

    private fun initSwipeToRefresh() {
        model.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.loading
        })
        swipe_refresh.setOnRefreshListener {
            model.refresh()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SUBREDDIT, model.currentSubReddit())
    }
}