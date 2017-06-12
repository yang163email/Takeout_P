package com.yan.takeout.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.yan.takeout.R;
import com.yan.takeout.util.OrderObservable;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL;
import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE;
import static com.yan.takeout.util.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL;

/**
 * Created by 楠GG on 2017/6/12.
 */

public class OrderDetailActivity extends Activity implements Observer {
    @Bind(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @Bind(R.id.tv_seller_name)
    TextView mTvSellerName;
    @Bind(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;
    @Bind(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @Bind(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;
    @Bind(R.id.map)
    MapView mapView;
    private String mOrderId;
    private String mType;
    private AMap aMap;
    private Marker mMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        processIntent();
        //添加到观察者中
        OrderObservable.getInstance().addObserver(this);

        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if(aMap == null) {
            aMap = mapView.getMap();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void processIntent() {
        if (getIntent() != null) {
            mOrderId = getIntent().getStringExtra("orderId");
            mType = getIntent().getStringExtra("type");
            int index = getIndex(mType);

            if (index != -1) {
                ((TextView) mLlOrderDetailTypeContainer.getChildAt(index))
                        .setTextColor(Color.BLUE);
                ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(index))
                        .setImageResource(R.drawable.order_time_node_disabled);
            }
        }
    }

    private int getIndex(String type) {
        int index = -1;
//        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case OrderObservable.ORDERTYPE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
            case ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
//                typeInfo = "配送中";
                index = 2;
                break;
            case OrderObservable.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }
        return index;
    }

    @OnClick({R.id.iv_order_detail_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_order_detail_back:
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<String, String> data = (Map<String, String>) arg;
        String pushOrderId = data.get("orderId");
        String pushType = data.get("type");

        if (mOrderId.equals(pushOrderId)) {
            //如果是这一单
            mType = pushType;

            int index = getIndex(pushType);
            if (index != -1) {
                ((TextView) mLlOrderDetailTypeContainer.getChildAt(index))
                        .setTextColor(Color.BLUE);
                ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(index))
                        .setImageResource(R.drawable.order_time_node_disabled);
            }

            switch (mType) {
                //如果商家接了单，显示商家位置
                case OrderObservable.ORDERTYPE_RECEIVEORDER:
                    mapView.setVisibility(View.VISIBLE);

//                aMap.moveCamera(CameraUpdateFactory.newLatLng
//                        (new LatLng(22.5788340000, 113.9216700000)));

                    //标注卖家
                    MarkerOptions sellerMarkerOptions = new MarkerOptions();
                    sellerMarkerOptions.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.order_seller_icon));
                    sellerMarkerOptions.position(new LatLng(22.5788340000, 113.9216700000));
                    sellerMarkerOptions.title("丰顺自选快餐");
                    sellerMarkerOptions.snippet("我是丰顺自选快餐");

                    aMap.addMarker(sellerMarkerOptions);
                    aMap.moveCamera(CameraUpdateFactory.newLatLng
                            (new LatLng(22.5788340000, 113.9216700000)));

                    //标注买家
                    MarkerOptions buyerMarkerOptions = new MarkerOptions();

                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.drawable.order_buyer_icon);
                    buyerMarkerOptions.icon(BitmapDescriptorFactory.fromView(imageView));
                    buyerMarkerOptions.position(new LatLng(22.5765800000, 113.9237520000));
                    buyerMarkerOptions.title("黑马程序员");
                    buyerMarkerOptions.snippet("我是黑马程序员");

                    aMap.addMarker(buyerMarkerOptions);
                    aMap.moveCamera(CameraUpdateFactory.newLatLng
                            (new LatLng(22.5765800000, 113.9237520000)));

                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    break;

                case OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
                    //骑手接单
                    initRider();
                    break;
                case OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL:
                    //骑手送餐
                    updateRider();
                    break;
            }

        }
    }

    private void updateRider() {
        mMarker.hideInfoWindow();
        mMarker.setPosition(new LatLng(22.5774220000,113.922475000));
        aMap.moveCamera(CameraUpdateFactory.newLatLng
                (new LatLng(22.5774220000,113.922475000)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }

    private void initRider() {
        MarkerOptions riderMarkerOptions = new MarkerOptions();

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.order_rider_icon);
        riderMarkerOptions.icon(BitmapDescriptorFactory.fromView(imageView));
        riderMarkerOptions.position(new LatLng(22.5766790000, 113.9205490000));
        riderMarkerOptions.snippet("我是百度骑士");

        mMarker = aMap.addMarker(riderMarkerOptions);
        mMarker.showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.newLatLng
                (new LatLng(22.5766790000, 113.9205490000)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }
}
