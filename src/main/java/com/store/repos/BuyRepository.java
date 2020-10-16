package com.store.repos;

import com.store.domain.Basket;
import com.store.domain.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Integer> {
}
