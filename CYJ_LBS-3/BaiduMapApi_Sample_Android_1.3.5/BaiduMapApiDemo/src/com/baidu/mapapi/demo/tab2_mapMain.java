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
	LocationListener mLocationListener = null;//onResumeʱע���listener��onPauseʱ��ҪRemove
	MyLocationOverlay mLocationOverlay = null;	//��λͼ��

	static View mPopView = null;	// ���markʱ����������View
	int iZoom = 0;
	OverItemT overitem = null;

	Button takePictureAndUpload;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapmain);

		mMapView = (MapView)findViewById(R.id.bmapView);

		//�����ǵ�ͼ�ؼ����        
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
		}
		app.mBMapMan.start();
		// ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		super.initMapActivity(app.mBMapMan);

		//        mMapView = (MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		//���������Ŷ���������Ҳ��ʾoverlay,Ĭ��Ϊ������
		mMapView.setDrawOverlayWhenZooming(true);

		// ��Ӷ�λͼ��
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);

		// ע�ᶨλ�¼�

		mLocationListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				if (location != null){
					GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
							(int)(location.getLongitude()*1e6));
					mMapView.getController().animateTo(pt);
					//��ʾ��λ����ֵ
					String strLog = String.format("����ǰ��λ��:"+"����:%f"+"γ��:%f",location.getLongitude(), location.getLatitude());

					TextView mainText = (TextView)findViewById(R.id.mylocation);
					mainText.setText(strLog);
				}
			}
		};


		GeoPoint point =new GeoPoint((int)(32.116860*1e6), (int)(118.969793*1e6));
		mMapView.getController().setCenter(point);
		// ���ItemizedOverlay
		Drawable marker = getResources().getDrawable(R.drawable.newthings);  //�õ���Ҫ���ڵ�ͼ�ϵ���Դ
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight());   //Ϊmaker����λ�úͱ߽�
		overitem = new OverItemT(marker, this, 3);
		mMapView.getOverlays().add(overitem); //���ItemizedOverlayʵ����mMapView

		// �������markʱ�ĵ�������
		mPopView=super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView( mPopView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						null, MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
		System.out.println("point!!!");
		iZoom = mMapView.getZoomLevel();
	}



	//��ת����ʾͼƬ��activity
	void enterTheChattingGroup(){
		//��ʾͼƬ
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
		mLocationOverlay.disableCompass(); // �ر�ָ����
		app.mBMapMan.stop();
		System.out.println("good onPause");
		super.onPause();
	}
	@Override
	protected void onResume() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // ��ָ����
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

		private double mLat1 =32.116860; // point1γ��
		private double mLon1 = 118.969793; // point1����

		private double mLat2 = 32.116860;
		private double mLon2 = 118.979793;

		private double mLat3 = 32.116860;
		private double mLon3 = 118.869793;

		public OverItemT(Drawable marker, Context context, int count) {



			super(boundCenterBottom(marker));

			System.out.println("OverItemT init");

			this.marker = marker;
			this.mContext = context;

			// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
			GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));

			// ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
			mGeoList.add(new OverlayItem(p1, "P1", "point1"));
			mGeoList.add(new OverlayItem(p2, "P2", "point2"));
			if(count == 3)
			{
				GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
				mGeoList.add(new OverlayItem(p3, "P3", "point3"));
			}
			populate();  //createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
		}

		public void updateOverlay()
		{
			System.out.println("good updateOverlay");
			populate();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection�ӿ�������Ļ��������;�γ������֮��ı任
			Projection projection = mapView.getProjection(); 
			for (int index = size() - 1; index >= 0; index--) { // ����mGeoList
				OverlayItem overLayItem = getItem(index); // �õ�����������item

				String title = overLayItem.getTitle();
				// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������
				Point point = projection.toPixels(overLayItem.getPoint(), null); 

				// ���ڴ˴�������Ļ��ƴ���
				Paint paintText = new Paint();
				paintText.setColor(Color.BLUE);
				paintText.setTextSize(15);
				canvas.drawText(title, point.x-30, point.y, paintText); // �����ı�
			}
			System.out.println("good draw");

			super.draw(canvas, mapView, shadow);
			//����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ������
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
		// ��������¼�
		protected boolean onTap(int i) {
			System.out.println("good onTap "+i);
			setFocus(mGeoList.get(i));
			// ��������λ��,��ʹ֮��ʾ
			GeoPoint pt = mGeoList.get(i).getPoint();
			tab2_mapMain.mMapView.updateViewLayout( tab2_mapMain.mPopView,
					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
							pt, MapView.LayoutParams.BOTTOM_CENTER));
			tab2_mapMain.mPopView.setVisibility(View.VISIBLE);
			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
					Toast.LENGTH_SHORT).show();

			//��ʾ����֮����ת������ʾͼƬ
			enterTheChattingGroup();




			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			System.out.println("onTap(GeoPoint)");
			// TODO Auto-generated method stub
			// ��ȥ����������
			tab2_mapMain.mPopView.setVisibility(View.GONE);
			return super.onTap(arg0, arg1);
		}
	}


}



