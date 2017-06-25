package com.hkurokawa.rxjavaexample;

import android.support.annotation.NonNull;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GitHubOAuthAppAuthenticationInterceptor implements Interceptor {
  private final String clientId;
  private final String clientSecret;

  public GitHubOAuthAppAuthenticationInterceptor(String clientId, String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @Override public Response intercept(@NonNull Chain chain) throws IOException {
    final Request request = chain.request();
    final HttpUrl url = request.url()
        .newBuilder()
        .addQueryParameter("client_id", clientId)
        .addQueryParameter("client_secret", clientSecret)
        .build();
    return chain.proceed(request.newBuilder().url(url).build());
  }
}
