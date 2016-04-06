package keni.gd_new.maps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import keni.gd_new.R;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar toolbar;

    private int mapType = GoogleMap.MAP_TYPE_NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initToolBar();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent map = getIntent();
        double latitude = map.getDoubleExtra("latitude", 0);
        double longitude = map.getDoubleExtra("longitude", 0);
        LatLng flat = new LatLng(latitude, longitude);

        //Проверка на доступность приложения к геолокации
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        //добавление маркера и перенос камеры
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(flat, 16));
        mMap.addMarker(new MarkerOptions().position(flat));

    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_maps);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }


    // Тулбар с кнопками геолокации и типом карт
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_loc:
                MyLocation();
                break;

            case R.id.map_types:
                showDialog(0);
                break;
        }
        return true;
    }

    // Функция определения геолокации
    public void MyLocation()
    {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        // Определение координат по сети, обработка если геолокация отключена
        Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null)
        {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));
        }


    }


    // Диалог смены типа карты
    @Override
    protected Dialog onCreateDialog(int id)
    {
        final String[] ListType = {"Нормальный", "Спутник", "Рельеф", "Гибрид"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Тип Карты");
        builder.setItems(ListType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        mapType = GoogleMap.MAP_TYPE_NORMAL;
                        break;
                    case 1:
                        mapType = GoogleMap.MAP_TYPE_SATELLITE;
                        break;
                    case 2:
                        mapType = GoogleMap.MAP_TYPE_TERRAIN;
                        break;
                    case 3:
                        mapType = GoogleMap.MAP_TYPE_HYBRID;
                        break;
                }
                mMap.setMapType(mapType);

            }
        });
        return builder.create();
    }
}
