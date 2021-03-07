package com.backend.catalogproducts.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 8149248097751757264L;

	public ResourceNotFoundException(String msg){
        super(msg);
    }
}
