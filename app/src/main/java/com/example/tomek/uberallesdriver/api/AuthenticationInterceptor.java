package com.example.tomek.uberallesdriver.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken)
                .method(original.method(), original.body());
        System.out.println("intercept");

        Request request = builder.build();
        Response response = chain.proceed(request);
//        System.out.println(String.format("Received response for %s in %.1fms%n%s" +
//                response.request().url() + response.headers()));
        return response;
}
}