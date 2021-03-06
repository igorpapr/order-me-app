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
import com.orderme.ordermebackend.utils.fieldnames.EntityFieldNames;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
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
        dto.setOrderId(resultingOrder.getOrderId());

        resultingOrder.setOrderLines(saveOrderLinesFromOrderDto(dto, resultingOrder));
        calculateAndSetFullPrice(resultingOrder);
        return resultingOrder;
    }

    @Override
    public Order getById(UUID id) {
        Order res = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order was not found with id: " + id)
        );
        calculateAndSetFullPrice(res);
        return res;
    }

    @Override
    @Transactional
    public Order patch(OrderDto dto, UUID orderId) {
        Optional<Order> target = orderRepository.findById(orderId);
        Order orderToPatch = target.orElseThrow(
                () -> new EntityNotFoundException("Couldn't find the order to update with id: " + orderId));
        orderMapper.updateOrderFromDto(dto, orderToPatch);
        if (dto.getOrderLines() != null) { //saving order lines
            dto.setOrderId(orderId);
            orderToPatch.setOrderLines(saveOrderLinesFromOrderDto(dto, orderToPatch));
        }
        if (dto.getProcessingById() != null) {
            User processingByRef = userRepository.getOne(dto.getProcessingById());
            orderToPatch.setProcessingBy(processingByRef);
        }

        orderToPatch.setLastUpdateTime(LocalDateTime.now());
        Order resultingOrder = orderRepository.save(orderToPatch);

        calculateAndSetFullPrice(resultingOrder);
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
    public Page<Order> getAllByParams(OrderDto params, boolean unprocessedOnly, Pageable pageable) {
        Example<Order> example = fillOrderExampleWithFetchedParameters(params, unprocessedOnly);
        Page<Order> resPage = orderRepository.findAll(example, pageable);
        resPage.getContent().forEach(OrderServiceImpl::calculateAndSetFullPrice);
        return resPage;
    }

    private Set<OrderLine> saveOrderLinesFromOrderDto(OrderDto orderDto) {
        return saveOrderLinesFromOrderDto(orderDto, null);
    }

    private Example<Order> fillOrderExampleWithFetchedParameters(OrderDto parameters, boolean unprocessedOnly) {
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
            System.out.println(parameters.getProcessingById().toString());
        }
        if (parameters.getOrderStatus() != null) {
            orderToExample.setOrderStatus(parameters.getOrderStatus());
        }
        if (unprocessedOnly) {
            // Gives a possibility to provide example for Spring Data Jpa with null processingBy value
            ExampleMatcher includeNullProcessingBy = ExampleMatcher.matchingAll()
                    .withIgnorePaths(getIgnoredNullFields(orderToExample))
                    .withIncludeNullValues();
            return Example.of(orderToExample, includeNullProcessingBy);
        }
        return Example.of(orderToExample);
    }

    private String[] getIgnoredNullFields(Order orderToExample) {
        Set<String> ignoredFieldsSet = new HashSet<>();
        if (orderToExample.getShop() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.SHOP);
        }
        if (orderToExample.getCreatedBy() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.CREATED_BY);
        }
        if (orderToExample.getOrderStatus() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.ORDER_STATUS);
        }
        if (orderToExample.getOrderId() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.ORDER_ID);
        }
        if (orderToExample.getCreationTime() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.CREATION_TIME);
        }
        if (orderToExample.getLastUpdateTime() == null) {
            ignoredFieldsSet.add(EntityFieldNames.ORDER.LAST_UPDATE_TIME);
        }
        String[] res = new String[ignoredFieldsSet.size()];
        return ignoredFieldsSet.toArray(res);
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

    private static void calculateAndSetFullPrice(Order order) {
        if (order.getOrderLines() != null) {
            BigDecimal sum = order.getOrderLines()
                    .stream()
                    .map(x -> x.getGoods().getActualPrice().multiply(BigDecimal.valueOf(x.getAmount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setFullPrice(sum);
        } else {
            order.setFullPrice(BigDecimal.ZERO);
        }
    }
}
