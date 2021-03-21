package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.service.GoodsService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import com.orderme.ordermebackend.service.validation.impl.GoodsValidationServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.orderme.ordermebackend.controller.utils.PathRoutes.PATH_GOODS;

@RestController
@RequestMapping(PATH_GOODS)
public class GoodsController {

    private final GoodsService goodsService;

    private final DtoValidationService<GoodsDto> goodsValidationService;

    public GoodsController(GoodsService goodsService, GoodsValidationServiceImpl goodsValidationService) {
        this.goodsService = goodsService;
        this.goodsValidationService = goodsValidationService;
    }

    //pageable - page, size https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.web
    @GetMapping
    public ResponseEntity<?> getAllGoods(Pageable pageable) {
        return new ResponseEntity<>(goodsService.getAllByPageable(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoodsById(@PathVariable UUID id) {
        return new ResponseEntity<>(goodsService.getById(id), HttpStatus.OK);
    }

    @GetMapping(PathRoutes.CHILD_PATH_GOODS_TYPE + "/{goodsTypeId}")
    public ResponseEntity<?> getGoodsByGoodsType(@PathVariable Integer goodsTypeId, Pageable pageable) {
        return new ResponseEntity<>(goodsService.getByGoodsTypeId(goodsTypeId, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewGoods(@RequestBody GoodsDto goodsDto) {
        goodsValidationService.validateCreate(goodsDto);
        return new ResponseEntity<>(goodsService.create(goodsDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@RequestBody GoodsDto goodsDto, @PathVariable UUID id) {
        goodsValidationService.validatePatch(goodsDto);
        return new ResponseEntity<>(goodsService.patch(goodsDto, id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable UUID id) {
        goodsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
