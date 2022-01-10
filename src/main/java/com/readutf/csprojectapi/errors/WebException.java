package com.readutf.csprojectapi.errors;

import org.springframework.stereotype.Controller;


public class WebException extends RuntimeException {

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
    }
}
