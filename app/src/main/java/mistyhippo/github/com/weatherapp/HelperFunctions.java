package mistyhippo.github.com.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HelperFunctions {

    View rootview;
    Context context;
    String url;
    String country, description, main, name, icon;
    int id, sunrise, sunset, visibility;
    double speed, humidity, latitude, longitude, temperature, pressure, min_temp, max_temp;

    TextView txtCity, txtTemp, txtDescription, txtMinTemp, txtMaxTemp, txtWindSpeed, txtHumidity,txtPressure, txtVisibility, txtSunrise, txtSunset;
    ImageView imageCondition;

    //this is the constructor
    public HelperFunctions(Context context, String url){

        this.context = context;
        this.url = url;
        // you can get the rootview of any class from the main activity with this line
        rootview = ((Activity)context).getWindow().getDecorView().getRootView();
        imageCondition = (ImageView) rootview.findViewById(R.id.condIcon);
        txtCity = (TextView) rootview.findViewById(R.id.cityText);
        txtTemp = (TextView) rootview.findViewById(R.id.temp);
        txtMinTemp = (TextView) rootview.findViewById(R.id.tempMin);
        txtMaxTemp = (TextView) rootview.findViewById(R.id.tempMax);
        txtDescription = (TextView) rootview.findViewById(R.id.descrWeather);
        txtWindSpeed = (TextView) rootview.findViewById(R.id.windSpeed);
        txtHumidity = (TextView)rootview.findViewById(R.id.hum);
        txtPressure = (TextView)rootview.findViewById(R.id.pressure);
        txtVisibility = (TextView)rootview.findViewById(R.id.visibility);
        txtSunrise = (TextView)rootview.findViewById(R.id.sunrise);
        txtSunset = (TextView) rootview.findViewById(R.id.sunset);
    }

    public JSONObject makeRequest(){

        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String stream;
                while(stream = bufferedReader.readLine()!= null){
                    builder.append(stream);
                }
                Log.d("JSON","JSON: " + builder.toString());
                return new JSONObject(builder.toString());
            }
        } catch (MalformedURLException e) {
            Log.e("JSON", e.getMessage());

        } catch (IOException e) {
            Log.e("JSON", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSON",e.getMessage());
        }

        return null;
    }
}


