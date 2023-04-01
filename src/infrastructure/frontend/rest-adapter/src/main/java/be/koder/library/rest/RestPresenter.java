package be.koder.library.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class RestPresenter {

    private ResponseEntity<Object> response;

    protected void setResponse(HttpStatus httpStatus, Object responseBody) {
        this.response = ResponseEntity.status(httpStatus).body(responseBody);
    }

    public ResponseEntity<Object> getResponse() {
        return response;
    }
}