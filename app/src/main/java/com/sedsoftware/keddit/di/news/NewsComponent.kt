package com.sedsoftware.keddit.di.news

import com.sedsoftware.keddit.di.AppModule
import com.sedsoftware.keddit.di.NetworkModule
import com.sedsoftware.keddit.features.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AppModule::class,
    NewsModule::class,
    NetworkModule::class)
)
interface NewsComponent {

  fun inject(newsFragment: NewsFragment)

}