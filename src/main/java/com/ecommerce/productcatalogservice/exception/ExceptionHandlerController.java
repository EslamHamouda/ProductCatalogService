package com.ecommerce.productcatalogservice.exception;

import com.ecommerce.productcatalogservice.dto.response.ErrorResponse;
import com.ecommerce.productcatalogservice.utils.DateUtils;
import com.ecommerce.productcatalogservice.utils.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateResourceException(DuplicateResourceException ex) {
        return new ErrorResponse(ex.getStatusCode(), ex.getMessage(), DateUtils.currentLocalDateTime());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getStatusCode(), ex.getMessage(), DateUtils.currentLocalDateTime());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        return new ErrorResponse(ex.getStatusCode(), ex.getMessage(), DateUtils.currentLocalDateTime());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getStatusCode().value(), ex.getMessage(), DateUtils.currentLocalDateTime());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleResourceAlreadyExistException(ResourceAlreadyExistException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), DateUtils.currentLocalDateTime());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        MessageConstants.UNEXPECTED_ERROR + ex.getMessage(), DateUtils.currentLocalDateTime());
    }
}
