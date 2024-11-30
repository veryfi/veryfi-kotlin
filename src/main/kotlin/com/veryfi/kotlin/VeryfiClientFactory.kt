package com.veryfi.kotlin

/**
 * Factory for creating instances of [Client].
 */
object VeryfiClientFactory {
    /**
     * Creates an instance of [Client].
     * @param clientId the [String] provided by Veryfi.
     * @param username the [String] provided by Veryfi.
     * @param apiKey the [String] provided by Veryfi.
     * @return the new instance.
     */
    fun createClient(clientId: String, username: String, apiKey: String, apiVersion: Int = 8): Client {
        return Client(clientId, username, apiKey, apiVersion)
    }
}