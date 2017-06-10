package com.yan.takeout.model.dao;

/**
 * 一家店铺对应多个Javabean
 */

public class CacheSelectedInfo {
    private int sellerId; //商家id

    private int typeId; //类型id

    private int goodsId;    //商品id

    private int count;  //商品数量

    public CacheSelectedInfo(int sellerId, int typeId, int goodsId, int count) {
        this.sellerId = sellerId;
        this.typeId = typeId;
        this.goodsId = goodsId;
        this.count = count;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
