package com.veryfi.kotlin

import com.veryfi.kotlin.VeryfiClientFactory.createClient
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ClientTest {
    private var clientId = "your_client_id"
    private var username = "your_username"
    private var apiKey = "your_apikey"
    var client = createClient(clientId, username, apiKey) as Client
    var mockResponses = true // Change to “false” if you want to test your personal credential
}
