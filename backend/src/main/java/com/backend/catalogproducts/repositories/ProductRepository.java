package com.backend.catalogproducts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.catalogproducts.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Transactional(readOnly = true)
	@Query("SELECT p FROM Product p WHERE (p.name LIKE %:q% OR p.description LIKE %:q%) AND p.price >= :minPrice AND p.price <= :maxPrice")
	List<Product> search(@Param("q") String q, //
						@Param("minPrice") Double minPrice, //
						@Param("maxPrice") Double maxPrice);
	
	@Transactional(readOnly = true)
	@Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <= :maxPrice")
	List<Product> search(@Param("minPrice") Double minPrice, //
						@Param("maxPrice") Double maxPrice);
	
}

