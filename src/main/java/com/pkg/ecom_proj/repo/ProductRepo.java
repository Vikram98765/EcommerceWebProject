package com.pkg.ecom_proj.repo;

import com.pkg.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT P FROM Product P WHERE " +
            "LOWER(P.name) LIKE LOWER(CONCAT('%',:keyword,'%')) " +
            "OR LOWER(P.description) LIKE LOWER(CONCAT('%',:keyword,'%')) " +
            "OR LOWER(P.brand) LIKE LOWER(CONCAT('%',:keyword,'%')) ")

    List<Product> search(String keyword);

    @Query("SELECT P FROM Product P ORDER BY  P.price desc ")
    List<Product> getProductByPrice();

    @Query("SELECT P FROM Product P ORDER BY  P.releaseDate desc ")
    List<Product> getProductByDate();

    @Query("SELECT P FROM Product  P where P.productAvailable=true ")
    List<Product> getProductByAvail();
}
