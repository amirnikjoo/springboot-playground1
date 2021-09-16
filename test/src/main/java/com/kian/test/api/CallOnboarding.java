package com.kian.test.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallOnboarding {

    public static void main(String[] args) throws Exception {

        // Sending get request
        URL url = new URL("https://uat.kian.digital/onboarding/api/v2/onboardings/start");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization","Bearer "+" Actual bearer token issued by provider.");
        //e.g. bearer token= eyJhbGciOiXXXzUxMiJ9.eyJzdWIiOiPyc2hhcm1hQHBsdW1zbGljZS5jb206OjE6OjkwIiwiZXhwIjoxNTM3MzQyNTIxLCJpYXQiOjE1MzY3Mzc3MjF9.O33zP2l_0eDNfcqSQz29jUGJC-_THYsXllrmkFnk85dNRbAw66dyEKBP5dVcFUuNTA8zhA83kk3Y41_qZYx43T

        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();
        // printing result from response
        System.out.println("Response:-" + response.toString());

    }
}
