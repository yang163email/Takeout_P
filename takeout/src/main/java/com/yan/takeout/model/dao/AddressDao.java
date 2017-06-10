package com.yan.takeout.model.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 楠GG on 2017/6/10.
 */

public class AddressDao {

    private Dao<ReceiptAddress, Integer> mAddressDao;

    public AddressDao(Context context) {
        TakeoutOpenHelper openHelper = new TakeoutOpenHelper(context);
        try {
            mAddressDao = openHelper.getDao(ReceiptAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertAddress(ReceiptAddress receiptAddress) {
        try {
            mAddressDao.create(receiptAddress);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAddress(ReceiptAddress receiptAddress) {
        try {
            mAddressDao.delete(receiptAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateAddress(ReceiptAddress receiptAddress) {
        try {
            mAddressDao.update(receiptAddress);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ReceiptAddress> queryAllAddress() {
        try {
            return mAddressDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ReceiptAddress> queryAddressByUserId(String userId) {
        try {
            return mAddressDao.queryForEq("userId", userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
