package com.amazonaws.dynamo.CrudOperations;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class CallbackFuture extends CompletableFuture<Response> implements Callback {
    public void onResponse(Call call, Response response) {
        super.complete(response);
    }
    public void onFailure(Call call, IOException e){
        super.completeExceptionally(e);
    }
}