package com.example.renthouseweb_be.response;

import com.example.renthouseweb_be.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    @JsonIgnore
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = MessageCodeFilter.class)
    public String messageCode;
    public String message;
    public BaseResponse(String messageCode) {
        this.messageCode = messageCode;
    }
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
    public String getMessage() {
        return (messageCode != null) ? Constants.SystemMessage.get(messageCode) : null;
    }
    public static class MessageCodeFilter {
        @Override
        public boolean equals(Object obj) {
            return obj != null && obj.getClass() == String.class;
        }
    }
}
