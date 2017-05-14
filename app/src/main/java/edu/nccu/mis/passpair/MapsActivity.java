package edu.nccu.mis.passpair;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.NumberFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    private GoogleMap mMap; //宣告 google map 物件
    float zoom;
    private LocationManager locMgr;
    String bestProv;
    private ListView lstPrefer;
    public String nam;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String UID = bundle.getString("UID");


        // 利用 getSupportFragmentManager() 方法取得管理器
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // 以非同步方式取得 GoogleMap 物件
        mapFragment.getMapAsync(this);


        // 取得介面元件
        lstPrefer=(ListView) findViewById(R.id.lstPrefer);

        // 建立 ArrayAdapter
        final ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, android.R.id.text1);

        // 設定 ListView 的資料來源
        lstPrefer.setAdapter(adapterBalls);

        // 設定 lstPrefer 元件 ItemClick 事件的 listener為 lstPreferListener
        lstPrefer.setOnItemClickListener(lstPreferListener);

        //讀取資料
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("User");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapterBalls.clear();

                adapterBalls.add(dataSnapshot.child("FvgpwspLhYWIqK7qwOuLEPvkQwo2").child("基本資料").child("暱稱").getValue(String.class));
                adapterBalls.add(dataSnapshot.child("TSRG4uPmjDhmFDHoVZVOVyU1AOM2").child("基本資料").child("暱稱").getValue(String.class));

                //    double Lat1r = dataSnapshot.child(UID).child("基本資料").child("Location").child("latitude").getValue(Double.class);
                 //   double Long1r = dataSnapshot.child(UID).child("基本資料").child("Location").child("longitude").getValue(Double.class);
               // for(DataSnapshot ds:dataSnapshot.getChildren()){

                 //   HashMap<String, String> mapper = new HashMap<String,String>();

                   // adapterBalls.add(dataSnapshot.child("暱稱").getValue(String.class));

/**
                    double Lat2r = dataSnapshot.child(id).child("Location").child("latitude").getValue(Double.class);
                    double Long2r = dataSnapshot.child(id).child("Location").child("longitude").getValue(Double.class);
                    float result[]=new float[1];
                    Location.distanceBetween(Lat1r,Long1r,Lat2r,Long2r,result);
                    int distance = (int) result[0];
                    int deal = distance+1;
                    Log.d("TAG", "Value is: " + result[0]);

                    if(deal > 0 && deal<2){


                        adapterBalls.add(dataSnapshot.child(L).getValue(String.class));

                    }
 **/
     //           }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



/**

        //讀取資料
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("contacts");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapterBalls.clear();
                for(int i=2;i<6;i++){

                    double Lat1r = dataSnapshot.child(UID).child("Location").child("latitude").getValue(Double.class);
                    double Long1r = dataSnapshot.child(UID).child("Location").child("longitude").getValue(Double.class);

                    String id = String.valueOf(i);

                    double Lat2r = dataSnapshot.child(id).child("Location").child("latitude").getValue(Double.class);
                    double Long2r = dataSnapshot.child(id).child("Location").child("longitude").getValue(Double.class);
                    float result[]=new float[1];
                    Location.distanceBetween(Lat1r,Long1r,Lat2r,Long2r,result);
                    int distance = (int) result[0];
                    int deal = distance+1;
                    Log.d("TAG", "Value is: " + result[0]);

                    if(deal > 0 && deal<2){

                        String L = id+"/user";
                        //  for (DataSnapshot ds : dataSnapshot.getChildren() ){

                        adapterBalls.add(dataSnapshot.child(L).getValue(String.class));

                    }
                    //          }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
**/
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 取得 GoogleMap 物件
        mMap = googleMap;
        LatLng Taipei101 = new LatLng(24.987516,121.576074); // 台北 101
        zoom = 17;
        mMap.addMarker(new MarkerOptions().position(Taipei101).title("台北 101"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Taipei101, zoom));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);       // 一般地圖
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        // 檢查授權
        requestPermission();
    }

    // 檢查授權
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {  // Androis 6.0 以上
            // 判斷是否已取得授權
            int hasPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {  // 未取得授權
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }
        // 如果裝置版本是 Androis 6.0 以下，
        // 或是裝置版本是6.0（包含）以上，使用者已經授權
        setMyLocation(); //  顯示定位圖層
    }

    // 使用者完成授權的選擇以後，會呼叫 onRequestPermissionsResult 方法
    //     第一個參數：請求授權代碼
    //     第二個參數：請求的授權名稱
    //     第三個參數：使用者選擇授權的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //按 允許 鈕
                setMyLocation(); //  顯示定位圖層
            }else{  //按 拒絕 鈕
                Toast.makeText(this, "未取得授權！", Toast.LENGTH_SHORT).show();
                finish();  // 結束應用程式
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //  顯示定位圖層
    private void setMyLocation() throws SecurityException {
        mMap.setMyLocationEnabled(true); // 顯示定位圖層
    }

    @Override
    public void onLocationChanged(Location location) {
        // 取得地圖座標值:緯度,經度
        String x="緯=" + Double.toString(location.getLatitude());
        String y="經=" + Double.toString(location.getLongitude());
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        zoom=17; //設定放大倍率1(地球)-21(街景)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
        Toast.makeText(this, x + "\n" + y, Toast.LENGTH_LONG).show();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String UID = bundle.getString("UID");
        String Ref ="User/"+UID+"/基本資料/Location";
        DatabaseReference myRef = database.getReference(Ref);


        //寫入資料庫
        myRef.setValue(Point);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 取得定位服務
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 取得最佳定位
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);

        // 如果GPS或網路定位開啟，更新位置
        if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //  確認 ACCESS_FINE_LOCATION 權限是否授權
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locMgr.requestLocationUpdates(bestProv, 1000, 1, this);
            }
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  確認 ACCESS_FINE_LOCATION 權限是否授權
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locMgr.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    // 定義 onItemClick 方法
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intentN = getIntent();
                    Bundle bundleN = intentN.getExtras();
                    final String UID = bundleN.getString("UID");

                    // 顯示 ListView 的選項內容
                    Intent intent = new Intent();
                    intent.setClass(MapsActivity.this, Second.class);

                    nam = parent.getItemAtPosition(position).toString();


                    String Ref ="User";
                    final DatabaseReference myRef = database.getReference(Ref);

                    // Read from the database
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            myRef.child(UID).child("Map資料").child(nam);

                            Integer value = dataSnapshot.child(UID).child("Map資料").child(nam).getValue(Integer.class);
                            if(value==null){
                                myRef.child(UID).child("Map資料").child(nam).setValue(0);
                            }else{
                                Integer value2 = dataSnapshot.child(UID).child("Map資料").child(nam).getValue(Integer.class);

                                int i = value2 ++;

                                myRef.child(UID).child("Map資料").child(nam).setValue(i);


                            }


                            Log.d("TAG", "onDataChange: ");
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });


                    //以bundle物件進行打包
                    Bundle bundle=new Bundle();
                    bundle.putString("NAME", nam);
                    bundle.putString("UIDFK", UID);
                    intent.putExtras(bundle);

                    // 執行附帶資料的 Intent
                    startActivity(intent);


                }
            };

}