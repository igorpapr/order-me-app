package com.orderme.ordermebackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orderme.ordermebackend.controller.utils.PathRoutes.PATH_GOODS;

@RestController
@RequestMapping(PATH_GOODS)
public class GoodsController {

    @GetMapping
    public ResponseEntity<Void> getAllGoods() {
        //TODO tbd
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
