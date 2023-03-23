package be.koder.library.rest;

import org.springframework.http.ResponseEntity;

public interface RestPresenter {
    ResponseEntity<Object> getResponse();
}