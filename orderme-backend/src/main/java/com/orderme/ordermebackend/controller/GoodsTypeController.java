package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.GoodsTypeDto;
import com.orderme.ordermebackend.model.entity.GoodsType;
import com.orderme.ordermebackend.service.GoodsTypeService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathRoutes.PATH_GOODS_TYPES)
@CrossOrigin
public class GoodsTypeController {

    private final GoodsTypeService goodsTypeService;

    private final DtoValidationService<GoodsTypeDto> goodsTypeDtoValidationService;

    public GoodsTypeController(GoodsTypeService goodsTypeService,
                               DtoValidationService<GoodsTypeDto> goodsTypeDtoValidationService) {
        this.goodsTypeService = goodsTypeService;
        this.goodsTypeDtoValidationService = goodsTypeDtoValidationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllGoodsTypes() {
        return new ResponseEntity<>(goodsTypeService.getAllNotPaginated(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoodsTypeById(@PathVariable Integer id) {
        return new ResponseEntity<>(goodsTypeService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GoodsType> addNewGoods(@RequestBody GoodsTypeDto goodsTypeDto) {
        goodsTypeDtoValidationService.validateCreate(goodsTypeDto);
        return new ResponseEntity<>(goodsTypeService.create(goodsTypeDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGoods(@RequestBody GoodsTypeDto goodsTypeDto, @PathVariable Integer id) {
        goodsTypeDtoValidationService.validatePatch(goodsTypeDto);
        return new ResponseEntity<>(goodsTypeService.patch(goodsTypeDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable Integer id) {
        goodsTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
