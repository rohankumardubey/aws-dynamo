package com.amazonaws.dynamo;

import com.amazonaws.dynamo.Bean.Music;
import com.amazonaws.dynamo.util.DynamoDBClientWrapper;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Music music = new Music();
        music.setArtist("artist 1");
        music.setSongTitle("song album 1");
        music.setNewValueAlbumTitle("album title 1");

        Music musicUpdate = new Music();
        musicUpdate.setArtist("artist 1");
        musicUpdate.setSongTitle("song album *");
//        musicUpdate.setNewValueAlbumTitle("album title 1");

        DynamoDBClientWrapper<Music> client = new DynamoDBClientWrapper<>();
        client.init("Music",Music.class);
        try {
//            for (int i = 0; i < 5; i++) {
//                client.putItem(dataGenerator(i));
//            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Item value are ---- "+getTableItem(client,music));
            System.out.println("Table name is ---- "+getTableName(client));
            System.out.println("Table value updated!!");

//            updateValue(client,queryValue(client,music.getArtist(),music.getSongTitle()),musicUpdate);
            System.out.println("Item updated value is ---- "+getTableItem(client,musicUpdate));

            queryValue(client,"artist 1","song album 1");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Music dataGenerator(int index){
        Music music = new Music();
        music.setArtist("artist "+index);
        music.setSongTitle("song album "+index);
        music.setNewValueAlbumTitle("album title "+index);
        return music;
    }

    public static Music getTableItem(DynamoDBClientWrapper<Music> client,Music music) throws ExecutionException, InterruptedException {
        return client.getItem(music);
    }

    public static String getTableName(DynamoDBClientWrapper<Music> client){
        return client.tableName();
    }

    public static void updateValue(DynamoDBClientWrapper<Music> client,Music oldMusic,Music newMusic){


        Music initialValue = client.getItem(oldMusic);
        if(initialValue!=null) {
            String artist = initialValue.getArtist();
            String songTitle = initialValue.getSongTitle();
            String newValueAlbumTitle = initialValue.getNewValueAlbumTitle();


            String newArtist = newMusic.getArtist();
            String newSongTitle = newMusic.getSongTitle();
            String newNewValueAlbumTitle = newMusic.getNewValueAlbumTitle();

            Music musicFinal = new Music();
            if (newArtist != null) {
                newArtist = newMusic.getArtist();
                musicFinal.setArtist(newArtist);
            } else {
                newArtist = artist;
                musicFinal.setArtist(newArtist);
            }

            if (newSongTitle != null) {
                newSongTitle = newMusic.getSongTitle();
                musicFinal.setSongTitle(newSongTitle);
            } else {
                newSongTitle = songTitle;
                musicFinal.setSongTitle(newSongTitle);
            }

            if (newNewValueAlbumTitle != null) {
                newNewValueAlbumTitle = newMusic.getNewValueAlbumTitle();
                musicFinal.setNewValueAlbumTitle(newNewValueAlbumTitle);
            } else {
                newArtist = newValueAlbumTitle;
                musicFinal.setNewValueAlbumTitle(newArtist);
            }

            client.updateItem(musicFinal);
            client.deleteItem(oldMusic);
        }
        else{
            client.putItem(oldMusic);
        }


    }

    public static Music queryValue(DynamoDBClientWrapper<Music> client, String partitionKey, String sortKey){
        QueryConditional keyEqual = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(partitionKey)
                .sortValue(sortKey)
                .build());
        // 1a. Define a QueryConditional that adds a sort key criteria to the partition value criteria.
        QueryConditional sortGreaterThanOrEqualTo = QueryConditional.sortGreaterThanOrEqualTo(b -> b.partitionValue(partitionKey).sortValue(sortKey));
        // 2. Define a filter expression that filters out items whose attribute value is null.
        Expression filterOutNoActingschoolname = Expression.builder().expression("attribute_exists(NewValueAlbumTitle)").build();


        QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
                .queryConditional(keyEqual)
                .filterExpression(filterOutNoActingschoolname)
                .build();

        PageIterable<Music> pagedResults = client.query(tableQuery);
        List<Music> sdk = pagedResults.items().stream().collect(Collectors.toList());
        return sdk.get(0);
    }

    public static List<Music> queryWithoutSortValue(DynamoDBClientWrapper<Music> client, String partitionKey){
        QueryConditional keyEqual = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(partitionKey)
                .build());
        // 1a. Define a QueryConditional that adds a sort key criteria to the partition value criteria.
        QueryConditional sortGreaterThanOrEqualTo = QueryConditional.sortGreaterThanOrEqualTo(b -> b.partitionValue(partitionKey));
        // 2. Define a filter expression that filters out items whose attribute value is null.
        final Expression filterOutNoActingschoolname = Expression.builder().expression("attribute_exists(NewValueAlbumTitle)").build();


        QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
                .queryConditional(keyEqual)
                .filterExpression(filterOutNoActingschoolname)
                .build();

        PageIterable<Music> pagedResults = client.query(tableQuery);
        return pagedResults.items().stream().collect(Collectors.toList());
    }
}
