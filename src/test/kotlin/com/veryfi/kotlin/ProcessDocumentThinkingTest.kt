package com.veryfi.kotlin

import com.veryfi.kotlin.VeryfiClientFactory.createClient
import com.veryfi.kotlin.documents.processDocument
import com.veryfi.kotlin.documents.processDocumentAsync
import com.veryfi.kotlin.documents.processDocumentUrl
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.clearInvocations
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.nio.ByteBuffer
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Flow

/**
 * Checks that the Veryfi Thinking Mode `thinking` request parameter reaches the
 * API body, both when passed via the dedicated argument and via the extra
 * parameters. Docs: https://docs.veryfi.com/api/receipts-invoices/process-a-document/
 */
@Suppress("UNCHECKED_CAST")
class ProcessDocumentThinkingTest {
    private val documentUrl = "https://cdn.example.com/receipt.jpg"
    private val modes = listOf("light", "deep")
    private val receiptPath = File(ClassLoader.getSystemResource("receipt.jpg").toURI()).path
    private lateinit var httpClient: HttpClient
    private lateinit var client: Client

    @BeforeEach
    fun setup() {
        httpClient = mock(HttpClient::class.java)
        val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
        `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
        `when`(httpResponse.statusCode()).thenReturn(200)
        `when`(httpResponse.body()).thenReturn("{}")
        client = createClient("your_client_id", "your_username", "your_apikey") as Client
        client.setHttpClient(httpClient)
    }

    // --- thinking passed through the extra parameters ---

    @Test
    fun processDocumentSendsThinking() {
        for (mode in modes) {
            clearInvocations(httpClient)
            client.processDocument(receiptPath, listOf(), false, JSONObject().put("thinking", mode))
            Assertions.assertEquals(mode, sentBody().getString("thinking"))
        }
    }

    @Test
    fun processDocumentUrlSendsThinking() {
        for (mode in modes) {
            clearInvocations(httpClient)
            client.processDocumentUrl(documentUrl, null, null, false, 1, false, null, JSONObject().put("thinking", mode))
            Assertions.assertEquals(mode, sentBody().getString("thinking"))
        }
    }

    @Test
    fun processDocumentWithoutThinkingDoesNotSendIt() {
        client.processDocument(receiptPath, listOf(), false, null)
        Assertions.assertFalse(sentBody().has("thinking"))
    }

    @Test
    fun processDocumentUrlWithoutThinkingDoesNotSendIt() {
        client.processDocumentUrl(documentUrl, null, null, false, 1, false, null, null)
        Assertions.assertFalse(sentBody().has("thinking"))
    }

    @Test
    fun processDocumentAsyncSendsThinking() {
        mockSendAsync()
        client.processDocumentAsync(receiptPath, listOf(), false, JSONObject().put("thinking", "deep"))
        Assertions.assertEquals("deep", sentAsyncBody().getString("thinking"))
    }

    // --- thinking passed via the dedicated argument (overloads) ---

    @Test
    fun processDocumentSendsThinkingViaDedicatedArgument() {
        for (mode in modes) {
            clearInvocations(httpClient)
            client.processDocument(receiptPath, listOf(), false, mode, null)
            Assertions.assertEquals(mode, sentBody().getString("thinking"))
        }
    }

    @Test
    fun processDocumentUrlSendsThinkingViaDedicatedArgument() {
        for (mode in modes) {
            clearInvocations(httpClient)
            client.processDocumentUrl(documentUrl, null, null, false, 1, false, null, mode, null)
            Assertions.assertEquals(mode, sentBody().getString("thinking"))
        }
    }

    @Test
    fun processDocumentAsyncSendsThinkingViaDedicatedArgument() {
        mockSendAsync()
        client.processDocumentAsync(receiptPath, listOf(), false, "deep", null)
        Assertions.assertEquals("deep", sentAsyncBody().getString("thinking"))
    }

    private fun mockSendAsync() {
        val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
        `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>()))
            .thenReturn(CompletableFuture.completedFuture(httpResponse))
        `when`(httpResponse.statusCode()).thenReturn(200)
        `when`(httpResponse.body()).thenReturn("{}")
    }

    /** The JSON body of the single synchronous request sent through the mocked client. */
    private fun sentBody(): JSONObject {
        val request = ArgumentCaptor.forClass(HttpRequest::class.java)
        verify(httpClient).send(request.capture(), any<BodyHandler<String>>())
        return bodyOf(request.value)
    }

    /** The JSON body of the single asynchronous request sent through the mocked client. */
    private fun sentAsyncBody(): JSONObject {
        val request = ArgumentCaptor.forClass(HttpRequest::class.java)
        verify(httpClient).sendAsync(request.capture(), any<BodyHandler<String>>())
        return bodyOf(request.value)
    }

    private fun bodyOf(request: HttpRequest): JSONObject {
        val out = ByteArrayOutputStream()
        request.bodyPublisher().orElseThrow().subscribe(object : Flow.Subscriber<ByteBuffer> {
            override fun onSubscribe(subscription: Flow.Subscription) = subscription.request(Long.MAX_VALUE)
            override fun onNext(item: ByteBuffer) {
                val bytes = ByteArray(item.remaining())
                item.get(bytes)
                out.write(bytes, 0, bytes.size)
            }
            override fun onError(throwable: Throwable) = throw AssertionError(throwable)
            override fun onComplete() {}
        })
        return JSONObject(out.toString())
    }
}
