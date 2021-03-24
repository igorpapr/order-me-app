package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.GoodsAvailabilitiesKey;
import com.orderme.ordermebackend.model.entity.GoodsAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsAvailabilityRepository extends JpaRepository<GoodsAvailability, GoodsAvailabilitiesKey> {

//    Optional<GoodsAvailability> findByGoodsAvailabilitiesIdShopIdAndGoodsAvailabilitiesIdGoodsId(Integer shopId, UUID goodsId);
//
//    Optional<GoodsAvailability> findByGoodsAvailabilitiesId(GoodsAvailabilitiesKey id);

}
