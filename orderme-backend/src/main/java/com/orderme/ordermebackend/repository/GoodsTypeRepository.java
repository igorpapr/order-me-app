package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsTypeRepository extends JpaRepository<GoodsType, Integer> {
}
