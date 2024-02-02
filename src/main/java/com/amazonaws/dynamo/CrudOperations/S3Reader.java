//package com.amazonaws.dynamo.CrudOperations;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//public class S3Reader {
//    public static void main(String[] args) throws IOException {
//        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
//
//        // s3://aws-glue-assets-679627425763-eu-west-1/scripts/Compact_deduplicate.py
//        // Create an S3Object object using the bucket name and key of the file you want to read
//        S3Object s3Object = s3Client.getObject("aws-glue-assets-679627425763-eu-west-1", "scripts/Compact_deduplicate.py");
//
//        // Create a buffer reader using the S3Object object and read the file line by line
//        BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//
//        // Close the buffer reader
//        reader.close();
//    }
//}
