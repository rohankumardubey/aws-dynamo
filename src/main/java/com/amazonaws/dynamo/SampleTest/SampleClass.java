package com.amazonaws.dynamo.SampleTest;

import com.amazonaws.dynamo.util.DateTimeConverter;
import org.apache.http.entity.StringEntity;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleClass {
    public static void main(String[] args) throws ParseException {
        Map<Object,Object> sample = Stream.of(new AbstractMap.SimpleEntry<>(1,2),new AbstractMap.SimpleEntry<>(3,4)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        System.out.println(sample);
        System.out.println(DateTimeConverter.dateToEpocTimestamp("2023-12-20"));
        System.out.println(System.currentTimeMillis());
//        System.out.println(DateTimeConverter.epocTimestampToDate(1738434599999L));
//        StringEntity entity = new StringEntity(Stream.of(
//                        new AbstractMap.SimpleEntry<>("tenancyName", "tenancyName"),
//                        new AbstractMap.SimpleEntry<>("usernameOrEmailAddress", "usernameOrEmailAddress"),
//                        new AbstractMap.SimpleEntry<>("password", "password"))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
