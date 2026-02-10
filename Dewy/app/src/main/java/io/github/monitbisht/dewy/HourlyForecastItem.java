package io.github.monitbisht.dewy;

public class HourlyForecastItem {
    public String time;
    public int iconRes;
    public String temp;

    public HourlyForecastItem(String time, int iconRes, String temp) {
        this.time = time;
        this.iconRes = iconRes;
        this.temp = temp;
    }
}
