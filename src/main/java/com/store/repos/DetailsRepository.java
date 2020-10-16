package com.store.repos;

import com.store.domain.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, String> {

}
