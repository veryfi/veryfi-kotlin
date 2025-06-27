package com.veryfi.kotlin

import com.veryfi.kotlin.documents.addLineItem
import com.veryfi.kotlin.documents.addLineItemAsync
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
class AddLineItemTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun addLineItemTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("addLineItem.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val total = 20.1f
        val description = "test"
        val jsonResponse = client.addLineItem("63480993", 20, description, total)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals(total, document.getFloat("total"))
        Assertions.assertEquals(description, document.getString("description").lowercase())
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun addLineItemAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("addLineItem.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val total = 20.1f
        val description = "test"
        val jsonResponseFuture = client.addLineItemAsync("63480993", 20, description, total)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals(total, document.getFloat("total"))
        Assertions.assertEquals(description, document.getString("description").lowercase())
    }
}
