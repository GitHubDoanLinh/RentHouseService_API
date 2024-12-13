package com.example.renthouseweb_be.response;

public class LogoutResponse extends BaseResponse{
    public LogoutResponse(boolean success, String messageCode) {
        super(success, messageCode);
    }
}
