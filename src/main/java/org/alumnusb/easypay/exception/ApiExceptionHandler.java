package org.alumnusb.easypay.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.Instant;
import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.application.name}")
    private String serviceName;

    private final ObjectMapper objectMapper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(), Instant.now(), this.toHttpCode(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.BAD_REQUEST, toHttpCode(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ApiError> handleValidationException(ValidationException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.PRECONDITION_FAILED, toHttpCode(HttpStatus.PRECONDITION_FAILED.value())), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.NOT_FOUND, toHttpCode(HttpStatus.NOT_FOUND.value())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    protected ResponseEntity<ApiError> handleConflictException(ResourceConflictException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.CONFLICT, toHttpCode(HttpStatus.CONFLICT.value())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    protected ResponseEntity<ApiError> handleForbiddenException(ResourceForbiddenException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.FORBIDDEN, toHttpCode(HttpStatus.FORBIDDEN.value())), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiError> handleSpringAccessDenied(org.springframework.security.access.AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.FORBIDDEN, toHttpCode(HttpStatus.FORBIDDEN.value())), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiError> handleServiceTempUnavailableException(IllegalStateException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(ex, HttpStatus.PRECONDITION_FAILED, toHttpCode(HttpStatus.PRECONDITION_FAILED.value())), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<ApiError> handleCustomValidationException(CustomValidationException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiError.createApiError(
                        ex,
                        HttpStatus.PRECONDITION_FAILED,
                        ex.getCode() != null ? toCustomCode(ex.getCode()) : toHttpCode(HttpStatus.PRECONDITION_FAILED.value())
                ),
                HttpStatus.PRECONDITION_FAILED
        );
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    protected ResponseEntity<ApiError> handleInvalidFormatException(InvalidFormatException ex) {
            return new ResponseEntity<>(
                    ApiError.createApiError(
                            ex,
                            HttpStatus.BAD_REQUEST,
                            toHttpCode(HttpStatus.BAD_REQUEST.value())
                    ),
                    HttpStatus.BAD_REQUEST
            );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ApiError> handleClientErrorException(HttpClientErrorException ex, WebRequest request) {
        String responseError = ex.getResponseBodyAsString();
        ApiError response;
        try {
            response = objectMapper.readValue(responseError, ApiError.class);
        } catch (IOException e) {
            log.debug("Unable to serialize exception as standard ApiError instance {} ", e);
            response = ApiError
                    .builder()
                    .title(ex.getMessage())
                    .message(ex.getResponseBodyAsString())
                    .status(ex.getStatusCode().value())
                    .timestamp(Instant.now())
                    .code(toHttpCode(ex.getStatusCode().value()))
                    .build();
        }
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    private String toHttpCode(Integer httpPhrase) {
        return String.format("%s_%s_%s", this.serviceName, "HTTP", httpPhrase)
                .replace(" ", "_")
                .toUpperCase(Locale.US);
    }

    private String toCustomCode(Integer code) {
        return String.format("%s_%s", this.serviceName, code.toString())
                .replace(" ", "_")
                .toUpperCase(Locale.US);
    }
}
