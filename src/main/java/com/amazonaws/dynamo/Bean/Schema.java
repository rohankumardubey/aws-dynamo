package com.amazonaws.dynamo.Bean;

import java.util.HashMap;
import java.util.Map;

public class Schema {
    public static Map<String,Object> initializeSchema(){
        Map<String,Object> map = new HashMap<>();
        map.put("userIntId",Number.class);
        map.put("dt",String.class);
        map.put("ts",Number.class);
        map.put("widKey",String.class);

        return map;
    }
}
