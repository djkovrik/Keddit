package com.sedsoftware.keddit

import com.sedsoftware.keddit.api.*
import com.sedsoftware.keddit.commons.RedditNews
import com.sedsoftware.keddit.features.news.NewsManager
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import retrofit2.Call
import retrofit2.Response
import java.util.*

class NewsManagerTest {

  var testSub = TestObserver<RedditNews>()
  var apiMock = mock<NewsAPI>()
  var callMock = mock<Call<RedditNewsResponse>>()

  @Before
  fun setup() {
    testSub = TestObserver<RedditNews>()
    apiMock = mock<NewsAPI>()
    callMock = mock<Call<RedditNewsResponse>>()
    `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
  }

  @Test
  fun testSuccess_basic() {
    // prepare
    val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
    val response = Response.success(redditNewsResponse)

    `when`(callMock.execute()).thenReturn(response)

    // call
    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    // assert
    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertComplete()
  }

  @Test
  fun testSuccess_checkOneNews() {
    // prepare
    val newsData = RedditNewsDataResponse(
        "author",
        "title",
        10,
        Date().time,
        "thumbnail",
        "url"
    )
    val newsResponse = RedditChildrenResponse(newsData)
    val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
    val response = Response.success(redditNewsResponse)

    `when`(callMock.execute()).thenReturn(response)

    // call
    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    // assert
    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertComplete()
  }
}

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)