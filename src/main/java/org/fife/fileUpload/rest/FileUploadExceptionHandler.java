package org.fife.fileUpload.rest;

import org.fife.fileUpload.exceptions.FileUploadException;
import org.fife.fileUpload.reps.ErrorRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class FileUploadExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorRep> appException(FileUploadException e, HttpServletRequest request) {

        ErrorRep error = new ErrorRep();
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
