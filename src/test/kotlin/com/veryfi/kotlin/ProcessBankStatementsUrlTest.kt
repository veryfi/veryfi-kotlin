package com.veryfi.kotlin

import com.veryfi.kotlin.bankstatements.processBankStatementUrl
import com.veryfi.kotlin.bankstatements.processBankStatementUrlAsync
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
class ProcessBankStatementsUrlTest: ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processBankStatementUrlTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processBankStatement.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processBankStatementUrl(
            "https://cdn-dev.veryfi.com/testing/veryfi-python/bankstatement.pdf",
            null,
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Mr Robot Roboto", document.getString("account_holder_name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processBankStatementUrlTestWithFileUrls() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processBankStatement.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processBankStatementUrl(
            null,
            listOf("https://cdn-dev.veryfi.com/testing/veryfi-python/bankstatement.pdf"),
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Mr Robot Roboto", document.getString("account_holder_name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processBankStatementUrlAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processBankStatement.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.processBankStatementUrlAsync(
            "https://cdn-dev.veryfi.com/testing/veryfi-python/bankstatement.pdf",
            null,
            null
        )
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Mr Robot Roboto", document.getString("account_holder_name"))
    }
}
