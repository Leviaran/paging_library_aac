package ran.singletondev.testandroid.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import ran.singletondev.testandroid.ui.repository.memori.RedditPostRepsitory

/**
 * Created by ran on 6/28/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class SubRedditPostViewModel(private val repository : RedditPostRepsitory) : ViewModel() {
    private val subRedditName = MutableLiveData<String>()

    private val repoResult = map(subRedditName, {
        repository.poststOfSubreddit(it, 30)
    })

    val posts = switchMap(repoResult, {it.pageList})
    val networkState = switchMap(repoResult, {it.networkState})
    val refreshState = switchMap(repoResult, {it.refreshState})

    fun refresh () {
        repoResult.value?.refresh?.invoke()
    }

    //TODO ganti
    fun showSubReddit(subReddit : String) : Boolean {
        if (subRedditName.value == subReddit) {
            return false
        }

        subRedditName.value = subReddit
        return true
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

    fun currentSubReddit() : String? = subRedditName.value



}