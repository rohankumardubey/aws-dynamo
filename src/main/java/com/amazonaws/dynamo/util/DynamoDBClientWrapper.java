package com.amazonaws.dynamo.util;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class DynamoDBClientWrapper<T> {


    private static DynamoDbEnhancedClient enhancedClient;
    private DynamoDBTableWrapper<T> tableWrapper;

    public DynamoDBClientWrapper() {
        initDynamoDbEnhancedClient();
    }

    public T getItem(T item) {
        return this.tableWrapper.getItem(item);
    }

    public void putItem(T item) {
        this.tableWrapper.putItem(item);
    }

    public String tableName(){
        return this.tableWrapper.tableName();
    }
    public void updateItem(T item) {
        this.tableWrapper.updateItem(item);
    }

    public void deleteItem(T item) {
        this.tableWrapper.deleteItem(item);
    }

    public PageIterable<T> query(QueryEnhancedRequest request) {
        return this.tableWrapper.queryTable(request);
    }

    public void init(String tableName, Class<T> itemClass) {
        this.tableWrapper = new DynamoDBTableWrapper<>(enhancedClient.table(tableName, TableSchema.fromBean(itemClass)));
    }
    public static void initDynamoDbEnhancedClient() {
        AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleSessionName("kda_app_isptodynamo_market-dev")
                .roleArn("arn:aws:iam::679627425763:role/Testing_DynamoDB")
                .build();

        StsClient stsClient = StsClient.builder()
                .region(Region.of("eu-west-1"))
                .build();

        StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder()
                .stsClient(stsClient)
                .refreshRequest(assumeRoleRequest)
                .build();


        // Create Async Client
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        DynamoDbClient client = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .overrideConfiguration(createDynamoDBClientConfiguration())
                .region(Region.of("eu-west-1"))
                .build();

        // Create Enhanced Async Client, Passing Async Client
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        log.warn("init DynamoClient assuming this role: "+"arn:aws:iam::679627425763:role/Testing_DynamoDB");

    }

    private static ClientOverrideConfiguration createDynamoDBClientConfiguration() {
        return ClientOverrideConfiguration.builder().apiCallAttemptTimeout(Duration.ofSeconds(30)).apiCallTimeout(Duration.ofSeconds(30)).build();
    }
}
