package com.pkg.ecom_proj.service;

import com.pkg.ecom_proj.model.Product;
import com.pkg.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    ProductRepo prepo;
    public List<Product> getAllProducts(){
        return prepo.findAll();
    }

    public Product getProduct(int id) {
        return prepo.findById(id).orElse(null);
    }
    public Product addProduct(Product product, MultipartFile file) throws IOException {
        product.setImageName(file.getOriginalFilename());
        product.setImageType(file.getContentType());
        product.setImageData(file.getBytes());

        return prepo.save(product);
    }
   public boolean DeleteProdById(int id) {
        prepo.deleteById(id);
        return true;

   }
   public Product updateProd(int id,Product product, MultipartFile file) throws IOException {
        product.setImageName(file.getOriginalFilename());
        product.setImageType(file.getContentType());
        product.setImageData(file.getBytes());
        return prepo.save(product);
   }
   public List<Product> searchProduct(String keyword) {
        return prepo.search(keyword);
   }

    public List<Product> getProductByPrice() {
        return prepo.getProductByPrice();
    }


    public List<Product> getProductByDate() {
        return prepo.getProductByDate();
    }

    public List<Product> getProductByAvilability() {
        return prepo.getProductByAvail();
    }
}
