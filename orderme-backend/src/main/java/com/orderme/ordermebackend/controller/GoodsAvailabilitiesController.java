package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.service.GoodsAvailabilityService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathRoutes.PATH_GOODS_AVAILABILITIES)
public class GoodsAvailabilitiesController {

    private final GoodsAvailabilityService goodsAvailabilityService;

    private final DtoValidationService<GoodsAvailabilityDto> goodsAvailabilityDtoValidationService;

    public GoodsAvailabilitiesController(GoodsAvailabilityService goodsAvailabilityService,
                                         DtoValidationService<GoodsAvailabilityDto> goodsAvailabilityDtoValidationService) {
        this.goodsAvailabilityService = goodsAvailabilityService;
        this.goodsAvailabilityDtoValidationService = goodsAvailabilityDtoValidationService;
    }

    @GetMapping
    public ResponseEntity<?> getByIds(@RequestParam Integer shopId,
                                      @RequestParam UUID goodsId) {
        return new ResponseEntity<>(goodsAvailabilityService.getByShopIdAndGoodsId(shopId, goodsId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addGoodsAvailability(@RequestBody GoodsAvailabilityDto goodsAvailabilityDto,
                                                  @RequestParam Integer shopId,
                                                  @RequestParam UUID goodsId) {
        goodsAvailabilityDtoValidationService.validateCreate(goodsAvailabilityDto);
        return new ResponseEntity<>(goodsAvailabilityService.createByShopIdAndGoodsId(goodsAvailabilityDto, shopId, goodsId),
                HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> patchByIds(@RequestBody GoodsAvailabilityDto goodsAvailabilityDto,
                                        @RequestParam Integer shopId,
                                        @RequestParam UUID goodsId) {
        return new ResponseEntity<>(goodsAvailabilityService.patchByShopIdAndGoodsId(goodsAvailabilityDto, shopId, goodsId),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByIds(@RequestParam Integer shopId,
                                         @RequestParam UUID goodsId) {
        goodsAvailabilityService.deleteByShopIdAndGoodsId(shopId, goodsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
