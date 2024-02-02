package com.amazonaws.dynamo.Encoder;

public class ConverterExample {
    public static void main(String[] args) {
        System.out.println(Converter.encodeWebId(1234567L));
        System.out.println(Converter.decodeWebId("LUWOK"));
    }
}
