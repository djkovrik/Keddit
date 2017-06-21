package com.sedsoftware.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sedsoftware.keddit.R
import com.sedsoftware.keddit.commons.adapter.ViewType
import com.sedsoftware.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.sedsoftware.keddit.commons.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

  override fun onCreateViewHolder(parent: ViewGroup)
      = LoadingDelegateAdapter.LoadingViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
  }

  class LoadingViewHolder(
      parent: android.view.ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.news_item_loading))
}