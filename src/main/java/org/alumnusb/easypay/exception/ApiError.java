package org.alumnusb.easypay.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private String title;

    private String message;

    private int status;

    private Instant timestamp;

    protected String code;

    public static ApiError createApiError(Throwable t, HttpStatus status, String code) {
        return ApiError.builder()
                .title(status.getReasonPhrase())
                .message(t.initCause(t).getMessage())
                .status(status.value())
                .timestamp(Instant.now())
                .code(code)
                .build();
    }

}