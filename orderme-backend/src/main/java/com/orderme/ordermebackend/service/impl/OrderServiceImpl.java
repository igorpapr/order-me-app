package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.OrderDto;
import com.orderme.ordermebackend.model.dtomappers.OrderMapper;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.model.entity.Order;
import com.orderme.ordermebackend.model.entity.OrderLine;
import com.orderme.ordermebackend.model.entity.OrderLinesKey;
import com.orderme.ordermebackend.model.entity.OrderStatus;
import com.orderme.ordermebackend.model.entity.Shop;
import com.orderme.ordermebackend.model.entity.User;
import com.orderme.ordermebackend.repository.GoodsRepository;
import com.orderme.ordermebackend.repository.OrderLineRepository;
import com.orderme.ordermebackend.repository.OrderRepository;
import com.orderme.ordermebackend.repository.ShopRepository;
import com.orderme.ordermebackend.repository.UserRepository;
import com.orderme.ordermebackend.service.OrderService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    private final OrderMapper orderMapper;

    private final OrderLineRepository orderLineRepository;

    private final GoodsRepository goodsRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ShopRepository shopRepository,
                            OrderMapper orderMapper,
                            OrderLineRepository orderLineRepository, GoodsRepository goodsRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.orderMapper = orderMapper;
        this.orderLineRepository = orderLineRepository;
        this.goodsRepository = goodsRepository;
    }


    @Override
    @Transactional
    public Order create(OrderDto dto) {
        //TODO maybe get it from security context - than remove validation for it
        User userRef = userRepository.getOne(dto.getCreatedById());
        Shop shopRef = shopRepository.getOne(dto.getShopId());
        Order orderToInsert = Order.builder()
                .shop(shopRef)
                .createdBy(userRef)
                .creationTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING_FOR_PROCESSING)
                .build();
        Order resultingOrder = orderRepository.save(orderToInsert);
        dto.setOrderId(resultingOrder.getOrderID());

        resultingOrder.setOrderLines(saveOrderLinesFromOrderDto(dto, resultingOrder));
        return resultingOrder;
    }

    private Set<OrderLine> saveOrderLinesFromOrderDto(OrderDto orderDto, Order preSavedOrder) {
        //preparing orderlines entity set from dtos set
        Set<OrderLine> orderLinesToInsert =
                orderDto.getOrderLines()
                        .stream()
                        .map(orderLineDto -> OrderLine.builder()
                                .orderLineId(new OrderLinesKey(orderDto.getOrderId(),
                                        orderLineDto.getGoodsId()))
                                .amount(orderLineDto.getAmount())
                                .build())
                        .collect(Collectors.toSet());
        Order finalPreSavedOrder = Objects.requireNonNullElseGet(
                preSavedOrder, () -> orderRepository.getOne(orderDto.getOrderId()));
        //setting dependent fields
        orderLinesToInsert.forEach(
                orderLine -> {
                    UUID goodsId = orderLine.getOrderLineId().getGoodsId();
                    Goods goods = goodsRepository.findById(goodsId)
                            .orElseThrow(() -> new EntityNotFoundException("Goods not found with id: "
                                    + goodsId));
                    orderLine.setSalesOrder(finalPreSavedOrder);
                    orderLine.setGoods(goods);
                }
        );
        return new HashSet<>(orderLineRepository.saveAll(orderLinesToInsert));
    }

    private Set<OrderLine> saveOrderLinesFromOrderDto(OrderDto orderDto) {
        return saveOrderLinesFromOrderDto(orderDto, null);
    }

    @Override
    public Order getById(UUID id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order was not found with id: " + id)
        );
    }

    @Override
    @Transactional
    public Order patch(OrderDto dto, UUID orderId) {
        User processingByRef = null;
        if (dto.getProcessingById() != null) {
            processingByRef = userRepository.getOne(dto.getProcessingById());
        }
        Optional<Order> target = orderRepository.findById(orderId);
        Order orderToPatch = target.orElseThrow(
                () -> new EntityNotFoundException("Couldn't find the order to update with id: " + orderId));
        orderMapper.updateOrderFromDto(dto, orderToPatch);
        orderToPatch.setProcessingBy(processingByRef);
        orderToPatch.setLastUpdateTime(LocalDateTime.now());
        Order resultingOrder = orderRepository.save(orderToPatch);

        if (dto.getOrderLines() != null) { //saving order lines
            dto.setOrderId(orderId);
            resultingOrder.setOrderLines(saveOrderLinesFromOrderDto(dto, resultingOrder));
        }
        return resultingOrder;
    }

    @Override
    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> getAllByPageable(Pageable pageable) {
        //todo ???
        throw new NotImplementedException("This method is not yet implemented");
    }


    @Override
    @Transactional
    public Page<Order> getAllByParams(OrderDto params, Pageable pageable) {
        Example<Order> example = fillOrderExampleWithFetchedParameters(params);
        return orderRepository.findAll(example, pageable);
    }

    private Example<Order> fillOrderExampleWithFetchedParameters(OrderDto parameters) {
        Order orderToExample = new Order();
        if (parameters.getShopId() != null) {
            Shop shop = shopRepository.findById(parameters.getShopId())
                    .orElseThrow(() -> new EntityNotFoundException("Shop wasn't found with id: "
                            + parameters.getShopId()));
            orderToExample.setShop(shop);
        }
        if (parameters.getCreatedById() != null) {
            User user = userRepository.findById(parameters.getCreatedById())
                    .orElseThrow(() -> new EntityNotFoundException("User wasn't found with id: "
                            + parameters.getCreatedById()));
            orderToExample.setCreatedBy(user);
        }
        if (parameters.getProcessingById() != null) {
            User user = userRepository.findById(parameters.getProcessingById()).orElseThrow(
                    () -> new EntityNotFoundException("Administrator wasn't found with id: " + parameters.getProcessingById()));
            orderToExample.setProcessingBy(user);
        }
        if (parameters.getOrderStatus() != null) {
            orderToExample.setOrderStatus(parameters.getOrderStatus());
        }
        return Example.of(orderToExample);
    }
}
