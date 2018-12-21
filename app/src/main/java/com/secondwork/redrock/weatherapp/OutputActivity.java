package com.secondwork.redrock.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class OutputActivity extends AppCompatActivity {
    String CITY;
    String Wendu;
    String Ganmao;
    TextView temp;
    TextView tips;
    WeatherAdapter adapter;
    List<WeatherData> weatherDataList= new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:tips.setText("温馨提示:"+(String)msg.obj);
                break;
                case 2:temp.setText((String)msg.obj+"℃");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent  intent = getIntent();
        CITY = intent.getStringExtra("city");
        adapter = new WeatherAdapter(weatherDataList);
        String get;
        temp = (TextView) findViewById(R.id.wendu);
        TextView city = (TextView) findViewById(R.id.city);
        tips=(TextView)findViewById(R.id.ganmao);
        initWeatherDatas();//处理数据
        city.setText(CITY);
        RecyclerView recyclerView=findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void initWeatherDatas(){
        HttpConnect httpConnect = new HttpConnect("https://www.apiopen.top/weatherApi?city="+CITY);
        httpConnect.sendRequestWithHttpURLConnection(new HttpConnect.Callback() {
            @Override
            public void finish(String response) {
                parseJson(response);
            }
        });
    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = new JSONObject(jsonObject.getString("data"));
            JSONObject yesterday= new JSONObject(data.getString("yesterday"));
            Message ganmao = new Message();
            Message wendu = new Message();
            ganmao.obj=data.getString("ganmao");
            ganmao.what=1;
            handler.sendMessage(ganmao);
            wendu.obj=data.getString("wendu");
            wendu.what=2;
            handler.sendMessage(wendu);
            String date = yesterday.getString("date");
            String high = yesterday.getString("high");
            String fengxiang = yesterday.getString("fx");
            String low = yesterday.getString("low");
            String fl = yesterday.getString("fl");
            String type = yesterday.getString("type");
            WeatherData weatherData = new WeatherData(date,high,low,fl,fengxiang,type);
            weatherDataList.add(weatherData);
            JSONArray jsonArray = new JSONArray(data.getString("forecast"));
            for(int i=0;i<jsonArray.length();i++){
                JSONObject weather = jsonArray.getJSONObject(i);
                date = weather.getString("date");
                high = weather.getString("high");
                fengxiang = weather.getString("fengxiang");
                low = weather.getString("low");
                fl = weather.getString("fengli");
                type = weather.getString("type");
                weatherData = new WeatherData(date,high,low,fl,fengxiang,type);
                weatherDataList.add(weatherData);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
