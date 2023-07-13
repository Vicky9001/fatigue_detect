package com.example.myapplication.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.myapplication.OfflineMap.OfflineMapActivity;
import com.example.myapplication.R;
import com.example.myapplication.activity.SearchActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MapFragment extends Fragment {

    private static final String TAG = "GaodeMapActivity";

    private MapView mapView;
    private AMap aMap = null;
    private double lat;
    private double lon;
    private View viewGroup;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private TextView back,history,btn_search,offlineMap;
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = inflater.inflate(R.layout.activity_map, container, false);
        back=viewGroup.findViewById(R.id.back);
        history=viewGroup.findViewById(R.id.record);
        try {
            initview(savedInstanceState,viewGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e("AAAAAAAAAA","3456789");
        return viewGroup;
    }
    private void initview( Bundle savedInstanceState,View view) throws Exception {
        mapView =  view.findViewById(R.id.gaode_map);
        MapsInitializer.updatePrivacyShow(getActivity(),true,true);
        MapsInitializer.updatePrivacyAgree(getActivity(),true);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            //展示地图
            aMap = mapView.getMap();
            Log.i(TAG,"展示地图");
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE); //持续定位
        //设置连续定位模式下定位间隔
        myLocationStyle.interval(2000);
        myLocationStyle.strokeWidth(20f);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.showMyLocation(true);

        btn_search = view.findViewById(R.id.btn_search);
        //这地方就是下面要讲的搜索导航功能
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        offlineMap=view.findViewById(R.id.offlineMap);
        offlineMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),  OfflineMapActivity.class);
                startActivity(intent);
            }
        });
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    Log.v("pcw","lat : "+lat+" lon : "+lon);

                    // 设置当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("当前位置");
                    markerOptions.visible(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
                    markerOptions.icon(bitmapDescriptor);
                    aMap.addMarker(markerOptions);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Toast.makeText(getActivity(),
                            amapLocation.getErrorCode() + ", errInfo:"
                                    + amapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
//获取状态栏高度
protected int getHeight(){
    /**
     * 获取状态栏高度——方法1
     * */
    int statusBarHeight1 = -1;
    //获取status_bar_height资源的ID
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        //根据资源ID获取响应的尺寸值
        statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
    }
    return statusBarHeight1;
}


}