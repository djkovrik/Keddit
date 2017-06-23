package com.sedsoftware.keddit.features.news

import com.sedsoftware.keddit.api.RestApi
import com.sedsoftware.keddit.commons.RedditNewsItem
import io.reactivex.Observable

class NewsManager(private val api: RestApi = RestApi()) {

  fun getNews(limit: String = "10"): Observable<List<RedditNewsItem>> {
    return Observable.create {
      subscriber ->
      val callResponse = api.getNews("", limit)
      val response = callResponse.execute()

      if (response.isSuccessful) {
        val news = response.body()?.data?.children?.map {
          val item = it.data
          RedditNewsItem(item.author, item.title, item.num_comments,
              item.created, item.thumbnail, item.url)
        }
        news?.let { subscriber.onNext(it) }
        subscriber.onComplete()
      } else {
        subscriber.onError(Throwable(response.message()))
      }
    }
  }
}