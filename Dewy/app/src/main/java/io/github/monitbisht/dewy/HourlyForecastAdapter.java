package io.github.monitbisht.dewy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourlyViewHolder> {

    private final List<HourlyForecastItem> items;
    public HourlyForecastAdapter(List<HourlyForecastItem> items) {
        this.items = items;
    }



    @NonNull
    @Override
    public HourlyForecastAdapter.HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_forecast,parent,false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastAdapter.HourlyViewHolder holder, int position) {
        HourlyForecastItem item = items.get(position);
        holder.hourlyTime.setText(item.time);
        holder.hourlyIcon.setImageResource(item.iconRes);
        holder.hourlyTemp.setText(item.temp);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    static class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView hourlyTime,hourlyTemp;
        ImageView hourlyIcon;
        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourlyTime = itemView.findViewById(R.id.hour_text);
            hourlyIcon = itemView.findViewById(R.id.hourly_icon);
            hourlyTemp = itemView.findViewById(R.id.hourly_temp);
        }

    }


}