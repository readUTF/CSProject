package com.readutf.csprojectapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(HttpStatus.CHECKPOINT)
public class WebException extends RuntimeException {

    String errorCode;
    String invalidData;

    /**
     * This is an exception returned within rest api mappings and is used by spring to return information when invalid data
     * is provided to the api. The two parameters are appended together so that the client interacting wit the api
     * can determine the type of error, as well as the invalid data, saving the user having to save a local copy of data being sent.
     *
     * @param errorCode   the error code relating to the exception
     * @param invalidData the data originally provided to the api
     */
    public WebException(String errorCode, String invalidData) {
        super(errorCode + ":" + invalidData);
        this.errorCode = errorCode;
        this.invalidData = invalidData;
    }

    public HashMap<String, String> getData() {
        return new HashMap<>() {{put("error_code", errorCode); put("invalid_data", invalidData);}};
    }
}
