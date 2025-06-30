package com.ecomarket.Product_Service.repository;



import com.ecomarket.Product_Service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category); // Funcionalidad adicional
}
