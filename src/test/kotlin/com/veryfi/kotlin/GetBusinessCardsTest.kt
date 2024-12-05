package com.veryfi.kotlin

import com.veryfi.kotlin.businessCards.*
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import java.io.IOException
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@Suppress("UNCHECKED_CAST")
@SpringBootTest
class GetBusinessCardsTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getBusinessCardsTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getBusinessCards.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.getBusinessCards()
        val documents = JSONObject(jsonResponse)
        Assertions.assertTrue(documents.length() > 0)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getBusinessCardsTestWithException() {
        val httpClient = mock(HttpClient::class.java)
        client.setHttpClient(httpClient)
        `when`(
            httpClient.send(
                any(HttpRequest::class.java), any<BodyHandler<String>>()
            )
        ).thenThrow(InterruptedException())
        val jsonResponse = client.getBusinessCards()
        Assertions.assertEquals("", jsonResponse)
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun getBusinessCardsAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getBusinessCards.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.getBusinessCardsAsync()
        val jsonResponse = jsonResponseFuture.get()
        val documents = JSONObject(jsonResponse)
        Assertions.assertTrue(documents.length() > 0)
    }
}
