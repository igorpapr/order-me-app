package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
