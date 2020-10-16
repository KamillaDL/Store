package com.store.repos;

import com.store.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findByUser_id(Integer user_id);
    Optional<Basket> findById(Integer Id);
    List<Basket> findByProduct_id(Integer product_id);
    List<Basket> findByProduct_idAndUser_id(Integer product_id, Integer user_id);
}
