package com.weather.data.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {


    companion object {
        const val AcceptKey = "Accept"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader(AcceptKey, "application/json")
        request = requestBuilder.build()

        return chain.proceed(request)
    }
}