package com.liuhao.smartcrutch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.DPoint;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.location.CoordinateConverter;
import com.liuhao.smartcrutch.tools.LocationTool;
import static com.amap.api.location.CoordinateConverter.CoordType.GPS;
import static com.amap.api.location.CoordinateConverter.CoordType.MAPBAR;


public class MainActivity extends AppCompatActivity {
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    private MapView mMap = null;
    private AMap aMap = null;
    private double[] locations;
    private LatLng latLng;
    private CameraUpdate cUpdate;
    private MarkerOptions markerOptions;
    private CoordinateConverter converter;
    private DPoint dPoint;
    private DPoint point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMap = (MapView) findViewById(R.id.amap);
        mMap.onCreate(savedInstanceState);
        converter = new CoordinateConverter(this);
        //aMap = mMap.getMap();
        new Thread(){
            public void run(){
                try{
                    locations = new LocationTool().getLocation();
                    dPoint = new DPoint(locations[0], locations[1]);
                    converter.from(GPS);
                    converter.coord(dPoint);
                    point = converter.convert();
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }

        }.start();
    }

    protected  void onDestroy(){
        super.onDestroy();
        mMap.onDestroy();
    }
    protected void onPause(){
        super.onPause();
        mMap.onPause();
    }
    protected void onResume(){
        super.onResume();
        mMap.onResume();
    }
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message message){
            switch (message.what){
                case SUCCESS:
                    init(mMap);
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this,"Can't get info",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this,"Can't get info",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public void init(MapView mapView) {
        if (aMap == null) {
            aMap = mapView.getMap();
            latLng = new LatLng(point.getLatitude(), point.getLongitude());
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            CameraPosition cPostion = CameraPosition.fromLatLngZoom(latLng,19);
            cUpdate = CameraUpdateFactory.newCameraPosition(cPostion);
            aMap.animateCamera(cUpdate);
            aMap.addMarker(markerOptions);
            //
        }
    }
}
