package keni.gd_new.flats;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import keni.gd_new.R;
import keni.gd_new.app.AppController;
import keni.gd_new.mail.MailSendDialog;
import keni.gd_new.maps.MapsActivity;

public class FlatviewActivity extends AppCompatActivity
{
    private String id, image, title, clas, type, district;
    private int place, price;
    private ArrayList<String> images_url;

    private GoogleMap googleMap;
    private double latitude, longitude;
    private float distance;
    DialogFragment booking;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_view);
        initToolBar();

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        LinearLayout imagesLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        TextView textViewID = (TextView) findViewById(R.id.textID);
        TextView textViewDistrict = (TextView) findViewById(R.id.textDistrict);
        TextView textViewClass = (TextView) findViewById(R.id.textClass);
        TextView textViewType = (TextView) findViewById(R.id.textType);
        TextView textViewAddress = (TextView) findViewById(R.id.textAddress);
        TextView textViewPlace = (TextView) findViewById(R.id.textPlace);
        TextView textViewPrice = (TextView) findViewById(R.id.textViewPrice);

        Intent info = getIntent();

        id = info.getStringExtra("id");
        title = info.getStringExtra("title");
        clas = info.getStringExtra("class");
        type = info.getStringExtra("type");
        district = info.getStringExtra("district");
        place = info.getIntExtra("place", 0);
        price = info.getIntExtra("price", 0);
        images_url = info.getStringArrayListExtra("images");


        for (int i = 0; i < images_url.size(); i++)
        {
            NetworkImageView images_all = new NetworkImageView(FlatviewActivity.this);
            images_all.setImageUrl(images_url.get(i), imageLoader);
            images_all.setLayoutParams(layoutParams);
            imagesLayout.addView(images_all);
        }


        textViewID.setText(id);
        textViewAddress.setText(title);
        textViewClass.setText(clas);
        textViewType.setText(type);
        textViewDistrict.setText(district);
        textViewPlace.setText(" " + place);
        textViewPrice.setText(price + " Р/сут.");


        try {
            //Передача координат
            latitude = info.getDoubleExtra("latitude", 0);
            longitude = info.getDoubleExtra("longitude", 0);

            //Функция инициализации карты
            initializeMap();

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(15).build();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.addMarker(marker);

            //Запрет на приближение, прокручивание и поворачивания камеры
            googleMap.getUiSettings().setZoomGesturesEnabled(false);
            googleMap.getUiSettings().setScrollGesturesEnabled(false);
            googleMap.getUiSettings().setRotateGesturesEnabled(false);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void Images(View v)
    {
        Intent images = new Intent(FlatviewActivity.this, ImagesActivity.class);
        images.putExtra("images_url", images_url);
        startActivity(images);
    }

    public void Booking(View v)
    {
        booking = new MailSendDialog();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        booking.setArguments(args);
        booking.show(getFragmentManager(), "Бронирование");
    }

    private void initializeMap()
    {
        if (googleMap == null)
        {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


            if (googleMap == null)
            {
                Toast.makeText(getApplicationContext(), "Невозможно загрузить карту", Toast.LENGTH_SHORT).show();
            }
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Log.d("Map", "Map clicked");
                Intent map = new Intent(FlatviewActivity.this, MapsActivity.class);
                map.putExtra("latitude", latitude);
                map.putExtra("longitude", longitude);
                startActivity(map);
            }
        });
    }

    public void initToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.flat);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }
}
