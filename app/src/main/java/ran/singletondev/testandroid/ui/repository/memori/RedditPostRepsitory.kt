package ran.singletondev.testandroid.ui.repository.memori

import ran.singletondev.testandroid.common.domain.model.RedditPost
import ran.singletondev.testandroid.ui.livedata.Listing

/**
 * Created by ran on 6/17/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

interface RedditPostRepsitory {
    fun poststOfSubreddit(subReddit : String, pageSize : Int) : Listing<RedditPost>

    enum class Type {
        IN_MEMORY_BY_ITEM
    }
}