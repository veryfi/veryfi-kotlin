package com.veryfi.kotlin

import com.veryfi.kotlin.VeryfiClientFactory.createClient
import com.veryfi.kotlin.documents.processDocument
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

@SpringBootTest
class ClientTest {
    private var clientId = "your_client_id"
    private var username = "your_username"
    private var apiKey = "your_apikey"
    var client = createClient(clientId, username, apiKey) as Client
    var mockResponses = true // Change to “false” if you want to test your personal credential
    val interruptedExceptionJsonResponse = "{\"details\":[{\"reason\":\"java.lang.InterruptedException\"}],\"error\":\"Unknown error\",\"status\":\"fail\"}"
    val badGatewayResponse = "<html><head><title>502 Bad Gateway</title></head><body><center><h1>502 Bad Gateway</h1></center><hr><center>cloudflare</center></body></html>"

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun invalidJsonResponseTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(502)
            `when`(httpResponse.body()).thenReturn(badGatewayResponse)
        }
        val path = ClassLoader.getSystemResource("receipt.jpg").path
        val jsonResponse = client.processDocument(path, listOf(), false, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("fail", document.getString("status"))
        Assertions.assertEquals("Status code 502", document.getString("error"))
    }

}
