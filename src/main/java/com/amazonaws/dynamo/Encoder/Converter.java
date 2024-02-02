package com.amazonaws.dynamo.Encoder;

public class Converter {
    private static final String toAlphaBet = "PQLAMZWOSKXNBCDJEIURHFGVTY";
    private static String fromAlphaBet;

    public Converter() {
    }

    private static String mapChars(String fromString, String charMap) {
        StringBuilder sb = new StringBuilder();
        char[] var3 = fromString.toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            sb.append(charMap.charAt(c - 65));
        }

        return sb.toString();
    }

    public static int decodeWebId(String webId) {
        int userIntId = 0;
        char[] var2 = mapChars(webId, fromAlphaBet).toCharArray();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            userIntId = userIntId * 26 + c - 65;
        }

        return userIntId;
    }

    public static String encodeWebId(long userIntId) {
        StringBuilder sb;
        for(sb = new StringBuilder(); userIntId != 0L; userIntId /= 26L) {
            sb.append((char)((int)(65L + userIntId % 26L)));
        }
        return mapChars(sb.reverse().toString(), toAlphaBet);
    }

    static {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < toAlphaBet.length(); ++i) {
            sb.append((char)(65 + toAlphaBet.indexOf(65 + i)));
        }

        fromAlphaBet = sb.toString();
    }
}
