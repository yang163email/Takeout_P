package com.yan.takeout.util;

import android.app.Application;

import com.yan.takeout.model.dao.CacheSelectedInfo;
import com.yan.takeout.model.net.User;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class TakeoutApp extends Application {

    public static User sUser;
    public static TakeoutApp sInstance;
    public List<CacheSelectedInfo> infos = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        sUser = new User();
        sInstance = this;
        //初始状态为-1
        sUser.setId(-1);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    public int queryCacheSelectedInfoByGoodsId(int goodsId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getGoodsId() == goodsId){
                count = info.getCount();
                break;
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoByTypeId(int typeId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getTypeId() == typeId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoBySellerId(int sellerId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public void addCacheSelectedInfo(CacheSelectedInfo info) {
        infos.add(info);
    }

    public void clearCacheSelectedInfo(int sellerId){
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                infos.remove(info);
            }
        }
    }

    public void deleteCacheSelectedInfo(int goodsId) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                infos.remove(info);
                break;
            }
        }
    }

    public void updateCacheSelectedInfo(int goodsId, int operation) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                switch (operation) {
                    case Constant.ADD:
                        info.setCount(info.getCount() + 1);
                        break;
                    case Constant.MINUS:
                        info.setCount(info.getCount() - 1);
                        break;
                }
            }
        }
    }
}
