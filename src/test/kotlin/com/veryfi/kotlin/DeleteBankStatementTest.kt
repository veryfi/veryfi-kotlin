package com.veryfi.kotlin;

import com.veryfi.kotlin.bankstatements.deleteBankStatement
import com.veryfi.kotlin.bankstatements.deleteBankStatementAsync
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
class DeleteBankStatementTest : ClientTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun deleteBankStatementTest() {
        val documentId = "4560116" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn("")
        }
        val jsonResponse = client.deleteBankStatement(documentId)
        Assertions.assertTrue(jsonResponse.isEmpty())
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun deleteBankStatementAsyncTest() {
        val documentId = "4560116" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn("")
        }
        val jsonResponseFuture = client.deleteBankStatementAsync(documentId)
        val jsonResponse = jsonResponseFuture.get()
        Assertions.assertTrue(jsonResponse.isEmpty())
    }

} 