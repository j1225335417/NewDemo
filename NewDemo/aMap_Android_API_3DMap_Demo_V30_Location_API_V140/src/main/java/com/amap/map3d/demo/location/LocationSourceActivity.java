package com.amap.map3d.demo.location;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.map3d.demo.R;

/**
 * AMapV2地图中简单介绍显示定位小蓝点
 */
public class LocationSourceActivity extends Activity implements LocationSource,
		AMapLocationListener ,GeocodeSearch.OnGeocodeSearchListener {
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker marker;// 定位雷达小图标
	private GeocodeSearch geocoderSearch;
	private PoiResultAdapter adpter;
	private ListView listView;
	private boolean flag = true;
	private ImageView icon;
	private ProgressBar mProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationsource_activity);
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
	    //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
//        MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		listView = (ListView) findViewById(R.id.listview);
		icon = (ImageView) findViewById(R.id.icon);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		mapView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
				return true;
			}
		});
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
			aMap.getUiSettings().setZoomGesturesEnabled(false);
			aMap.getUiSettings().setZoomControlsEnabled(false);
			aMap.getScalePerPixel();
			geocoderSearch = new GeocodeSearch(this);
			geocoderSearch.setOnGeocodeSearchListener(this);
			aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition cameraPosition) {
				}

				@Override
				public void onCameraChangeFinish(CameraPosition cameraPosition) {
					translate(icon);
					mProgressBar.setVisibility(View.VISIBLE);
					LatLng l = cameraPosition.target;
					LatLonPoint latLonPoint = new LatLonPoint(l.latitude, l.longitude);
					getAddress(latLonPoint);
				}
			});
			setUpMap();
		}
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				List<PoiItem> data = ((PoiResultAdapter)listView.getAdapter()).getData();
				PoiItem poi = data.get(i);
				Toast.makeText(LocationSourceActivity.this, "名称："+poi.getTitle()+"\n"
						+"经纬度："+poi.getLatLonPoint().getLatitude()+"、"+poi.getLatLonPoint().getLongitude(), Toast.LENGTH_SHORT).show();
			}
		});
 
	}
	//popupwindow动画
	private void translate(View view) {
		TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 20);
		anim.setDuration(200);
		anim.setRepeatCount(2);
		anim.setRepeatMode(Animation.REVERSE);
		view.setAnimation(anim);
	}
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
//		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
//		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point1));
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point2));
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point3));
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point4));
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point5));
//		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point6));
		marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				/*.icons(giflist)*/.period(100));
		// 自定义系统定位小蓝点
//		MyLocationStyle myLocationStyle = new MyLocationStyle();
//		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//				.fromResource(R.drawable.knight_end));// 设置小蓝点的图标
////		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
////		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
////		myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
//		aMap.setMyLocationStyle(myLocationStyle);
		Marker marker = aMap.addMarker(new MarkerOptions()

				.title("好好学习")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.setRotateAngle(90);// 设置marker旋转90度
		marker.setPositionByPixels(400, 400);
		marker.showInfoWindow();// 设置默认显示一个infowinfow




		aMap.setMyLocationRotateAngle(100);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		//设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种 
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	 
	
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
//		Toast.makeText(LocationSourceActivity.this, "123", Toast.LENGTH_SHORT).show();
		if (mListener != null && aLocation != null&&flag) {
			mAMapLocationManager.removeUpdates(this);
//			flag = false;
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
			marker.setPosition(new LatLng(aLocation.getLatitude(), aLocation
					.getLongitude()));// 定位雷达小图标
			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
//			mAMapLocationManager.startSocket();
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();;
		}
		mAMapLocationManager = null;
	}


	@Override
	public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
		mProgressBar.setVisibility(View.GONE);
		List<PoiItem> poiItems =  regeocodeResult.getRegeocodeAddress().getPois();
		if(adpter==null){
			adpter = new PoiResultAdapter(poiItems,LocationSourceActivity.this,true);
			listView.setAdapter(adpter);
		}else {
			adpter.refresh(poiItems);
		}
	}

	@Override
	public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

	}
}
