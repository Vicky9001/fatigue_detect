package com.example.myapplication.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.myapplication.R;
import com.example.myapplication.dao.HistoryDao;
import com.example.myapplication.entity.HistoryData;
import com.example.myapplication.utils.ImageUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private View viewGroup;
    private LineChart chart;
    private TextView tvName,tvTime,tvBoolean;
    private ImageView img1,img2,img3;

    private List<HistoryData> times = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        return viewGroup;
    }

    private void initView() {
        chart = viewGroup.findViewById(R.id.chart1);
        tvName = viewGroup.findViewById(R.id.name);
        tvTime = viewGroup.findViewById(R.id.tvTime);
        img1 = viewGroup.findViewById(R.id.img1);
        img2 = viewGroup.findViewById(R.id.img2);
        img3 = viewGroup.findViewById(R.id.img3);
    }

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

    private void initData() {


        Log.e("dd","ImageUtils.imgList[1]");
        List<HistoryData> historyData1 = HistoryDao.getInstance(getActivity()).queryAll();
        if (historyData1.isEmpty()){
            times.add(new HistoryData((int) ((Math.random() ) + 20),"6月21日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (2)) + 20),"6月22日", ImageUtils.imgList[1], ImageUtils.imgList[2]));
            times.add(new HistoryData((int) ((Math.random() * (3)) + 20),"6月23日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (4)) + 20),"6月24日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (5)) + 20),"6月25日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (6)) + 20),"6月26日", ImageUtils.imgList[1], ImageUtils.imgList[2]));
            times.add(new HistoryData((int) ((Math.random() * (7)) + 20),"6月27日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (8)) + 20),"6月28日", ImageUtils.imgList[1], ImageUtils.imgList[2]));
            times.add(new HistoryData((int) ((Math.random() * (9)) + 20),"6月29日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (10)) + 20),"6月30日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (11)) + 20),"7月1日", ImageUtils.imgList[1], ImageUtils.imgList[2]));
            times.add(new HistoryData((int) ((Math.random() * (12)) + 20),"7月2日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (13)) + 20),"7月3日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (14)) + 20),"7月4日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (15)) + 20),"7月5日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (16)) + 20),"7月6日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (17)) + 20),"7月7日", ImageUtils.imgList[1], ImageUtils.imgList[2]));
            times.add(new HistoryData((int) ((Math.random() * (18)) + 20),"7月8日", ImageUtils.imgList[1]));
            times.add(new HistoryData((int) ((Math.random() * (19)) + 20),"7月9日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            times.add(new HistoryData((int) ((Math.random() * (20)) + 20),"7月10日", ImageUtils.imgList[1], ImageUtils.imgList[2],ImageUtils.imgList[3]));
            for (HistoryData time : times) {
                HistoryDao.getInstance(getActivity()).add(time);
            }
        }else{
            times.addAll(historyData1);
        }
        HistoryData historyData = times.get(times.size() - 1);
        tvName.setText(historyData.getNum()+"次");
        tvTime.setText(historyData.getTvTime());
        if (TextUtils.isEmpty(historyData.getImgPath1())){
            img1.setVisibility(View.GONE);
        }else{
            img1.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(historyData.getImgPath1())
                    .error(R.drawable.nan)
                    .priority(Priority.LOW)
                    .into(img1);
        }

        if (TextUtils.isEmpty(historyData.getImgPath2())){
            img2.setVisibility(View.GONE);
        }else{
            img2.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(historyData.getImgPath2())
                    .error(R.drawable.nvshi)
                    .priority(Priority.LOW)
                    .into(img2);
        }

        if (TextUtils.isEmpty(historyData.getImgPath3())){
            img3.setVisibility(View.GONE);
        }else{
            img3.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(historyData.getImgPath3())
                    .error(R.drawable.nan)
                    .priority(Priority.LOW)
                    .into(img3);
        }


        chart.setBackgroundColor(Color.WHITE);
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (tvName!=null &&tvTime!=null){
                    tvName.setText(String.valueOf((int) e.getX()));
                    tvTime.setText(String.valueOf((int) e.getY()));
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // th

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return times.get((int) value%times.size()).getTvTime();
            }
        });

        chart.animateXY(2000, 2000);
        setData();
        Legend l = chart.getLegend();
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
        // don't forget to refresh the drawing
        chart.invalidate();
    }

    private void setData() {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            values.add(new Entry(i, times.get(i).getNum()));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(ContextCompat.getColor(getActivity(),R.color.color_F3DA06));
            set1.setHighLightColor(Color.RED);
            set1.setColor(ContextCompat.getColor(getActivity(),R.color.color_F3DA06));
            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(ContextCompat.getColor(getActivity(),R.color.color_06AEFA));
                set1.setFillAlpha(60);
            }
            set1.setDrawHorizontalHighlightIndicator(false);

            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
            data.setValueTextSize(10f);
            data.setDrawValues(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            // set data
            chart.setData(data);
        }
    }
}