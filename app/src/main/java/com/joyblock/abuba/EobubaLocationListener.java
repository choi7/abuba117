package com.joyblock.abuba;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;



/**
 * Created by BLUE1 on 2018-01-28.
 */

public class EobubaLocationListener implements LocationListener {
    BaseActivity activity;
    public EobubaLocationListener(BaseActivity activity){
        this.activity=activity;
    }

    public void onLocationChanged(Location location) {
        //여기서 위치값이 갱신되면 이벤트가 발생한다.
        //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

        Log.d("location", "onLocationChanged, location:" + location);
        activity.processLocation(location.getLatitude(),location.getLongitude());

//            location.
//        longitude = location.getLongitude(); //경도
//        latitude = location.getLatitude();   //위도
//        logView.setText(location.toString());//latitude+"/"+longitude);//+location.getProvider().toString());
//        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
//        if(!once) {
//            MapPOIItem marker = new MapPOIItem();
//            marker.setItemName("Default Marker");
//            marker.setTag(0);
//            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
//            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//            mMapView.addPOIItem(marker);
//
//            once=!once;
//        }
//            geovariable.setLatitude(latitude); // 클래스 변수에 위도 대입
//            geovariable.setLongitube(longitude);  // 클래스 변수에 경도 대입
    }

    public void onProviderDisabled(String provider) {
        // Disabled시
        Log.d("location", "onProviderDisabled, provider:" + provider);
    }

    public void onProviderEnabled(String provider) {
        // Enabled시
        Log.d("location", "onProviderEnabled, provider:" + provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // 변경시
        Log.d("location", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
    }

}
