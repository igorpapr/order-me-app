package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.service.GoodsService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import com.orderme.ordermebackend.service.validation.impl.GoodsValidationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.orderme.ordermebackend.controller.utils.PathRoutes.PATH_GOODS;

@RestController
@RequestMapping(PATH_GOODS)
@CrossOrigin
public class GoodsController {

    private final GoodsService goodsService;

    private final DtoValidationService<GoodsDto> goodsValidationService;

    public GoodsController(GoodsService goodsService, GoodsValidationServiceImpl goodsValidationService) {
        this.goodsService = goodsService;
        this.goodsValidationService = goodsValidationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllGoods(@RequestParam(required = false) Integer shopId,
                                         Pageable pageable) {
        Page<Goods> result;
        if (shopId != null) {
            result = goodsService.getAllByPageable(shopId, pageable);
        } else {
            result = goodsService.getAllByPageable(pageable);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoodsById(@RequestParam(required = false) Integer shopId,
                                              @PathVariable UUID id) {
        Goods result;
        if (shopId == null) {
            result = goodsService.getById(id);
        } else {
            result = goodsService.getByIdAndShopId(shopId, id);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(PathRoutes.CHILD_PATH_GOODS_TYPE + "/{goodsTypeId}")
    public ResponseEntity<Page<Goods>> getGoodsByGoodsType(@RequestParam(required = false) Integer shopId,
            @PathVariable Integer goodsTypeId, Pageable pageable) {
        return new ResponseEntity<>(goodsService.getByGoodsTypeId(shopId, goodsTypeId, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Goods> addNewGoods(@RequestBody GoodsDto goodsDto) {
        goodsValidationService.validateCreate(goodsDto);
        return new ResponseEntity<>(goodsService.create(goodsDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@RequestBody GoodsDto goodsDto, @PathVariable UUID id) {
        goodsValidationService.validatePatch(goodsDto);
        return new ResponseEntity<>(goodsService.patch(goodsDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable UUID id) {
        goodsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

//pageable - page, size https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.web
