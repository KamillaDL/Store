package com.store.repos;

import com.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_id(Integer category_id);
    Product findById(Integer id);
    @Query(value = "SELECT * FROM products WHERE LOWER(name_of_item) LIKE LOWER(?1)", nativeQuery = true)
    List<Product> findProductsByName_of_item(String name_of_item);
//    @Query(value = "SELECT * FROM products WHERE (name_of_item) LIKE %?1%", nativeQuery = true)

}