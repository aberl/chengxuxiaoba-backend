package com.chengxuxiaoba.video.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class HttpUtils {
    /**
     * post request through application/x-www-form-urlencoded type request
     *
     * @param url
     * @param postData
     * @return
     */
    public static String postForm(String url, Map<String, String> postData) {
        return postForm(url, postData, null, null);
    }

    /**
     * post request through application/x-www-form-urlencoded type request
     *
     * @param url
     * @param postData
     * @param proxyUrl
     * @param proxyPort
     * @return
     */
    public static String postForm(String url, Map<String, String> postData, String proxyUrl, Integer proxyPort) {
        String resultJson = "";
        try {
            OkHttpClient client = getUnsafeOkHttpClient(proxyUrl, proxyPort);

            FormBody.Builder builder = new FormBody.Builder();

            for (Map.Entry<String, String> entry : postData.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }

            FormBody formBody = builder.build();

            Request request = new Request.Builder().url(url)
                    .post(formBody).build();


            Response response = client.newCall(request).execute();
            resultJson = response.body().string();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return resultJson;
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, String proxyUrl, Integer proxyPort) {
        OkHttpClient client = getUnsafeOkHttpClient(proxyUrl, proxyPort);

        Request request = new Request.Builder().url(url).get().build();
        String resultJson = "";
        try {
            Response response = client.newCall(request).execute();
            resultJson = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClientBuilder() {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(final java.security.cert.X509Certificate[] chain,
                                                   final String authType)
                            throws CertificateException {
                        // Empty Method
                    }

                    @Override
                    public void checkServerTrusted(final java.security.cert.X509Certificate[] chain,
                                                   final String authType)
                            throws CertificateException {
                        // Empty method
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        return getUnsafeOkHttpClient(null, null);
    }

    private static OkHttpClient getUnsafeOkHttpClient(String proxyUrl, Integer proxyPort) {
        OkHttpClient.Builder builder = null;
        if (StringUtil.isNotNullOrEmpty(proxyUrl) && proxyPort != null) {
            builder = getUnsafeOkHttpClientBuilder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort)))
            ;
        } else {
            builder = getUnsafeOkHttpClientBuilder();
        }

        return builder.build();
    }
}
