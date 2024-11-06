package com.example.renthouseweb_be.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<String, String> SystemMessage;
    static {
        SystemMessage = new HashMap<>();
        SystemMessage.put("MS-HO-01", "Create house success");
        SystemMessage.put("ER-HO-01", "Create house fail");

    }
}
