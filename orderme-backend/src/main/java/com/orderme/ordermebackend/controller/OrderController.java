package com.orderme.ordermebackend.controller;

import com.orderme.ordermebackend.controller.utils.PathRoutes;
import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.dto.OrderDto;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.model.entity.OrderStatus;
import com.orderme.ordermebackend.service.OrderService;
import com.orderme.ordermebackend.service.validation.DtoValidationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathRoutes.PATH_ORDERS)
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    private final DtoValidationService<OrderDto> orderDtoValidationService;

    public OrderController(OrderService orderService,
                           DtoValidationService<OrderDto> orderDtoValidationService) {
        this.orderService = orderService;
        this.orderDtoValidationService = orderDtoValidationService;
    }

    //pageable - page, size https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.web

    /**
     * Get the page of orders by parameters and pageable
     * @param shopId - shopId, not required
     * @param createdBy - createdBy Id, not required
     * @param processingBy - processingBy Id, not required
     * @param status - order status, not required
     * @param unprocessedOnly - This parameter is used to fetch only the unprocessed orders (where processingBy is null).
     *                        Important! When the processingBy parameter is provided - this parameter is ignored.
     *                        Default value = false
     * @param pageable - page, size and sort can be specified, required
     * @return page with orders
     */
    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrdersByPageable(@RequestParam(required = false) Integer shopId,
                                                              @RequestParam(required = false) UUID createdBy,
                                                              @RequestParam(required = false) UUID processingBy,
                                                              @RequestParam(required = false) OrderStatus status,
                                                              @RequestParam(required = false, defaultValue = "false") Boolean unprocessedOnly,
                                                              Pageable pageable) {
        OrderDto dto = OrderDto.builder()
                .shopId(shopId)
                .createdById(createdBy)
                .processingById(processingBy)
                .orderStatus(status)
                .build();
        return new ResponseEntity<>(orderService.getAllByParams(dto, unprocessedOnly, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> addNewOrder(@RequestBody OrderDto goodsDto) {
        orderDtoValidationService.validateCreate(goodsDto);
        return new ResponseEntity<>(orderService.create(goodsDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderDto orderDto,
                                             @PathVariable UUID id) {
        orderDto.setOrderId(id);
        orderDtoValidationService.validatePatch(orderDto);
        orderDto.setOrderId(null);

        return new ResponseEntity<>(orderService.patch(orderDto, id), HttpStatus.OK);
    }
}
