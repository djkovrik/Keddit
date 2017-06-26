package com.sedsoftware.keddit

import android.app.Application
import com.sedsoftware.keddit.di.AppModule
import com.sedsoftware.keddit.di.news.DaggerNewsComponent
import com.sedsoftware.keddit.di.news.NewsComponent

class KedditApp : Application() {

  companion object {
    lateinit var newsComponent: NewsComponent
  }

  override fun onCreate() {
    super.onCreate()
    newsComponent = DaggerNewsComponent.builder()
        .appModule(AppModule(this))
        .build()
  }
}