package com.giulianodb.resources.execptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.giulianodb.service.execptions.CpfInvalideException;
import com.giulianodb.service.execptions.ObjectNotFoudException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoudException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoudException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(),status.value(), "Non trouvé", e.getMessage(),request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(CpfInvalideException.class)
	public ResponseEntity<StandardError> cpfInvalide(CpfInvalideException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(System.currentTimeMillis(),status.value(), "CPF invalide", e.getMessage(),request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
}
