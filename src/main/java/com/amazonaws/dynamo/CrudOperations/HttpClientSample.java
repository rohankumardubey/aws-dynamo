package com.amazonaws.dynamo.CrudOperations;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.nio.charset.StandardCharsets;

public class HttpClientSample {


    public static void send(String restReq, String url) {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(1000000000);
            cm.setDefaultMaxPerRoute(1000000000);

            HttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
//            httpClient = HttpClientBuilder.create().build();

        String httpResponseString = "";
//        ObjectNode restResp = om.createObjectNode();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        HttpResponse httpResp = null;

        try {
            httpPost.setEntity(new StringEntity(restReq.toString(), StandardCharsets.UTF_8));
            long httpStartNanos = System.nanoTime();
            httpResp = httpClient.execute(httpPost);
            long httpEndNanos = System.nanoTime();
//            httpResponseString = IOUtils.toString(httpResp.getEntity().getContent(), StandardCharsets.UTF_8);
//            restResp = (ObjectNode)om.readTree(httpResponseString);
            System.out.println(httpEndNanos - httpStartNanos);
//            restResp.put("http_duration_nanos", );
//            restResp.put("success", true);
        } catch (Exception var11) {
//            restResp.put("success", false);
////            restResp.put("error", Utilities.exceptionToString(var11));
//            restResp.put("http_response_string", httpResponseString);
        }

        httpResp.toString();
    }


}
