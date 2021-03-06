package com.joaopedro.librarymanager.exception;

import com.joaopedro.librarymanager.exception.aluguel.AluguelDataNotValidException;
import com.joaopedro.librarymanager.exception.livro.LivroInsufficientQuantity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<Object> handlerEntityExistsException(EntityExistsException exception) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Object> handlerEntityNotFoundException(EntityNotFoundException exception) {
        return buildResponseEntity(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(EntityCanNotBeDeletedException.class)
    private ResponseEntity<Object> handlerEntityCanNotBeDeleted(EntityCanNotBeDeletedException exception) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(AluguelDataNotValidException.class)
    public ResponseEntity<Object> handleAluguelDataNotValid(AluguelDataNotValidException exception) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(LivroInsufficientQuantity.class)
    public ResponseEntity<Object> handleLivroInsufficientQuantity(LivroInsufficientQuantity exception) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> { errors.add("Field:" + fieldError.getField().toUpperCase() + " - " + fieldError.getDefaultMessage()); });
        exception.getBindingResult().getGlobalErrors()
                .forEach(globalError -> { errors.add("Object: " + globalError.getObjectName() + " - " + globalError.getDefaultMessage()); });

        return buildResponseEntity(HttpStatus.BAD_REQUEST, "", errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON BODY and/or found error",
                Collections.singletonList(exception.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
        ApiError apiError = ApiError.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
