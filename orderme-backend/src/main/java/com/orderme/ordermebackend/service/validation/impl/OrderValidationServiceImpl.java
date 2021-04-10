package com.orderme.ordermebackend.service.validation.impl;

import com.orderme.ordermebackend.model.dto.OrderDto;
import com.orderme.ordermebackend.model.entity.*;
import com.orderme.ordermebackend.repository.UserRepository;
import com.orderme.ordermebackend.service.GoodsAvailabilityService;
import com.orderme.ordermebackend.service.OrderService;
import com.orderme.ordermebackend.service.validation.AbstractDtoValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderValidationServiceImpl extends AbstractDtoValidationService<OrderDto> {

    private final OrderService orderService;

    private final UserRepository userRepository;

    private final GoodsAvailabilityService goodsAvailabilityService;

    public OrderValidationServiceImpl(OrderService orderService,
                                      UserRepository userRepository,
                                      GoodsAvailabilityService goodsAvailabilityService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.goodsAvailabilityService = goodsAvailabilityService;
    }

    @Override
    protected void failWithMissingParameter(String field) {
        failWithMissingParameter(field, OrderDto.class);
    }

    @Override
    public void validateCreate(OrderDto dto) {
        if (dto != null) {
            validateEmptyRequiredFields(dto);
            //Order lines should not be empty
            if (dto.getOrderLines().isEmpty()) {
                throw new IllegalArgumentException("Order cannot be created without order items");
            } else {
                checkOrderLinesAvailability(dto);
            }
        } else {
            failWithEmptyDto(OrderDto.class);
        }
    }

    @Override
    public void validatePatch(OrderDto dto) {
        if (dto != null) {
            //creator cannot be changed
            if (dto.getCreatedById() != null) {
                throw new IllegalArgumentException("Creator of an order cannot be changed");
            }
            if (dto.getShopId() != null) {
                throw new IllegalArgumentException("The shop of an order cannot be changed");
            }
            //processing by must exist and it must be an admin or super_admin
            if (dto.getProcessingById() != null) {
                checkProcessingByDuringPatch(dto.getProcessingById());
            }
            //Not all statuses can be assigned from the current status
            if (dto.getOrderStatus() != null) {
                checkOrderStatusPatch(dto);
            }
            if (dto.getOrderLines() != null) {
                checkOrderLinesAvailability(dto);
            }
        } else {
            failWithEmptyDto(OrderDto.class);
        }
    }

    //Order lines should contain only goods available in the given shop
    private void checkOrderLinesAvailability(OrderDto dto) {
        Integer shopId;
        if (dto.getShopId() == null) { //then it's used for patch, fetch order from the database
             shopId = orderService.getById(dto.getOrderId()).getShop().getShopId();
        } else {
            shopId = dto.getShopId();
        }
        dto.getOrderLines().forEach(
                orderLine -> {
                    UUID goodsId = orderLine.getGoodsId();

                    GoodsAvailability goodsAvailability = goodsAvailabilityService.getByShopIdAndGoodsId(shopId,
                            goodsId);
                    if (goodsAvailability.getAvailabilityStatus().equals(AvailabilityStatus.NOT_AVAILABLE)) {
                        throw new IllegalArgumentException(
                                "The goods with id: " + goodsId +
                                        " is not available in the shop with id: " + shopId);
                    }
                    if (orderLine.getAmount() == 0) {
                        throw new IllegalArgumentException(
                                "The goods cannot be added to the order with the requested amount of zero items. Goods id: " + goodsId
                        );
                    }
                });
    }

    private void validateEmptyRequiredFields(OrderDto dto) {
        if (dto.getCreatedById() == null) {
            failWithMissingParameter("createdById");
        }
        if (dto.getShopId() == null) {
            failWithMissingParameter("shopId");
        }
        if (dto.getOrderLines() == null) {
            failWithMissingParameter("orderLines");
        }
    }

    private void checkOrderStatusPatch(OrderDto dto) {
        OrderStatus targetOrderStatus = dto.getOrderStatus();
        assert dto.getOrderId() != null;
        OrderStatus currentOrderStatus = orderService.getById(dto.getOrderId()).getOrderStatus();
        switch (targetOrderStatus) {
            case READY:
            case COMPLETED:
                if (currentOrderStatus.equals(OrderStatus.CANCELED)) {
                    failWithIncorrectStatusChange(targetOrderStatus, currentOrderStatus);
                }
                break;
            case WAITING_FOR_PROCESSING:
                    failWithIncorrectStatusChange(targetOrderStatus, currentOrderStatus);
                break;
            case COLLECTING:
                if (currentOrderStatus.equals(OrderStatus.CANCELED) ||
                        currentOrderStatus.equals(OrderStatus.READY)) {
                    failWithIncorrectStatusChange(targetOrderStatus, currentOrderStatus);
                }
                break;
            case PROCESSING:
                if (currentOrderStatus.equals(OrderStatus.READY) ||
                        currentOrderStatus.equals(OrderStatus.CANCELED)) {
                    failWithIncorrectStatusChange(targetOrderStatus, currentOrderStatus);
                }
                break;
            case CANCELED:
                break;
        }
    }

    private void checkProcessingByDuringPatch(UUID processingBy) {
        User adminOrSuperAdmin = userRepository.findById(processingBy)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find an administrator with id:"
                        + processingBy));
        Role role = adminOrSuperAdmin.getRole();
        if (!(role.equals(Role.ADMIN) || role.equals(Role.SUPER_ADMIN))) {
            throw new IllegalArgumentException("An order must be processed by an administrator. Given processingBy id: "
                    + processingBy);
        }
    }

    private void failWithIncorrectStatusChange(OrderStatus targetStatus, OrderStatus currentStatus) {
        throw new IllegalArgumentException("The order status change from " + currentStatus
                + " to " + targetStatus + " is prohibited");
    }
}
