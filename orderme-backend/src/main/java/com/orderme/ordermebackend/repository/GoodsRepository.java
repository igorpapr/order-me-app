package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.model.entity.GoodsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodsRepository extends JpaRepository<Goods, UUID> {

    Page<Goods> findAllByGoodsType(GoodsType goodsType, Pageable pageable);

}
