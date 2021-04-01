package com.orderme.ordermebackend.controller.utils;

public class PathRoutes {

    //Full routes
    public static final String BASE_PATH = "/api/v1";
    public static final String PATH_AUTH = BASE_PATH + "/auth";
    public static final String PATH_ORDERS = BASE_PATH + "/orders";
    public static final String PATH_ORDER_LINES = BASE_PATH + "/ordersLines";
    public static final String PATH_STATUSES = BASE_PATH + "/statuses";
    public static final String PATH_GOODS = BASE_PATH + "/goods";
    public static final String PATH_USERS = BASE_PATH + "/users";
    public static final String PATH_SHOPS = BASE_PATH + "/shops";
    public static final String PATH_GOODS_TYPES = BASE_PATH + "/goodsTypes";
    public static final String PATH_GOODS_AVAILABILITIES = BASE_PATH + "/goodsAvailabilities";

    //Child routes
    public static final String CHILD_PATH_REGISTER = "/register";
    public static final String CHILD_PATH_ADMIN_REGISTER = "/register/admin";
    public static final String CHILD_PATH_AUTH = "/authenticate";
    public static final String CHILD_PATH_GOODS_TYPE = "/goodsType";

}
