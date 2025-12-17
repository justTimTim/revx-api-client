package com.revx.api.client;


import com.revx.api.RevxApiRestClient;
import com.revx.api.impl.RevxApiRestClientImpl;

public class RevxClientFactory {

    private String apiKey;
    private String secret;

    private RevxClientFactory(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public static RevxClientFactory newInstance(String apiKey, String secret) {
        return new RevxClientFactory(apiKey, secret);
    }

    public RevxApiRestClient newRestClient(String url) {
        return new RevxApiRestClientImpl(url, apiKey, secret);
    }


}
