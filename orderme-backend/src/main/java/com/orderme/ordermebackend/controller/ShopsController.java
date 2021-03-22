package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.service.ShopService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathRoutes.PATH_SHOPS)
public class ShopsController {

    private final ShopService shopService;

    private final DtoValidationService<ShopDto> shopDtoDtoValidationService;

    public ShopsController(ShopService shopService, DtoValidationService<ShopDto> shopDtoDtoValidationService) {
        this.shopService = shopService;
        this.shopDtoDtoValidationService = shopDtoDtoValidationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllShops() {
        return new ResponseEntity<>(shopService.getAllNotPaginated(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable Integer id) {
        return new ResponseEntity<>(shopService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewShop(@RequestBody ShopDto shopDto) {
        shopDtoDtoValidationService.validateCreate(shopDto);
        return new ResponseEntity<>(shopService.create(shopDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchShop(@RequestBody ShopDto shopDto, @PathVariable Integer id) {
        return new ResponseEntity<>(shopService.patch(shopDto, id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable Integer id) {
        shopService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
