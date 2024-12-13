package com.example.renthouseweb_be.response;

public class VerifyTokenResponse extends BaseResponse{
    public VerifyTokenResponse(boolean success, String messageCode) {
        super(success, messageCode);
    }
}
