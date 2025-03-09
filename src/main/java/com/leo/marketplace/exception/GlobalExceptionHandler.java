package com.leo.marketplace.exception;

import com.leo.marketplace.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", ex.getMessage(),
                "code", ex.getCode(),
                "description", ex.getDescription()  // Include the description field
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(Map.of(
                "error", "Internal server error",
                "message", ex.getMessage(),
                "code", ErrorCode.SYSTEM_ERROR.getCode()
        ));
    }
}
