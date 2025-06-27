package com.veryfi.kotlin

import com.veryfi.kotlin.w2s.*
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
class ProcessW2UrlTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processW2UrlTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processW2.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processW2Url(
            "https://cdn.veryfi.com/wp-content/uploads/image.png",
            null,
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("The Big Company", document.getString("employer_name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processW2UrlTestWithFileUrls() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processW2.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processW2Url(
            null,
            listOf("https://cdn.veryfi.com/wp-content/uploads/image.png"),
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("The Big Company", document.getString("employer_name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processW2UrlAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processW2.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.processW2UrlAsync(
            "https://cdn.veryfi.com/wp-content/uploads/image.png",
            null,
            null
        )
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("The Big Company", document.getString("employer_name"))
    }
}
