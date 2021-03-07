package com.backend.catalogproducts.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = -652995557724499156L;

	private Long id;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String name;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String description;

	@NotNull
	@Positive(message = "Valor precisa ser positivo")
	private Double price;
}
