package ran.singletondev.testandroid.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ran.singletondev.testandroid.model.Api
import ran.singletondev.testandroid.network.ApiService
import ran.singletondev.testandroid.network.BASE_URL
import ran.singletondev.testandroid.rx.SchedulersFacade
import ran.singletondev.testandroid.util.DefaultServiceLocator
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by ran on 6/8/18.
 * Email in randy.arba@gmail.com
 * Github in https://github.com/Leviaran
 * Publication in https://medium.com/@randy.arba
 */

@Module
class NetworkModule {

    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun provideOkHttp() : OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }


}