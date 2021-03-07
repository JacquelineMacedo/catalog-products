package com.backend.catalogproducts.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -1614116647419805036L;

	public ObjectNotFoundException(String msg){
        super(msg);
    }
}
