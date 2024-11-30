package com.veryfi.kotlin

import com.veryfi.kotlin.documents.*
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
class ProcessDocumentTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTest() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponse = client.processDocument(path, categories, false, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Walgreens", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTestWithParameters() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        val parameters = JSONObject()
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponse = client.processDocument(path, categories, false, parameters)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Walgreens", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTestWithoutCategories() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }

        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponse = client.processDocument(path, null, false, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Walgreens", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processDocumentAsyncTest() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
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
        val jsonResponseFuture = client.processDocumentAsync(path, categories, false, null)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Walgreens", document.getJSONObject("vendor").getString("name"))
    }
}
