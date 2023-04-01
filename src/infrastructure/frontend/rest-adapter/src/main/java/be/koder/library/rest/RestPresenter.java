package be.koder.library.rest;

import org.springframework.http.ResponseEntity;

public abstract class RestPresenter {

    private ResponseEntity<Object> response;

    protected void setResponse(ResponseEntity<Object> response) {
        this.response = response;
    }

    public ResponseEntity<Object> getResponse() {
        return response;
    }
}