package ran.singletondev.testandroid.common.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by ran on 6/14/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Entity(tableName = "posts",
        indices = [(Index(value = ["subreddit"], unique = false))])
data class RedditPost (

        @PrimaryKey
        @Json(name = "name")
        val name : String,

        @Json(name = "title")
        val title : String,

        @Json(name = "score")
        val score : Int,

        @Json(name = "author")
        val author : String,

        @Json(name = "subreddit")
        @ColumnInfo(collate = ColumnInfo.NOCASE)
        val subreddit : String,

        @Json(name = "num_comments")
        val num_comments : Int,

        @Json(name = "created_utc")
        val created : Long,
        val thumbnail : String?,
        val url : String?
) {
        var indexIntResponse : Int = -1
}