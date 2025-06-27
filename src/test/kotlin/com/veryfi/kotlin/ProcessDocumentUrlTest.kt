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
class ProcessDocumentUrlTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentUrlTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processDocumentUrl(
            "https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf",
            null,
            null,
            false,
            1,
            false,
            null,
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke Of Ohio", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentUrlTestWithFileUrlsAndParameters() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val parameters = JSONObject()
        val jsonResponse = client.processDocumentUrl(
            "",
            listOf("https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf"),
            null,
            false,
            1,
            false,
            null,
            parameters
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke Of Ohio", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processDocumentUrlAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.processDocumentUrlAsync(
            "https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf",
            null,
            null,
            false,
            1,
            false,
            null,
            null
        )
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke Of Ohio", document.getJSONObject("vendor").getString("name"))
    }
}
