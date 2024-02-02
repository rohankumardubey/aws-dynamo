package com.amazonaws.dynamo.util;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.concurrent.CompletableFuture;

public class DynamoDBTableWrapper<T> {
    public final DynamoDbTable<T> table;

    public DynamoDBTableWrapper(DynamoDbTable<T> table) {
        this.table = table;
    }

    public T getItem(T item) {

        return table.getItem(item);
    }

    public void putItem(T item) {
        table.putItem(item);
    }

    public void updateItem(T item) {
        table.updateItem(item);
    }

    public void deleteItem(T item) {
        table.deleteItem(item);
    }

    public String tableName(){return table.tableName();}

    public PageIterable<T> queryTable(QueryEnhancedRequest request){
        return table.query(request);
    }
}

