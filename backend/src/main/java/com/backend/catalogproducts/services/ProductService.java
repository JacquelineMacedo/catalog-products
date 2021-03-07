package com.backend.catalogproducts.services;

import static com.backend.catalogproducts.entities.Product.builder;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogproducts.dto.ProductDTO;
import com.backend.catalogproducts.entities.Product;
import com.backend.catalogproducts.repositories.ProductRepository;
import com.backend.catalogproducts.services.exceptions.DatabaseException;
import com.backend.catalogproducts.services.exceptions.ObjectNotFoundException;
import com.backend.catalogproducts.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	
	private void updateData(ProductDTO dto, Product entity) {
		entity.setName(dto.getName()); 
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = builder() //
				.name(dto.getName()) //
				.description(dto.getDescription()) //
				.price(dto.getPrice()) //
				.build();
		
		entity = productRepository.save(entity);
		
		return ProductDTO.builder() //
				.id(entity.getId()) //
				.name(entity.getName()) //
				.description(entity.getDescription()) //
				.price(entity.getPrice()) //
				.build();
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getOne(id);
			
			updateData(dto, entity);
			
			entity = productRepository.save(entity);
			
			return ProductDTO.builder() //
					.id(entity.getId()) //
					.name(entity.getName()) //
					.description(entity.getDescription()) //
					.price(entity.getPrice()) //
					.build();
		
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id: " + id);
		}
	}
	
	@Transactional(readOnly = true)
	public ProductDTO fidById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ObjectNotFoundException("Produto não Encontrada!"));
		
		return ProductDTO.builder() //
				.id(entity.getId()) //
				.name(entity.getName()) //
				.description(entity.getDescription()) //
				.price(entity.getPrice()) //
				.build();
	}
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		
		return productRepository //
				.findAll() //
				.stream() //
				.map(entity -> ProductDTO.builder() //
						.id(entity.getId()) //
						.name(entity.getName()) //
						.description(entity.getDescription()) //
						.price(entity.getPrice()) //
						.build()) //
				.collect(toList());
		
	}
	
	@Transactional(readOnly = true)
	public List<ProductDTO> search(String q, Double minPrice, Double maxPrice) {
		
		if(q != null && !q.isEmpty()) {
			
			return productRepository.search(q, minPrice, maxPrice) //
					.stream() //
					.map(entity -> ProductDTO.builder() //
							.id(entity.getId()) //
							.name(entity.getName()) //
							.description(entity.getDescription()) //
							.price(entity.getPrice()) //
							.build()) //
					.collect(toList());
		}
		
		return productRepository.search(minPrice, maxPrice) //
				.stream() //
				.map(entity -> ProductDTO.builder() //
						.id(entity.getId()) //
						.name(entity.getName()) //
						.description(entity.getDescription()) //
						.price(entity.getPrice()) //
						.build()) //
				.collect(toList());
		
	}	

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Id: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrid Vaiolation!");
		}
	}

}
