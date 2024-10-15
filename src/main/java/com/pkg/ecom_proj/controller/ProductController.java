package com.pkg.ecom_proj.controller;

import com.pkg.ecom_proj.model.Product;
import com.pkg.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@Controller
@CrossOrigin
public class ProductController {
    @RequestMapping("/")
    public String greet(){
        return "Hello World";
    }

    @Autowired
    ProductService service;
    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
      List<Product> pr=   service.getAllProducts();
      if(pr!=null){
          return new ResponseEntity<>(pr, HttpStatus.OK);
        }else{
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }

    }
    @RequestMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product p= service.getProduct(id);
        if(p!=null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }
    @GetMapping("/product/{prodID}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int prodID) {
        Product product = service.getProduct(prodID);
        byte[] arr=product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(arr);
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        if(service.getProduct(id)!=null){
            service.DeleteProdById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product prod=null;
        try{
            prod=service.updateProd(id,product,imageFile);

        }
        catch(IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(prod!=null){
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product failed to updated", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword){
        List<Product>p1=service.searchProduct(keyword);

        if(p1!=null){
            return new ResponseEntity<>(p1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/filter/{opt}")
    public ResponseEntity<List<Product>> filter(@PathVariable int opt){
        if(opt==1){
            List<Product> pro=service.getProductByPrice();
            for(int i=0;i<pro.size();i++){
                System.out.println(pro.get(i).getPrice());

            }
            return new ResponseEntity<>(service.getProductByPrice(),HttpStatus.OK);
        }else if(opt==2){
            return new ResponseEntity<>(service.getProductByDate(),HttpStatus.OK);
        }else if(opt==3){
            return new ResponseEntity<>(service.getProductByAvilability(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


}
