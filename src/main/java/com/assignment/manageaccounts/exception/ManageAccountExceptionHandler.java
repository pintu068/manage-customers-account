package com.assignment.manageaccounts.exception;

import com.assignment.manageaccounts.constants.TechnicalErrors;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.util.Utility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;



/**
 * Exception handler class for all REST APIs
 */


@ControllerAdvice
@EnableWebMvc
public class ManageAccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    public ResponseEntity<String> parsingExceptionHandler(HttpMessageConversionException e) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.BAD_REQUEST.getErrorCode(), TechnicalErrors.BAD_REQUEST.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class, ManageAccountsException.class})
    public ResponseEntity<String> defaultExceptionHandler(Exception e) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.INTERNAL_SERVER_ERROR.getErrorCode(), TechnicalErrors.INTERNAL_SERVER_ERROR.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.BAD_REQUEST.getErrorCode(), TechnicalErrors.BAD_REQUEST.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.BAD_REQUEST.getErrorCode(), TechnicalErrors.BAD_REQUEST.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.BAD_REQUEST.getErrorCode(), TechnicalErrors.BAD_REQUEST.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<ErrorRecord> errorRecordList = new ArrayList<>();
        ErrorRecord errorRecord = new ErrorRecord(TechnicalErrors.PARAMETER_MISSING.getErrorCode(), TechnicalErrors.PARAMETER_MISSING.getErrorMessage());
        errorRecordList.add(errorRecord);
        return new ResponseEntity(Utility.getErrorResponse(null, errorRecordList), HttpStatus.BAD_REQUEST);

    }
}
