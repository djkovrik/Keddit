package com.sedsoftware.keddit.commons

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class RxBaseFragment : Fragment() {

  protected var disposableList = CompositeDisposable()

  override fun onResume() {
    super.onResume()
    disposableList = CompositeDisposable()
  }

  override fun onPause() {
    super.onPause()
    disposableList.clear()
  }
}