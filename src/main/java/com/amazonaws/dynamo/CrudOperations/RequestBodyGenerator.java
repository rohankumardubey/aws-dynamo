package com.amazonaws.dynamo.CrudOperations;

import com.amazonaws.dynamo.Bean.CustomerProfileSampleFields;
import com.amazonaws.dynamo.Encoder.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBodyGenerator {
    public static String generatePostBody(DatabaseCRUDOperation<Long> databaseCRUDOperation) throws JsonProcessingException {
        Map<String,Object> fieldsToModify = databaseCRUDOperation.getDatabaseFieldsToModify();
        Map<String,String> fieldsToModifyUpdated = new HashMap<>();
        fieldsToModify.forEach((k,v) -> {
            fieldsToModifyUpdated.put(k, String.valueOf(v));
        });
        Long rowId = databaseCRUDOperation.getRowId();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put(CustomerProfileSampleFields.userIntId,rowId);
        String widKeyTemp = Converter.encodeWebId(rowId);
        StringBuilder widKeyTempReverse = new StringBuilder().append(widKeyTemp).reverse();
        rootNode.put(CustomerProfileSampleFields.widKey, String.valueOf(widKeyTempReverse));
        rootNode.put(CustomerProfileSampleFields.ts,String.valueOf(databaseCRUDOperation.getCrudTimestamp()));
        fieldsToModifyUpdated.forEach(rootNode::put);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }


    public static String generateGetBody(DatabaseCRUDOperation<Long> databaseCRUDOperation) throws JsonProcessingException, ParseException {
        List<String> fieldsToRetrieve = databaseCRUDOperation.getDatabaseFieldsToRetrieve();
        String fieldString = null;
        StringBuilder builder = new StringBuilder();
        if(fieldsToRetrieve!=null){
            for (String field : fieldsToRetrieve) {
                builder.append(field);
                builder.append(",");
            }
            fieldString = builder.substring(0, builder.toString().length() - 1);
        }
        String timestamp;
        Long rowId;
        if(databaseCRUDOperation.getCrudTimestamp() == null){
            timestamp = String.valueOf(System.currentTimeMillis());
        } else{
            timestamp = String.valueOf(databaseCRUDOperation.getCrudTimestamp());
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        if(databaseCRUDOperation.getRowId()!=null){
            rowId = databaseCRUDOperation.getRowId();
            rootNode.put(CustomerProfileSampleFields.userIntId, rowId);
        }

        if (timestamp != null) {
            rootNode.put(CustomerProfileSampleFields.ts, timestamp);
        }
        if(fieldString!=null){
            rootNode.put(CustomerProfileSampleFields.fields, fieldString);
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }


    public static String generatePutBody(Map<String,String> data,DatabaseCRUDOperation<Long> databaseCRUDOperation) throws IOException {
        Map<String,Object> fieldsToModify = databaseCRUDOperation.getDatabaseFieldsToModify();
        for (Map.Entry<String, Object> entry : fieldsToModify.entrySet()) {
            if(data.containsKey(entry.getKey())){
                data.put(entry.getKey(),(String)entry.getValue());
            }
        }
        data.put("ts", String.valueOf(databaseCRUDOperation.getCrudTimestamp()));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }


    public static String generateDeleteBody(DatabaseCRUDOperation<Long> databaseCRUDOperation) throws JsonProcessingException {
        Map<String,Object> fieldsToModify = databaseCRUDOperation.getDatabaseFieldsToModify();
        Map<String,String> fieldsToModifyUpdated = new HashMap<>();
        fieldsToModify.forEach((k,v) -> {
            fieldsToModifyUpdated.put(k, " ");
        });
        Long rowId = databaseCRUDOperation.getRowId();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put(CustomerProfileSampleFields.userIntId,rowId);
        rootNode.put(CustomerProfileSampleFields.ts,String.valueOf(databaseCRUDOperation.getCrudTimestamp()));
        fieldsToModifyUpdated.forEach(rootNode::put);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}
