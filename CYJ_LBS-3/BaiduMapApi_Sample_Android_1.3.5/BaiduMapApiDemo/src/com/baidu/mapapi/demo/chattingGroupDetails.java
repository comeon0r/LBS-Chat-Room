package com.baidu.mapapi.demo;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;

public class chattingGroupDetails extends Activity{

	int iZoom = 0;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		System.out.println("viePicture init...");
        setContentView(R.layout.grounddetails);
        
	}
}
