package com.store.repos;

import com.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.store.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    //Category findByName_of_pic(String name_of_pic);
    Category findById(Integer id);
    @Query(value = "SELECT * FROM categories WHERE name_of_pic = ?1", nativeQuery = true)
    Category findCategoryByName_of_pic(String name_of_pic);
}
