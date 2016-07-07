package com.oceancx;

public class Main {

    public static void main(String[] args) {
        LzHttpClient client = LzHttpClient.get();
        client.getRequest("http://www.xiaomi.com");
    }
}
