package com.liuhao.smartcrutch.tools;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is used for get location info from server
 */

public class LocationTool {
    public double[] location = new double[2];
    public double[] getLocation() throws Exception {
        String path  = "http://123.206.56.219/test2.php?user=2";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        int code = conn.getResponseCode();
        if(code==200){
            InputStream is = conn.getInputStream();
            String data = StreamTool.readStream(is);
            JSONObject jsonObject = new JSONObject(data);
            location[0] = jsonObject.getDouble("latitude");
            location[1] = jsonObject.getDouble("longitude");
            System.out.println(1);
        }
        return location;
    }
}
