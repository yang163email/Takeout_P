package com.yan.takeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.takeout.model.net.ResponseInfo;
import com.yan.takeout.model.net.Seller;
import com.yan.takeout.view.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class HomeFragmentPresenter extends NetPresenter {
    private HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo() {
        Call<ResponseInfo> call = mTakeoutService.getHomeInfo();
        call.enqueue(mCallback);
    }

    @Override
    protected void onConnectError(String message) {
        mHomeFragment.onHomeError(message);
    }

    @Override
    protected void onServerBug(int code) {
        mHomeFragment.onHomeFailed(code);
    }

    @Override
    protected void onSuccess(String data) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String nearby = jsonObject.getString("nearbySellerList");
            List<Seller> nearbySellerList = gson.fromJson(nearby, new TypeToken<List<Seller>>(){}.getType());

            String other = jsonObject.getString("otherSellerList");
            List<Seller> otherSellerList = gson.fromJson(other, new TypeToken<List<Seller>>(){}.getType());

            mHomeFragment.onHomeSuccess(nearbySellerList, otherSellerList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
