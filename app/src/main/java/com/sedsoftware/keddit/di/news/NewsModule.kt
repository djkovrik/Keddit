package com.sedsoftware.keddit.di.news

import com.sedsoftware.keddit.api.NewsAPI
import com.sedsoftware.keddit.api.NewsRestAPI
import com.sedsoftware.keddit.api.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NewsModule {

  @Provides
  @Singleton
  fun provideNewsAPI(redditApi: RedditApi): NewsAPI {
    return NewsRestAPI(redditApi)
  }

  @Provides
  @Singleton
  fun provideRedditApi(retrofit: Retrofit): RedditApi {
    return retrofit.create(RedditApi::class.java)
  }
}