package com.sedsoftware.keddit.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sedsoftware.keddit.KedditApp
import com.sedsoftware.keddit.R
import com.sedsoftware.keddit.commons.InfiniteScrollListener
import com.sedsoftware.keddit.commons.RedditNews
import com.sedsoftware.keddit.commons.RxBaseFragment
import com.sedsoftware.keddit.commons.extensions.inflate
import com.sedsoftware.keddit.features.news.adapter.NewsAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment : RxBaseFragment() {

  companion object {
    private val KEY_REDDIT_NEWS = "redditNews"
  }

  @Inject lateinit var newsManager: NewsManager

  private var redditNews: RedditNews? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    KedditApp.newsComponent.inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return container?.inflate(R.layout.news_fragment)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    news_list.apply {
      setHasFixedSize(true)
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
      clearOnScrollListeners()
      addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
    }

    initAdapter()

    if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
      redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
      (news_list.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
    } else {
      requestNews()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    val news = (news_list.adapter as NewsAdapter).getNews()
    if (redditNews != null && news.isNotEmpty()) {
      outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
    }
  }

  private fun requestNews() {

    val dispose = newsManager.getNews(redditNews?.after ?: "")
        .subscribeOn(Schedulers.io())
        .subscribe(
            { retrievedNews ->
              redditNews = retrievedNews
              (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
            },
            { e ->
              Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
            }
        )
    disposableList.add(dispose)
  }

  private fun initAdapter() {
    if (news_list.adapter == null) {
      news_list.adapter = NewsAdapter()
    }
  }
}
