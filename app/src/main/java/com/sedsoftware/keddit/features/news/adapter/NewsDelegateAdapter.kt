package com.sedsoftware.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import com.sedsoftware.keddit.R
import com.sedsoftware.keddit.commons.RedditNewsItem
import com.sedsoftware.keddit.commons.adapter.ViewType
import com.sedsoftware.keddit.commons.extensions.getFriendlyTime
import com.sedsoftware.keddit.commons.extensions.inflate
import com.sedsoftware.keddit.commons.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

class NewsDelegateAdapter : com.sedsoftware.keddit.commons.adapter.ViewTypeDelegateAdapter {

  override fun onCreateViewHolder(
      parent: android.view.ViewGroup): RecyclerView.ViewHolder {
    return NewsDelegateAdapter.NewsViewHolder(parent)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
      item: ViewType) {
    holder as NewsDelegateAdapter.NewsViewHolder
    holder.bind(item as RedditNewsItem)
  }

  class NewsViewHolder(
      parent: android.view.ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.news_item)) {

    fun bind(item: RedditNewsItem) = with(itemView) {
      img_thumbnail.loadImg(item.thumbnail)
      description.text = item.title
      author.text = item.author
      comments.text = "${item.numComments} comments"
      time.text = item.created.getFriendlyTime()
    }
  }
}