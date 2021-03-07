package com.backend.catalogproducts.resources.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StanderError implements Serializable {
	private static final long serialVersionUID = 389942791224578492L;
	
	private Integer statusCode;
    private String message;
}
