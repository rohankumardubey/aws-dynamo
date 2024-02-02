package com.amazonaws.dynamo.CrudOperations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.*;

import java.io.IOException;
import java.text.ParseException;

import static com.amazonaws.dynamo.CrudOperations.CRUD.*;

public class RestService {
    private static OkHttpClient okHttpClient = null;

    public static Response execute(String url, DatabaseCRUDOperation<Long> databaseCRUDOperation, String media) throws IOException, ParseException, URISyntaxException, ExecutionException, InterruptedException {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .build();
        }
        Response response = null;
//        if (databaseCRUDOperation.getCrud()== READ) {
//            response = get(okHttpClient, url, databaseCRUDOperation, media);
//        } else if (databaseCRUDOperation.getCrud()== CREATE_AND_UPDATE){
//            response=post(okHttpClient,url,databaseCRUDOperation,media);
//        }
         if (databaseCRUDOperation.getCrud()== UPDATE){
            response=put(okHttpClient,url,databaseCRUDOperation,media);
        }
//        else if (databaseCRUDOperation.getCrud()== DELETE){
//            response=delete(okHttpClient,url,databaseCRUDOperation,media);
//        }

        return response;
    }
    public static Response post(OkHttpClient okHttpClient,String url,DatabaseCRUDOperation<Long> databaseCRUDOperation,String media) throws IOException {

        String sample = RequestBodyGenerator.generatePostBody(databaseCRUDOperation);
        MediaType mediaType = MediaType.parse(media);
        Request request;
        RequestBody body = RequestBody.create(sample,mediaType);
        request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", media)
                    .build();
        Response response =  okHttpClient.newCall(request).execute();
        return response;
    }


    public static Response get(OkHttpClient okHttpClient,String url,DatabaseCRUDOperation<Long> databaseCRUDOperation,String media) throws IOException, ParseException, ExecutionException, InterruptedException {
        String sample = RequestBodyGenerator.generateGetBody(databaseCRUDOperation);
        MediaType mediaType = MediaType.parse(media);
        Request request;
        RequestBody body = RequestBody.create(sample,mediaType);
        request = new Request.Builder()
                    .url(url+"lambdaGet")
                    .post(body)
                    .addHeader("Content-Type", media)
                    .build();
        long startTime = System.currentTimeMillis();

        Response response = okHttpClient.newCall(request).execute();
//        Response response = doGetRequest(request);
        System.out.println("Get request time ---> "+(System.currentTimeMillis()-startTime));
        return response;
    }

    public static Response put(OkHttpClient okHttpClient, String url, DatabaseCRUDOperation<Long> databaseCRUDOperation, String media) throws IOException, ParseException, URISyntaxException, ExecutionException, InterruptedException {
        Request putRequest;
        MediaType mediaType = MediaType.parse(media);
        String path = "/Users/dubeyroh/Library/CloudStorage/OneDrive-TheStarsGroup/Desktop/Github/aws-dynamo/src/main/resources/Sample.txt";

        //getting data
        Long postTime = System.currentTimeMillis();
        Response getResponse =  get(okHttpClient,url,databaseCRUDOperation,media);
        String responseString = getResponse.body().string();
        //parsing the getter data
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(responseString);
        String postSample = null;
        if (matcher.find()) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(matcher.group(1), Map.class);
            postSample = RequestBodyGenerator.generatePutBody(map,databaseCRUDOperation);
        }

        RequestBody putBody = RequestBody.create(postSample,mediaType);
        putRequest = new Request.Builder()
                .url(url)
                .post(putBody)
                .addHeader("Content-Type", media)
                .build();

//        System.out.println("=========================================================================");
//        System.out.println(putResponse.body().string());

//        Call call = okHttpClient
//                .newCall(putRequest);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("error");
//                call.cancel();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                response.body().string();
//                response.close();
//            }
//        });



//        OkHttpResponseFuture callback = new OkHttpResponseFuture();
//        okHttpClient.newCall(putRequest).enqueue(callback);
//
//        return callback.future.thenApply(response -> {
//            try {
//                return response;
//            }finally {
//                response.close();
//            }
//        });
////        Response response = null;

        Response response = okHttpClient.newCall(putRequest).execute();

//        doGetRequest(putRequest);
        Long timeCompleted = System.currentTimeMillis();
        System.out.println("update request time ---> "+(timeCompleted-postTime));
        WriteFile writeFile = new WriteFile(path,true);
        writeFile.writeToFile(String.valueOf(timeCompleted-postTime));
//        System.out.println("---------------------------------");
        return response;
//        return null;
    }

    public static void doGetRequest(Request request) throws ExecutionException, InterruptedException {
//        CallbackFuture future = new CallbackFuture();
//        okHttpClient.newCall(request).enqueue(future);
//        return future.get();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error
                        System.out.println("error");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();
//                        System.out.println(res);
                        // Do something with the response
                    }
                });
    }

    public static Response delete(OkHttpClient okHttpClient,String url,DatabaseCRUDOperation<Long> databaseCRUDOperation,String media) throws IOException {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .build();
        }
        String sample = RequestBodyGenerator.generateDeleteBody(databaseCRUDOperation);
        System.out.println(sample);
        MediaType mediaType = MediaType.parse(media);
        Request request;
        RequestBody body = RequestBody.create(sample,mediaType);
        request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", media)
                .build();
        Response response =  okHttpClient.newCall(request).execute();
        assert response.body() != null;
        System.out.println(response.body().string());
        return response;
    }
}
