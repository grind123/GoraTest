package com.grind.goratest.utils

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * This verifier trust all services
 */
class MySSLVerifier: HostnameVerifier {
    override fun verify(p0: String?, p1: SSLSession?): Boolean {
        return true
    }
}