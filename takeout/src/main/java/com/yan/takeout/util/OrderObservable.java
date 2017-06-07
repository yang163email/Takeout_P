package com.yan.takeout.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by lidongzhi on 2016/10/20.
 */
public class OrderObservable extends Observable {
    private static OrderObservable sOrderObserver = new OrderObservable();
    private OrderObservable() {
    }

    public static OrderObservable getInstance(){
        return sOrderObserver;
    }

    /* 订单状态
       * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单*/
    public static final String ORDERTYPE_UNPAYMENT = "10";
    public static final String ORDERTYPE_SUBMIT = "20";
    public static final String ORDERTYPE_RECEIVEORDER = "30";
    public static final String ORDERTYPE_DISTRIBUTION = "40";
    // 骑手状态：接单、取餐、送餐
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE = "43";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL = "46";
    public static final String ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL = "48";

    public static final String ORDERTYPE_SERVED = "50";
    public static final String ORDERTYPE_CANCELLEDORDER = "60";

    public void newMsgComing(String extras) {
        //解析获取到的json数据并保存
        Map<String, String> data = processJson(extras);

        //通知观察者内容发生变化
        setChanged();
        //发生变化的内容
        notifyObservers(data);
    }

    private Map<String, String> processJson(String extras) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(extras);
            String orderId = jsonObject.getString("orderId");
            String type = jsonObject.getString("type");
            map.put("orderId", orderId);
            map.put("type", type);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
