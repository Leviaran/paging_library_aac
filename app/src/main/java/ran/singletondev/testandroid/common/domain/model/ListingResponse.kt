package ran.singletondev.testandroid.common.domain.model

/**
 * Created by ran on 6/14/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

class ListingResponse(val data : ListingData)

class ListingData(
        val children : List<RedditChildrenResponse>,
        val after : String?,
        val before : String?
)

data class RedditChildrenResponse(val data : RedditPost)
