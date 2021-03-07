package com.backend.catalogproducts.resources;

import static java.lang.Double.MAX_VALUE;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backend.catalogproducts.dto.ProductDTO;
import com.backend.catalogproducts.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductResource {

	@Autowired
    private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
		dto = productService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id , @Valid @RequestBody ProductDTO dto){
		dto = productService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
 
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto =  productService.fidById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
    	List<ProductDTO> dto =  productService.findAll();
    	return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search( //
    		@RequestParam(required = false) String q, //
    		@RequestParam(name = "min_price", defaultValue = "0.1") Double minPrice, //
    		@RequestParam(name = "max_price", defaultValue = "99999999.99") Double maxPrice ){ //
    	
    	List<ProductDTO> dto = productService.search(q, minPrice, maxPrice);
    	
    	return ResponseEntity.ok().body(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
