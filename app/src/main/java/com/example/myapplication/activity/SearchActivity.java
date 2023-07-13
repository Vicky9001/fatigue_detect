package com.example.myapplication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
//import com.amap.api.navi.services.search.model.Tip;
import com.example.myapplication.R;
import com.example.myapplication.utils.RvAdapter;

import java.util.ArrayList;
import java.util.List;

public  class SearchActivity extends AppCompatActivity implements Inputtips.InputtipsListener, TextWatcher, RvAdapter.OnItemClickListener {
    private RvAdapter rvAdapter;
    private Inputtips inputtips;
    private AMapNavi aMapNavi;
    private EditText editText;
    private RecyclerView recyclerView;
    private ArrayList<com.amap.api.services.help.Tip> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText = findViewById(R.id.search_edit);
        editText.addTextChangedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.search_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rvAdapter = new RvAdapter(list,SearchActivity.this,recyclerView);
        rvAdapter.setmOnItemClickListener(this);
        recyclerView.setAdapter(rvAdapter);
        inputtips = new Inputtips(this,(InputtipsQuery) null);
        inputtips.setInputtipsListener(this);
        //这是隐私合规接口，如果不加，可能出现地图加载不出来的问题
//        NaviSetting.updatePrivacyShow(this, true, true);
//        NaviSetting.updatePrivacyAgree(this, true);
        try {
            //这个地方也许捕获一下异常，不然编译不过去
            aMapNavi = AMapNavi.getInstance(this);
        }catch (Exception e){
            Log.e("TAG", e.getMessage());
        }
        if (aMapNavi!=null) {
            //设置内置语音播报
            aMapNavi.setUseInnerVoice(true, false);
        }
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        InputtipsQuery inputtipsQuery = new InputtipsQuery(String.valueOf(charSequence),null);
        inputtipsQuery.setCityLimit(true);
        inputtips.setQuery(inputtipsQuery);
        inputtips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
//    @Override
//    public void onItemClick(RecyclerView parent, View view, int postion, Tip data) {
//
//    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int postion, com.amap.api.services.help.Tip data) {
        Log.i("TAG", "onItemClick:  点击了"+postion+"条");
        //得到点击的坐标
        LatLonPoint point = data.getPoint();
        Log.i("Tag", "坐标为"+point);
        //得到经纬度
        Poi poi = new Poi(data.getName(), new LatLng(point.getLatitude(), point.getLongitude()), data.getPoiID());
        //导航参数对象（起点，途径，终点，导航方式）DRIVER是导航方式（驾驶，步行...当前为驾驶）ROUTE会计算路程选择
        AmapNaviParams params = new AmapNaviParams(null, null, poi, AmapNaviType.DRIVER, AmapPageType.ROUTE);
        //传递上下文和导航参数
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
    }



    @Override
    public void onGetInputtips(List<com.amap.api.services.help.Tip> list, int i) {
        Log.i("Tag","onGetInputtips data = "+list);
        rvAdapter.setData(list);
    }
}
