package www.ariandasilvaperez.spend_sense.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import www.ariandasilvaperez.spend_sense.product.models.dto.ProductDTO;
import www.ariandasilvaperez.spend_sense.product.services.IProductServices;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

    @Autowired
    IProductServices productServices;

    @PostMapping(value = "/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.addProduct(product, authentication));
    }

    @PutMapping(value = "/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(productServices.updateProduct(id, product, authentication));
    }

    @DeleteMapping(value = "product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Authentication authentication){
        productServices.deleteProduct(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

}
