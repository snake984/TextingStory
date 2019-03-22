package com.textingstory.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = Credentials.basic("test", "VF62pqDX")
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Authorization", token)

        val request = builder.build()
        return chain.proceed(request)
    }
}