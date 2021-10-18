package com.example.oauth1;

public interface SignatureService
{
    public String getSignature(String baseString, String apiSecret, String tokenSecret);
    public String getSignatureMethod();
}