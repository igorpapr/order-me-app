package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.OrderLine;
import com.orderme.ordermebackend.service.OrderLineService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathRoutes.PATH_ORDER_LINES)
public class OrderLineController {

    private final OrderLineService orderLineService;

    private final DtoValidationService<OrderLineDto> orderLineDtoValidationService;


    public OrderLineController(OrderLineService orderLineService, DtoValidationService<OrderLineDto> orderLineDtoValidationService) {
        this.orderLineService = orderLineService;
        this.orderLineDtoValidationService = orderLineDtoValidationService;
    }

    @PatchMapping
    public ResponseEntity<OrderLine> patchByIds(@RequestBody OrderLineDto orderLineDto,
                                        @RequestParam UUID orderId,
                                        @RequestParam UUID goodsId) {

        orderLineDto.setGoodsId(goodsId);
        orderLineDto.setOrderId(orderId);
        orderLineDtoValidationService.validatePatch(orderLineDto);
        return new ResponseEntity<>(orderLineService.patchByOrderIdAndGoodsId(orderLineDto),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByIds(@RequestParam UUID orderId,
                                         @RequestParam UUID goodsId) {
        orderLineService.deleteByOrderIdAndGoodsId(orderId, goodsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
