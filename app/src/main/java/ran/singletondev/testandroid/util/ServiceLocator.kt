package ran.singletondev.testandroid.util

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.ui.repository.memori.ListRepository
import ran.singletondev.testandroid.ui.repository.memori.RedditPostRepsitory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by ran on 6/28/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */



/**
 * Super simplified service locator implementation to allow us to replace default implementations
 * for testing.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
//        fun instance(context: Context): ServiceLocator {
//            synchronized(LOCK) {
//                if (instance == null) {
//                    instance = DefaultServiceLocator(
//                            app = context.applicationContext as Application,
//                            useInMemoryDb = false)
//                }
//                return instance!!
//            }
//        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(type: RedditPostRepsitory.Type): RedditPostRepsitory

    fun getNetworkExecutor(): Executor

    fun getDiskIOExecutor(): Executor

}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator @Inject constructor(val apiService: ApiService,val schedulersFacade: SchedulersFacade) : ServiceLocator {


    // thread pool used for disk access
    @Suppress("PrivatePropertyName")
    private val DISK_IO = Executors.newSingleThreadExecutor()

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)



    override fun getRepository(type: RedditPostRepsitory.Type): RedditPostRepsitory {
        return when (type) {
            RedditPostRepsitory.Type.IN_MEMORY_BY_ITEM -> ListRepository(
                    apiService = apiService,
                    networkExecutor = getNetworkExecutor(),
                    schedulersFacade = schedulersFacade
                    )
        }
    }

    override fun getNetworkExecutor(): Executor = NETWORK_IO

    override fun getDiskIOExecutor(): Executor = DISK_IO

}