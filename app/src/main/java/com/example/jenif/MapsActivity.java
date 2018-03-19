package com.example.jenif.univercienegamap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;
    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;


    private Button boton;
    private EditText input;
    private String direccion;
    private List <Address> address;
    ///////
    private static  int PLACE_PICKER_REQUEST = 1;

    private Button _pickAPlaceButton;
    private TextView _placeNameTextView;
    private TextView _placeAddressTextView;
    private TextView _placePhoneNumberTextView;
    private TextView _placeWebSiteTextView;
    /////

    //NOMBRES DE CONSTANTES DE LAS UBICACION DE LOS EDIFICIOS
    static final double latABR = 20.369802;
    static final double longABR = -102.770099;
    static final double LongCor= 20.370993;
    static final double LatCor= -102.770694;
    static final double latH = 20.370282;
    static final double longH= -102.770651;
    static final double latL= 20.370734;
    static final double longL= -102.770824;
    static final double latCAF= 20.370849;
    static final double longCAF= -102.770457;
    static final double latG= 20.370463;
    static final double longG= -102.770155;
    static final double latE= 20.370712;
    static final double longE= -102.769945;
    static final double latAd=20.370849;
    static final double longAd= -102.770457;
    static final double latD= 20.370967;
    static final double longD= -102.769657;
    static final double latRe =20.371153;
    static final double longRe =-102.768921;
    static final double latC= 20.371383;
    static final double longC= -102.769416;
    static final double latB = 20.371516;
    static final double longB = -102.768941;
    static final double latM = 20.371263;
    static final double longM = -102.770058;
    static final double latI = 20.371654;
    static final double longI = -102.770343;
    static final double latF = 20.371087;
    static final double longF = -102.770459;
    static final double latLabIngIndustrial = 20.371087;
    static final double longLabIngIndustrial = -102.770459;
    static final double latServiciosGenerales = 20.371330;
    static final double longServiciosGenerales = -102.770345;
    static final double latO = 20.371954;
    static final double longO = -102.770427;
    static final double latBiblioteca = 20.372060;
    static final double longBiblioteca = -102.769634;
    static final double latP = 20.372061;
    static final double longP = -102.769635;
    //FIN DE CONSTANTES


    private Button btnAnimar;
    //widgets

    private EditText mSearchText;

  //onCreate(Bundle savedInstanceState) Se llama cuando la actividad se inicia por primera vez.
  // Usted puede utilizarlo para realizar la inicialización de una sola vez como la creación de la interfaz de usuario .
  // onCreate() Toma un parámetro que es nula o alguna información de estado previamente guardado por el onSaveInstanceState .
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //es linea de codigo importante para tomar en cuenta el codigo existente fuera de  este metodo
        setContentView(R.layout.activity_maps);

        //mSearchText = (EditText) findViewById(R.id.input_search);

        input = (EditText)findViewById(R.id.editText1);
        boton = (Button)findViewById(R.id.button1);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnAnimar = (Button)findViewById(R.id.btnAnimar);
        btnAnimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animarCucienega();

            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         direccion = input.getText().toString();

                                         Geocoder geocoder = new Geocoder(MapsActivity.this);
                                         List<Address> list = new ArrayList<>();
                                         try{
                                             list = geocoder.getFromLocationName(direccion, 1);
                                         }catch (IOException e){
                                             Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                                         }

                                         if(list.size() > 0){
                                             Address address = list.get(0);

                                             Log.d(TAG, "geoLocate: found a location: " + address.toString());
                                             //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

                                             LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
                                             mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                                             moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                                                     address.getAddressLine(0));
                                         }

                                         // Ocultar el teclado
                                         InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                         imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                                     }
        });
    }



    //place picker



    @Override
    protected void onPause() {
        super.onPause();

        // Deshabilita geolocalizacion
        //location.disableMyLocation();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "onMapReady: map is ready");

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = mMap.getProjection();
                Point coord = proj.toScreenLocation(point);

                Toast.makeText(
                        MapsActivity.this,
                        "Click\n" +
                                "Lat: " + point.latitude + "\n" +
                                "Lng: " + point.longitude + "\n" +
                                "X: " + coord.x + " - Y: " + coord.y,
                        Toast.LENGTH_SHORT).show();


            }
        });
        // Controles UI
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }


        mMap.getUiSettings().setZoomControlsEnabled(true);

    }




    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);

        }

        hideSoftKeyboard();
    }
   @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 1 &&
                    permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }



    private void animarCucienega()
    {
        LatLng cucienega = new LatLng(20.3721218,-102.7689451);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(cucienega)   //Centramos el mapa en CUCIénega
                .zoom(19)         //Establecemos el zoom en 19
                .bearing(45)      //Establecemos la orientación con el noreste arriba
                .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);

        mMap.animateCamera(camUpd3);

 //Marcadores

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latL, longL ))
                .title("EDIF L")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latG, longG ))
                .title("EDIF G")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latCAF, longCAF ))
                .title("CAFETERIA")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latRe, longRe ))
                .title("RECTORIA")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latC, longC ))
                .title("EDIF C")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(LatCor, LongCor ))
                .title("COORDINACION")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latB, longB))
                .title("EDIF B")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latM, longM))
                .title("EDIF M")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latI, longI))
                .title("EDIF I")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latF, longF))
                .title("EDIF F")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLabIngIndustrial, longLabIngIndustrial))
                .title("Lab. Ing. Ind.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latServiciosGenerales, longServiciosGenerales))
                .title("Servicios Grales.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latO, longO))
                .title("EDIF O")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latBiblioteca, longBiblioteca))
                .title("Biblioteca")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latP, longP))
                .title("EDIF P")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        //Marcadores
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
