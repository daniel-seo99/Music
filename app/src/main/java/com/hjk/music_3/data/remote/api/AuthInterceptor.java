package com.hjk.music_3.data.remote.api;



import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//
//public class AuthInterceptor implements Interceptor {
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request=chain.request();
//
//        HttpUrl url=request.url().newBuilder()
//                .addQueryParameter("api_key","kyu")
//                .build();
//
//        request= request.newBuilder().url(url).build();
//        return chain.proceed(request);
//    }
//}
