package com.orderme.ordermebackend.utils.fieldnames;

import com.orderme.ordermebackend.model.dto.OrderLineDto;
import com.orderme.ordermebackend.model.entity.OrderStatus;

import java.util.Set;
import java.util.UUID;

public final class EntityFieldNames {

    public static final class ORDER {
        public static final String ORDER_ID = "orderId";
        public static final String CREATION_TIME = "creationTime";
        public static final String LAST_UPDATE_TIME = "lastUpdateTime";
        public static final String CREATED_BY = "createdBy";
        public static final String PROCESSING_BY = "processingBy";
        public static final String ORDER_STATUS = "orderStatus";
        public static final String ORDER_LINES = "orderLines";
        public static final String SHOP = "shop";
        public static final String FULL_PRICE = "fullPrice";
    }
}
