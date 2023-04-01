package be.koder.library.rest;

import org.springframework.http.ResponseEntity;

public abstract class RestPresenter {

    protected ResponseEntity<Object> response;

    public ResponseEntity<Object> getResponse() {
        return response;
    }
}