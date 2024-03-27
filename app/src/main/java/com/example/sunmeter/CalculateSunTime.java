

package com.example.sunmeter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.time.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class CalculateSunTime extends AsyncTask<Double, Void, Integer> {



	public static int weatherData(double lat, double lon) throws IOException, MalformedURLException {

		String locationUrl = "http://dataservice.accuweather.com/locations/v1/cities/"
				+ "geoposition/search?apikey=qAV3nAnWDhGyKRWoiDcbgsg2P2EeYxed&q=" + lat + "," + lon;

		URL url = new URL(locationUrl);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();


		con.setRequestMethod("GET");

		int status = con.getResponseCode();


		String locationKey;

		StringBuffer content = null;

//		System.out.println(status);
		if (status == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		}
		Log.i("test","deep weatherFata");
//		System.out.println(content);

		// get index of K to find the index of the key. Add 6 to it. Then get the substring from that character to that plus 6."

		int index = content.indexOf("K");

		index = index + 6;

		locationKey = content.substring(index, index+6);

//		System.out.println("Location Key: " + locationKey);

		// need to print cloud cover, time of day, sunset/sunrise"

		// FIND WEATHER DATA

		String weatherUrl = "http://dataservice.accuweather.com/currentconditions/v1/"
				+ locationKey + "?apikey=qAV3nAnWDhGyKRWoiDcbgsg2P2EeYxed&details=true";

		url = new URL(weatherUrl);

		con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");

		status = con.getResponseCode();

		content = null;

//		System.out.println(status);
		if (status == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		}

		System.out.println(content);

		DateTime dt = new DateTime(content.substring(30,55));


//		System.out.println("Local Date/Time: " + dt);

		String cloudCover = content.substring(content.indexOf("CloudCover")+12,content.indexOf("CloudCover")+14);

//		System.out.println("Cloud Cover: " + cloudCover);

		// next steps are to get sunset/sunrise time, and calculate minutes based on that need to spend outside.
		// get weather icon

		String weatherIconString = content.substring(content.indexOf("WeatherIcon")+13,content.indexOf("WeatherIcon")+15);

		// check for comma, get rid of it

		weatherIconString = weatherIconString.replaceAll(",","");


		int weatherIcon = Integer.parseInt(weatherIconString);

		String isDayText = content.substring(content.indexOf("IsDayTime")+12,content.indexOf("IsDayTime")+13);

		if (isDayText.equals("f")) {
			return 100;
		}


		Log.i("i",weatherIconString);

//		System.out.println(weatherIcon);

		switch (weatherIcon) {
			case 1: case 2: case 3:
				return 10;
			case 4:
				return 20;
			case 5: case 6: case 7:
				return 30;
			case 8: case 9: case 10: case 11: case 12: case 13:
				return 40;
			case 14:
				return 30;
			case 15:
				return 40;
			case 16: case 17:
				return 25;
			case 18: case 19: case 20: case 21: case 22: case 23:
				return 40;
		}

		return 100;
	}




	@Override
	protected Integer doInBackground(Double... doubles) {
		try {
			return weatherData(doubles[0],doubles[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}
}