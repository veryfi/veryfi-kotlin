package com.veryfi.kotlin

/**
 * header constants for HttpRequests.
 */
object Constants {
    /**
     * header for HttpRequest
     */
    const val ACCEPT = "Accept"

    /**
     * header for HttpRequest
     */
    const val USER_AGENT = "User-Agent"

    /**
     * header for HttpRequest
     */
    const val USER_AGENT_KOTLIN = "Kotlin Veryfi-Kotlin/1.0.8"

    /**
     * header for HttpRequest
     */
    const val APPLICATION_JSON = "application/json"

    /**
     * header for HttpRequest
     */
    const val CONTENT_TYPE = "Content-Type"

    /**
     * header for HttpRequest
     */
    const val CLIENT_ID = "Client-Id"

    /**
     * header for HttpRequest
     */
    const val AUTHORIZATION = "Authorization"

    /**
     * header for HttpRequest
     */
    const val FORM_URL_ENCODED = "application/x-www-form-urlencoded"

    /**
     * header for HttpRequest
     */
    const val X_VERYFI_REQUEST_TIMESTAMP = "X-Veryfi-Request-Timestamp"

    /**
     * header for HttpRequest
     */
    const val X_VERYFI_REQUEST_SIGNATURE = "X-Veryfi-Request-Signature"

    /**
     * header for HttpRequest
     */
    const val SHA256 = "HmacSHA256"

    /**
     * param for HttpRequest
     */
    const val FILE_NAME = "file_name"

    /**
     * param for HttpRequest
     */
    const val FILE_DATA = "file_data"

    /**
     * param for HttpRequest
     */
    const val CATEGORIES = "categories"

    /**
     * param for HttpRequest
     */
    const val AUTO_DELETE = "auto_delete"

    /**
     * param for HttpRequest
     */
    const val BOOST_MODE = "boost_mode"

    /**
     * param for HttpRequest
     */
    const val EXTERNAL_ID = "external_id"

    /**
     * param for HttpRequest
     */
    const val FILE_URL = "file_url"

    /**
     * param for HttpRequest
     */
    const val FILE_URLS = "file_urls"

    /**
     * param for HttpRequest
     */
    const val MAX_PAGES_TO_PROCESS = "max_pages_to_process"

    /**
     * param for HttpRequest
     */
    const val TEMPLATE_NAME = "template_name"

    /**
     * param for HttpRequest
     */
    const val ORDER = "order"

    /**
     * param for HttpRequest
     */
    const val DESCRIPTION = "description"

    /**
     * param for HttpRequest
     */
    const val TOTAL = "total"

    /**
     * param for HttpRequest
     */
    const val NAME = "name"

    /**
     * param for HttpRequest
     */
    const val TAGS = "tags"

    /**
     * default list of categories.
     */
    val LIST_CATEGORIES: List<String> = listOf(
        "Advertising & Marketing",
        "Automotive",
        "Bank Charges & Fees",
        "Legal & Professional Services",
        "Insurance",
        "Meals & Entertainment",
        "Office Supplies & Software",
        "Taxes & Licenses",
        "Travel",
        "Rent & Lease",
        "Repairs & Maintenance",
        "Payroll",
        "Utilities",
        "Job Supplies",
        "Grocery"
    )
}