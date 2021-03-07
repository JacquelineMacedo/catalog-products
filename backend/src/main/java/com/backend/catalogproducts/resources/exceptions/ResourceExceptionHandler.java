package com.backend.catalogproducts.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.backend.catalogproducts.services.exceptions.DatabaseException;
import com.backend.catalogproducts.services.exceptions.ObjectNotFoundException;
import com.backend.catalogproducts.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StanderError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		return getStanderErrorResponseEntity("Recurso Não Encontrado. " + e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StanderError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StanderError> database(DatabaseException e, HttpServletRequest request) {
		return getStanderErrorResponseEntity(
				"Integridade com o banco, entidade possue dados relacionados!" + e.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StanderError> validate(MethodArgumentNotValidException e, HttpServletRequest request) {
		StringBuilder erroMessage = new StringBuilder();

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			erroMessage.append(fieldError.getField() + " - " + fieldError.getDefaultMessage() + ". ");
		}
		return getStanderErrorResponseEntity(erroMessage.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StanderError> genericException(Exception e, HttpServletRequest request) {
		return getStanderErrorResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<StanderError> getStanderErrorResponseEntity(String message, HttpStatus status) {
		StanderError error = new StanderError();
		error.setStatusCode(status.value());
		error.setMessage(message);
		return ResponseEntity.status(status).body(error);
	}
}
