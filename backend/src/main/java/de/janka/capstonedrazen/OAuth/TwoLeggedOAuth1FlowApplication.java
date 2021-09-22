package de.janka.capstonedrazen.OAuth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwoLeggedOAuth1FlowApplication {
    private static final String CONSUMER_KEY = "Bootcamp CapstoneKey";
    private static final String CONSUMER_SECRET = "1BXdj7bdFYr7wbAd";
    private static final String PROTECTED_RESOURCE_ENDPOINT = "https://rest.sandbox-immobilienscout24.de/restapi/api/";


    public static void main(String[] args) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        // create a consumer object and configure it with the access
        // token and token secret obtained from the service provider
        OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

        // create an HTTP request to a protected resource endpoint
        URL url = new URL(PROTECTED_RESOURCE_ENDPOINT);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        // sign the request
        consumer.sign(request);

        // send the request
        request.connect();
        printResponse(request);
    }

    private static void printResponse(HttpURLConnection request) throws IOException {
        System.out.println(request.getResponseCode());
        InputStream in;
        try {
            in = request.getInputStream();
        } catch (IOException e) {
            in = request.getErrorStream();
        }

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
        String inputLine;
        while ((inputLine = inputReader.readLine()) != null) {
            System.out.println(inputLine);
        }
        inputReader.close();
    }
}