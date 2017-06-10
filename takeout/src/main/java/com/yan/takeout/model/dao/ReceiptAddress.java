package com.yan.takeout.model.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 收货地址的Javabean模型
 */

@DatabaseTable(tableName = "t_address")
public class ReceiptAddress {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "sex")
    private String sex;
    @DatabaseField(columnName = "phone")
    private String phone;
    @DatabaseField(columnName = "phoneOther")
    private String phoneOther;
    @DatabaseField(columnName = "address")
    private String address;
    @DatabaseField(columnName = "detailAddress")
    private String detailAddress;
    @DatabaseField(columnName = "selectLabel")
    private String selectLabel;

    @DatabaseField(columnName = "userId")
    private String userId;  //用户id

    public ReceiptAddress() {
    }

    public ReceiptAddress(int id, String name, String sex, String phone, String phoneOther, String address, String detailAddress, String selectLabel, String userId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.phoneOther = phoneOther;
        this.address = address;
        this.detailAddress = detailAddress;
        this.selectLabel = selectLabel;
        this.userId = userId;
    }
}
