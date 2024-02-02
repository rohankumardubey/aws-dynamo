package com.amazonaws.dynamo.CrudOperations;

import java.util.Arrays;
import java.util.List;

public class StringParser{
    public static void main(String[] args) {
        String path = "s3a://intg-transformation-sink-823554533179-eu-west-1/chargeback/aggscorer-models/sentinel.csv";
        String[] splitString = path.split("//")[1].split("/");
        String bucketName = splitString[0];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < splitString.length; i++) {
            stringBuilder.append(splitString[i]);
            stringBuilder.append("/");
        }
        String filename =  stringBuilder.substring(0,stringBuilder.length()-1);
        System.out.println(bucketName);
        System.out.println(filename);

    }
}
