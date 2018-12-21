package com.secondwork.redrock.weatherapp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<WeatherData> weatherDataList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView datetext;
        TextView typetext;
        TextView fengxiang;
        TextView fengli;
        TextView high;
        TextView low;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            datetext = (TextView) itemView.findViewById(R.id.date);
            typetext = (TextView) itemView.findViewById(R.id.type);
            fengxiang=(TextView) itemView.findViewById(R.id.fengxiang);
            fengli = (TextView) itemView.findViewById(R.id.fengli);
            high = (TextView) itemView.findViewById(R.id.high);
            low = (TextView) itemView.findViewById(R.id.low);
        }
    }
    public WeatherAdapter(List<WeatherData> weatherDataList){
        this.weatherDataList=weatherDataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WeatherData weatherData=weatherDataList.get(i);
        String fl = weatherData.getFengli().substring(9,weatherData.getFengli().length()-3);
        viewHolder.datetext.setText("日期:"+weatherData.getDate());
        viewHolder.typetext.setText("天气:"+weatherData.getType());
        viewHolder.fengli.setText("风力:"+fl);
        viewHolder.fengxiang.setText("风向:"+weatherData.getFengxiang());
        viewHolder.high.setText("最"+weatherData.getHigh());
        viewHolder.low.setText("最"+weatherData.getLow());
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }


}
