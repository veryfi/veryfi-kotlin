package com.veryfi.kotlin

import com.veryfi.kotlin.documents.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Flow
import java.util.concurrent.TimeUnit

/**
 * Verifies the Veryfi Thinking Mode `thinking` parameter is serialized into the
 * request body of the Process a Document endpoint.
 * Docs: https://docs.veryfi.com/api/receipts-invoices/process-a-document/
 */
@Suppress("UNCHECKED_CAST")
@SpringBootTest
class ProcessDocumentThinkingTest : ClientTest() {

    private fun setupSendMock(): HttpClient {
        val httpClient = mock(HttpClient::class.java)
        client.setHttpClient(httpClient)
        val result = String(ClassLoader.getSystemResourceAsStream("processDocument.json")!!.readAllBytes())
        val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
        `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
        `when`(httpResponse.statusCode()).thenReturn(200)
        `when`(httpResponse.body()).thenReturn(result)
        return httpClient
    }

    private fun setupSendAsyncMock(): HttpClient {
        val httpClient = mock(HttpClient::class.java)
        client.setHttpClient(httpClient)
        val result = String(ClassLoader.getSystemResourceAsStream("processDocument.json")!!.readAllBytes())
        val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
        `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>()))
            .thenReturn(CompletableFuture.completedFuture(httpResponse))
        `when`(httpResponse.statusCode()).thenReturn(200)
        `when`(httpResponse.body()).thenReturn(result)
        return httpClient
    }

    private fun bodyOf(request: HttpRequest): String {
        val publisher = request.bodyPublisher().orElseThrow()
        val result = StringBuilder()
        val latch = CountDownLatch(1)
        publisher.subscribe(object : Flow.Subscriber<ByteBuffer> {
            override fun onSubscribe(subscription: Flow.Subscription) = subscription.request(Long.MAX_VALUE)
            override fun onNext(item: ByteBuffer) {
                result.append(StandardCharsets.UTF_8.decode(item).toString())
            }
            override fun onError(throwable: Throwable) = latch.countDown()
            override fun onComplete() = latch.countDown()
        })
        latch.await(5, TimeUnit.SECONDS)
        return result.toString()
    }

    @Test
    fun processDocumentIncludesThinkingWhenProvided() {
        var httpClient: HttpClient? = null
        if (mockResponses) httpClient = setupSendMock()
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        client.processDocument(path, null, false, "light", null)
        if (mockResponses) {
            val captor = ArgumentCaptor.forClass(HttpRequest::class.java)
            verify(httpClient!!).send(captor.capture(), any<BodyHandler<String>>())
            val body = bodyOf(captor.value)
            Assertions.assertTrue(
                body.contains("\"thinking\":\"light\""),
                "request body should contain thinking=light, was: $body"
            )
        }
    }

    @Test
    fun processDocumentOmitsThinkingByDefault() {
        var httpClient: HttpClient? = null
        if (mockResponses) httpClient = setupSendMock()
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        client.processDocument(path, null, false, null)
        if (mockResponses) {
            val captor = ArgumentCaptor.forClass(HttpRequest::class.java)
            verify(httpClient!!).send(captor.capture(), any<BodyHandler<String>>())
            val body = bodyOf(captor.value)
            Assertions.assertFalse(
                body.contains("\"thinking\""),
                "request body should not contain thinking when not provided, was: $body"
            )
        }
    }

    @Test
    fun processDocumentAsyncIncludesThinkingWhenProvided() {
        var httpClient: HttpClient? = null
        if (mockResponses) httpClient = setupSendAsyncMock()
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        client.processDocumentAsync(path, null, false, "deep", null).get()
        if (mockResponses) {
            val captor = ArgumentCaptor.forClass(HttpRequest::class.java)
            verify(httpClient!!).sendAsync(captor.capture(), any<BodyHandler<String>>())
            val body = bodyOf(captor.value)
            Assertions.assertTrue(
                body.contains("\"thinking\":\"deep\""),
                "async request body should contain thinking=deep, was: $body"
            )
        }
    }

    @Test
    fun processDocumentUrlIncludesThinkingWhenProvided() {
        var httpClient: HttpClient? = null
        if (mockResponses) httpClient = setupSendMock()
        client.processDocumentUrl(
            "https://cdn.veryfi.com/wp-content/uploads/image.png",
            null, null, false, 1, false, null, "deep", null
        )
        if (mockResponses) {
            val captor = ArgumentCaptor.forClass(HttpRequest::class.java)
            verify(httpClient!!).send(captor.capture(), any<BodyHandler<String>>())
            val body = bodyOf(captor.value)
            Assertions.assertTrue(
                body.contains("\"thinking\":\"deep\""),
                "url request body should contain thinking=deep, was: $body"
            )
        }
    }

    @Test
    fun processDocumentUrlOmitsThinkingByDefault() {
        var httpClient: HttpClient? = null
        if (mockResponses) httpClient = setupSendMock()
        client.processDocumentUrl(
            "https://cdn.veryfi.com/wp-content/uploads/image.png",
            null, null, false, 1, false, null, null
        )
        if (mockResponses) {
            val captor = ArgumentCaptor.forClass(HttpRequest::class.java)
            verify(httpClient!!).send(captor.capture(), any<BodyHandler<String>>())
            val body = bodyOf(captor.value)
            Assertions.assertFalse(
                body.contains("\"thinking\""),
                "url request body should not contain thinking when not provided, was: $body"
            )
        }
    }
}
