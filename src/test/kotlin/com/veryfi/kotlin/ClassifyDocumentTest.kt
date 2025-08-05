package com.veryfi.kotlin

import com.veryfi.kotlin.classify.classifyDocument
import com.veryfi.kotlin.classify.classifyDocumentAsync
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
class ClassifyDocumentTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun classifyDocumentTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("classify.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponse = client.classifyDocument(path, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("receipt", document.getJSONObject("data").getJSONObject("document_type").getString("value"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun classifyDocumentAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("classify.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponseFuture = client.classifyDocumentAsync(path, null)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("receipt", document.getJSONObject("data").getJSONObject("document_type").getString("value"))
    }
}
