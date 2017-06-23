package com.sedsoftware.keddit.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sedsoftware.keddit.R
import com.sedsoftware.keddit.commons.RxBaseFragment
import com.sedsoftware.keddit.commons.extensions.inflate
import com.sedsoftware.keddit.features.news.adapter.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.*

class NewsFragment : RxBaseFragment() {

  private val newsManager by lazy { NewsManager() }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return container?.inflate(R.layout.news_fragment)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    news_list.setHasFixedSize(true)
    news_list.layoutManager = LinearLayoutManager(context)
    initAdapter()

    requestNews()
  }

  private fun requestNews() {
    val dispose = newsManager.getNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { retrievedNews ->
              (news_list.adapter as NewsAdapter).addNews(retrievedNews)
            },
            { error ->
              Snackbar.make(news_list, error.message ?: "", Snackbar.LENGTH_LONG).show()
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
