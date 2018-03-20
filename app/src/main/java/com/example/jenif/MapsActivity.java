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
    private static final LatLng EDIF_H = new LatLng(20.370226, -102.770565);
    private static final LatLng EDIF_L = new LatLng(20.370727, -102.770804);
    private static final LatLng EDIF_G = new LatLng(20.370463, -102.770155 );
    private static final LatLng EDIF_E = new LatLng(20.370712, -102.769945 );
    private static final LatLng EDIF_D = new LatLng(20.370967, -102.769657 );
    private static final LatLng EDIF_C = new LatLng(20.371383, -102.769416 );
    private static final LatLng EDIF_B = new LatLng(20.371516, -102.768941 );
    private static final LatLng EDIF_M = new LatLng(20.371263, -102.770058  );
    private static final LatLng EDIF_I = new LatLng(20.371654, -102.770343 );
    private static final LatLng EDIF_F = new LatLng(20.371087, -102.770459  );
    private static final LatLng EDIF_O = new LatLng(20.371954, -102.770427   );
    private static final LatLng EDIF_P = new LatLng(20.372628, -102.771223 );
    private static final LatLng EDIF_RECTORIA = new LatLng(20.371153, -102.768921  );
    private static final LatLng CANCHA_FUTBOL = new LatLng(20.371093, -102.771514 );
    private static final LatLng EDIF_LAB_ING_INDUSTRIAL = new LatLng(20.371222, -102.770385  );
    private static final LatLng EDIF_SER_GENERALES = new LatLng(20.371330, -102.770345   );
    private static final LatLng CAFETERIA = new LatLng(20.370849, -102.770457);
    private static final LatLng EDIF_COORDINACION = new LatLng(20.371026, -102.770726);
    private static final LatLng LAB_BMV = new LatLng(20.370914, -102.772843);

    private Marker eH;
    private Marker eL;
    private Marker eG;
    private Marker eE;
    private Marker eD;
    private Marker eC;
    private Marker eB;
    private Marker eM;
    private Marker eI;
    private Marker eF;
    private Marker eO;
    private Marker eP;
    private Marker eRECTORIA;
    private Marker eCFUTBOL;
    private Marker eLAB_ING_INDUSTRIAL;
    private Marker eSER_GENERALES;
    private Marker eCAFETERIA;
    private Marker eCOORDINACION;
    private Marker eLABBMV;

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

                                             LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                             mMap.addMarker(new MarkerOptions().position(EDIF_P).title("EDIFICIO P"));
                                             moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
                                         }

                                         // Ocultar el teclado
                                         hideSoftKeyboard();

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

        eH = mMap.addMarker(new MarkerOptions()
                .position( EDIF_H)
                .title("EDIF H")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eL = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_L)
                        .title("EDIF L")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eG = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_G)
                        .title("EDIF G")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eE = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_E)
                        .title("EDIF E")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eD = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_D)
                        .title("EDIF D")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eC = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_C)
                        .title("EDIF C")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eB = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_B)
                        .title("EDIF B")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eM = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_M)
                        .title("EDIF M")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eI = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_I)
                        .title("EDIF I")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eF = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_F)
                        .title("EDIF F")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eO = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_O)
                        .title("EDIF O")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eP = mMap.addMarker(new MarkerOptions()
                        .position( EDIF_P)
                        .title("EDIF P")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eRECTORIA = mMap.addMarker(new MarkerOptions()
                .position( EDIF_RECTORIA)
                .title("EDIF RECTORIA")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eCFUTBOL = mMap.addMarker(new MarkerOptions()
                .position( CANCHA_FUTBOL)
                .title("CANCHA DE FUTBOL")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eLAB_ING_INDUSTRIAL = mMap.addMarker(new MarkerOptions()
                        .position(EDIF_LAB_ING_INDUSTRIAL)
                        .title("LABORATORIO DE ING INDUSTRIAL")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eSER_GENERALES = mMap.addMarker(new MarkerOptions()
                        .position(EDIF_SER_GENERALES)
                        .title("EDIF SERVICIOS GENERALES")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eCAFETERIA = mMap.addMarker(new MarkerOptions()
                        .position(CAFETERIA)
                        .title("CAFETERIA")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eCOORDINACION = mMap.addMarker(new MarkerOptions()
                        .position(EDIF_COORDINACION)
                        .title("EDIF COORDINACION-ADMINISTRACION")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));
        eLABBMV = mMap.addMarker(new MarkerOptions()
                .position(LAB_BMV)
                .title("Laboratorio de Biología Molecular Vegetal")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.edif0d)));

        //Marcadores
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
