package com.veryfi.kotlin;

import com.veryfi.kotlin.checks.deleteCheck
import com.veryfi.kotlin.checks.deleteCheckAsync
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
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
class DeleteCheckTest : ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun deleteCheckTest() {
        val documentId = "4560117" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn("{\"status\": \"ok\", \"message\": \"Document has been deleted\"}")
        }
        val jsonResponse = client.deleteCheck(documentId)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("ok", document.getString("status"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun deleteCheckAsyncTest() {
        val documentId = "4560117" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn("{\"status\": \"ok\", \"message\": \"Document has been deleted\"}")
        }
        val jsonResponseFuture = client.deleteCheckAsync(documentId)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("ok", document.getString("status"))
    }

} 