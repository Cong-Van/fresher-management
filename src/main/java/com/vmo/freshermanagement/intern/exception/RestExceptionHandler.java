package com.vmo.freshermanagement.intern.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.vmo.freshermanagement.intern.common.HttpResponse;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exc) {
        return createHttpResponse(FORBIDDEN, exc.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exc) {
        HttpMethod supportedMethod = Objects.requireNonNull(exc.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOW, supportedMethod));
    }

    @ExceptionHandler(FresherNotFoundException.class)
    public ResponseEntity<HttpResponse> fresherNotFoundException(FresherNotFoundException exc) {
        return createHttpResponse(BAD_REQUEST, exc.getMessage());
    }

    @ExceptionHandler(CenterNotFoundException.class)
    public ResponseEntity<HttpResponse> centerNotFoundException(CenterNotFoundException exc) {
        return createHttpResponse(BAD_REQUEST, exc.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exc) {
        return createHttpResponse(BAD_REQUEST, exc.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> httpMessageNotReadableException(HttpMessageNotReadableException exc) {
        return createHttpResponse(BAD_REQUEST, exc.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<HttpResponse> transactionSystemException(TransactionSystemException exc) {
        return createHttpResponse(BAD_REQUEST, exc.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exc) {
        return createHttpResponse(NOT_FOUND, exc.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exc) {
        LOGGER.error(exc.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exc) {
        LOGGER.error(exc + "/" + exc.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
