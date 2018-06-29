package ran.singletondev.testandroid.network

import io.reactivex.Single
import ran.singletondev.testandroid.common.domain.model.ListingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ran on 6/14/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */
const val BASE_URL = "https://www.reddit.com/"

interface ApiService {
    @GET("/r/{subreddit}/hot.json")
    fun getHot(
            @Path("subreddit") subreddit : String,
            @Query("limit") limit : Int
    ) : Single<ListingResponse>

    @GET("/r/{subreddit}/hot.json")
    fun getHotAfter(
            @Path("subreddit") subreddit : String,
            @Query("after") after : String,
            @Query("limit") limit: Int
    ) : Single<ListingResponse>

    @GET("/r/{subreddit}/hot.json")
    fun getHotBefore(
            @Path("subreddit") subreddit: String,
            @Query("before") before : String,
            @Query("limit") limit: Int
    )
}