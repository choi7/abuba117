package com.joyblock.abuba.bus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.EobubaLocationListener;
import com.joyblock.abuba.R;
import com.joyblock.abuba.map.MapApiConst;

import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class A1_1_bus_location extends BaseActivity implements MapView.OpenAPIKeyAuthenticationResultListener, MapView.MapViewEventListener {
    String authority;
    private MapView mMapView;

    private static final int MENU_MAP_TYPE = Menu.FIRST + 1;
    private static final int MENU_MAP_MOVE = Menu.FIRST + 2;

    private static final String LOG_TAG = "MapViewDemoActivity";



    TextView logView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a1_1_bus_location);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText("실시간버스위치");
        title.setVisibility(View.VISIBLE);

        //패키지 명 출력
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        MapLayout mapLayout = new MapLayout(this);
        mMapView = mapLayout.getMapView();


        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setOpenAPIKeyAuthenticationResultListener(this);
        mMapView.setMapViewEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapLayout);


        logView = (TextView) findViewById(R.id.textView41);


        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(permissionCheck1 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},1);

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck2 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1);

        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck3 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        // LocationManager 객체를 얻어온다

        EobubaLocationListener gps=new EobubaLocationListener(this);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    5000, // 통지사이의 최소 시간간격 (miliSecond)
                    10, // 통지사이의 최소 변경거리 (m)
                    gps);//mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    5000, // 통지사이의 최소 시간간격 (miliSecond)
                    10, // 통지사이의 최소 변경거리 (m)
                    gps);//mLocationListener);
        } catch(SecurityException e){
            e.printStackTrace();
        }

    }


    MapPOIItem marker;
    public void clickPresentLocation(View v){
        if(latitude*longitude==0){
            Toast.makeText(this,"GPS가 수신되지 않습니다.",Toast.LENGTH_LONG).show();
            return;
        }

        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        logView.setText(latitude+"/"+longitude);//+location.getProvider().toString());
//        if(map_point_present!=null)
        if(marker!=null)
            mMapView.removePOIItem(marker);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");

        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker);


    }
//    public void processLocation(double latitude,double longitude){
//        super.processLocation(latitude,longitude);
//    }

//    double longitude,latitude;
//    boolean once=false;
//    private final LocationListener mLocationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//            //여기서 위치값이 갱신되면 이벤트가 발생한다.
//            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
//
//            Log.d("test", "onLocationChanged, location:" + location);
////            location.
//            longitude = location.getLongitude(); //경도
//            latitude = location.getLatitude();   //위도
//            logView.setText(location.toString());//latitude+"/"+longitude);//+location.getProvider().toString());
//            mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
//            if(!once) {
//                MapPOIItem marker = new MapPOIItem();
//                marker.setItemName("Default Marker");
//                marker.setTag(0);
//                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
//                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//                mMapView.addPOIItem(marker);
//
//                once=!once;
//            }
////            geovariable.setLatitude(latitude); // 클래스 변수에 위도 대입
////            geovariable.setLongitube(longitude);  // 클래스 변수에 경도 대입
//        }
//
//        public void onProviderDisabled(String provider) {
//            // Disabled시
//            Log.d("test", "onProviderDisabled, provider:" + provider);
//        }
//
//        public void onProviderEnabled(String provider) {
//            // Enabled시
//            Log.d("test", "onProviderEnabled, provider:" + provider);
//        }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            // 변경시
//            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
//        }
//    };



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        menu.add(0, MENU_MAP_TYPE, Menu.NONE, "MapType");
//        menu.add(0, MENU_MAP_MOVE, Menu.NONE, "Move");
//
//        return true;
//    }



//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        final int itemId = item.getItemId();
//
//        switch (itemId) {
//            case MENU_MAP_TYPE: {
//
//                //String hdMapTile = mMapView.isHDMapTileEnabled()? "HD Map Tile Off" : "HD Map Tile On";
//
//                String hdMapTile;
//
//                if (mMapView.getMapTileMode() == MapView.MapTileMode.HD2X) {
//                    hdMapTile = "Set to Standard Mode";
//                } else if (mMapView.getMapTileMode() == MapView.MapTileMode.HD) {
//                    hdMapTile = "Set to HD 2X Mode";
//                } else {
//                    hdMapTile = "Set to HD Mode";
//                }
//
//                String[] mapTypeMenuItems = { "Standard", "Satellite", "Hybrid", hdMapTile, "Clear Map Tile Cache"};
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setTitle("MapType");
//                dialog.setItems(mapTypeMenuItems, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        controlMapTile(which);
//                    }
//                });
//                dialog.show();
//
//
//                return true;
//            }
//
//            case MENU_MAP_MOVE: {
//                String rotateMapMenu = mMapView.getMapRotationAngle() == 0.0f? "Rotate Map 60" : "Unrotate Map";
//                String[] mapMoveMenuItems = { "Move to", "Zoom to", "Move and Zoom to", "Zoom In", "Zoom Out", rotateMapMenu};
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setTitle("Move");
//                dialog.setItems(mapMoveMenuItems, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        controlMapMove(which);
//                    }
//
//                });
//                dialog.show();
//
//                return true;
//            }
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void controlMapMove(int which) {
//        switch (which) {
//            case 0: // Move to
//            {
//                mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
//            }
//            break;
//            case 1: // Zoom to
//            {
//                mMapView.setZoomLevel(7, true);
//            }
//            break;
//            case 2: // Move and Zoom to
//            {
//                mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
//            }
//            break;
//            case 3: // Zoom In
//            {
//                mMapView.zoomIn(true);
//            }
//            break;
//            case 4: // Zoom Out
//            {
//                mMapView.zoomOut(true);
//            }
//            break;
//            case 5: // Rotate Map 60, Unrotate Map
//            {
//                if (mMapView.getMapRotationAngle() == 0.0f) {
//                    mMapView.setMapRotationAngle(60.0f, true);
//                } else {
//                    mMapView.setMapRotationAngle(0.0f, true);
//                }
//            }
//            break;
//        }
//    }

    /**
     * 지도 타일 컨트롤.
     */
//    private void controlMapTile(int which) {
//        switch (which) {
//            case 0: // Standard
//            {
//                mMapView.setMapType(MapView.MapType.Standard);
//            }
//            break;
//            case 1: // Satellite
//            {
//                mMapView.setMapType(MapView.MapType.Satellite);
//            }
//            break;
//            case 2: // Hybrid
//            {
//                mMapView.setMapType(MapView.MapType.Hybrid);
//            }
//            break;
//            case 3: // HD Map Tile On/Off
//            {
//                if (mMapView.getMapTileMode() == MapView.MapTileMode.HD2X) {
//                    //Set to Standard Mode
//                    mMapView.setMapTileMode(MapView.MapTileMode.Standard);
//                } else if (mMapView.getMapTileMode() == MapView.MapTileMode.HD) {
//                    //Set to HD 2X Mode
//                    mMapView.setMapTileMode(MapView.MapTileMode.HD2X);
//                } else {
//                    //Set to HD Mode
//                    mMapView.setMapTileMode(MapView.MapTileMode.HD);
//                }
//            }
//            break;
//            case 4: // Clear Map Tile Cache
//            {
//                MapView.clearMapTilePersistentCache();
//            }
//            break;
//        }
//    }

    //	/////////////////////////////////////////////////////////////////////////////////////////////////
    // net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int resultCode, String resultMessage) {
        Log.i(LOG_TAG,	String.format("Open API Key Authentication Result : code=%d, message=%s", resultCode, resultMessage));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // net.daum.mf.map.api.MapView.MapViewEventListener

    public void onMapViewInitialized(MapView mapView) {
        Log.i(LOG_TAG, "MapView had loaded. Now, MapView APIs could be called safely");
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.537229,127.005515), 2, true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapCenterPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("DaumMapLibrarySample");
        alertDialog.setMessage(String.format("Double-Tap on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("DaumMapLibrarySample");
        alertDialog.setMessage(String.format("Long-Press on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        alertDialog.setPositiveButton("OK", null);
        alertDialog.show();
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewSingleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewDragEnded (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewMoveFinished (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {
        Log.i(LOG_TAG, String.format("MapView onMapViewZoomLevelChanged (%d)", zoomLevel));
    }



}
