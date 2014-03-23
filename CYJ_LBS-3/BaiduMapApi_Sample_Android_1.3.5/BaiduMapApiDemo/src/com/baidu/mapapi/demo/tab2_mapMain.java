package com.baidu.mapapi.demo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class tab2_mapMain extends MapActivity {

	static MapView mMapView = null;
	LocationListener mLocationListener = null;//onResume时注册此listener，onPause时需要Remove
	MyLocationOverlay mLocationOverlay = null;	//定位图层

	static View mPopView = null;	// 点击mark时弹出的气泡View
	int iZoom = 0;
	OverItemT overitem = null;

	Button takePictureAndUpload;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapmain);

		mMapView = (MapView)findViewById(R.id.bmapView);

		//以下是地图控件相关        
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
		}
		app.mBMapMan.start();
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mBMapMan);

		//        mMapView = (MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		//设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);

		// 添加定位图层
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);

		// 注册定位事件

		mLocationListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				if (location != null){
					GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
							(int)(location.getLongitude()*1e6));
					mMapView.getController().animateTo(pt);
					//显示定位的数值
					String strLog = String.format("您当前的位置:"+"经度:%f"+"纬度:%f",location.getLongitude(), location.getLatitude());

					TextView mainText = (TextView)findViewById(R.id.mylocation);
					mainText.setText(strLog);
				}
			}
		};


		GeoPoint point =new GeoPoint((int)(32.116860*1e6), (int)(118.969793*1e6));
		mMapView.getController().setCenter(point);
		// 添加ItemizedOverlay
		Drawable marker = getResources().getDrawable(R.drawable.newthings);  //得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight());   //为maker定义位置和边界
		overitem = new OverItemT(marker, this, 3);
		mMapView.getOverlays().add(overitem); //添加ItemizedOverlay实例到mMapView

		// 创建点击mark时的弹出泡泡
		mPopView=super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView( mPopView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						null, MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
		System.out.println("point!!!");
		iZoom = mMapView.getZoomLevel();
	}



	//跳转到显示图片的activity
	void enterTheChattingGroup(){
		//显示图片
		try {
			Intent intent = null;
			intent = new Intent(tab2_mapMain.this, chattingGroupUI.class);
			System.out.println("view Picture");
			this.startActivity(intent);
		} catch ( ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		mLocationOverlay.disableMyLocation();
		mLocationOverlay.disableCompass(); // 关闭指南针
		app.mBMapMan.stop();
		System.out.println("good onPause");
		super.onPause();
	}
	@Override
	protected void onResume() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		// 注册定位事件，定位后将地图移动到定位点
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针
		app.mBMapMan.start();
		System.out.println("good onResume");
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		System.out.println("good isRouteDisplayed");
		return false;
	}


	public class OverItemT extends ItemizedOverlay<OverlayItem> {

		public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		private Drawable marker;
		private Context mContext;

		private double mLat1 =32.116860; // point1纬度
		private double mLon1 = 118.969793; // point1经度

		private double mLat2 = 32.116860;
		private double mLon2 = 118.979793;

		private double mLat3 = 32.116860;
		private double mLon3 = 118.869793;

		public OverItemT(Drawable marker, Context context, int count) {



			super(boundCenterBottom(marker));

			System.out.println("OverItemT init");

			this.marker = marker;
			this.mContext = context;

			// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
			GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));

			// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
			mGeoList.add(new OverlayItem(p1, "P1", "point1"));
			mGeoList.add(new OverlayItem(p2, "P2", "point2"));
			if(count == 3)
			{
				GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
				mGeoList.add(new OverlayItem(p3, "P3", "point3"));
			}
			populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
		}

		public void updateOverlay()
		{
			System.out.println("good updateOverlay");
			populate();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
			Projection projection = mapView.getProjection(); 
			for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
				OverlayItem overLayItem = getItem(index); // 得到给定索引的item

				String title = overLayItem.getTitle();
				// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
				Point point = projection.toPixels(overLayItem.getPoint(), null); 

				// 可在此处添加您的绘制代码
				Paint paintText = new Paint();
				paintText.setColor(Color.BLUE);
				paintText.setTextSize(15);
				canvas.drawText(title, point.x-30, point.y, paintText); // 绘制文本
			}
			System.out.println("good draw");

			super.draw(canvas, mapView, shadow);
			//调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
			boundCenterBottom(marker);
		}

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			System.out.println("good createItem");
			return mGeoList.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			System.out.println("good size");
			return mGeoList.size();
		}
		@Override
		// 处理当点击事件
		protected boolean onTap(int i) {
			System.out.println("good onTap "+i);
			setFocus(mGeoList.get(i));
			// 更新气泡位置,并使之显示
			GeoPoint pt = mGeoList.get(i).getPoint();
			tab2_mapMain.mMapView.updateViewLayout( tab2_mapMain.mPopView,
					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
							pt, MapView.LayoutParams.BOTTOM_CENTER));
			tab2_mapMain.mPopView.setVisibility(View.VISIBLE);
			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
					Toast.LENGTH_SHORT).show();

			//显示气泡之后，跳转，并显示图片
			enterTheChattingGroup();




			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			System.out.println("onTap(GeoPoint)");
			// TODO Auto-generated method stub
			// 消去弹出的气泡
			tab2_mapMain.mPopView.setVisibility(View.GONE);
			return super.onTap(arg0, arg1);
		}
	}


}



