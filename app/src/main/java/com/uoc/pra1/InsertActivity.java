package com.uoc.pra1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.datalevel.DataException;
import com.uoc.datalevel.DataObject;
import com.uoc.datalevel.SaveCallback;

import static android.R.attr.name;
import static com.uoc.pra1.R.id.imageView;


public class InsertActivity extends AppCompatActivity {

    Context mContext;

    Button btChangeImage, btInsertItem;
    EditText etPrice, etName, etDescription;
    ImageView image;
    TextView txtLatitudeLongitude;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    public LocationManager locationManager;
    public LocationListener locationListener;

    private static double longitude;
    private static double latitude;

    private Location location;

    private String[] permissions;




    private static final String ERR_PERMISSION = "Permission denied. Check permissions on AndroidManifest.xml.";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mContext=this;


        // Cambiamos el título de la actividad
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PR2 :: Insert");

        // Location
        // Presentamos los datos de Longitud y Latitud
        txtLatitudeLongitude=(TextView)this.findViewById(R.id.textViewLatitudeLongitude);

        locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
       // isLocationEnabled();

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null){
            // set TextView
            latitude=location.getLatitude();
            longitude=location.getLongitude();

           // String msg = "New Latitude: "+String.valueOf(location.getLatitude())+"New Longitude: "+String.valueOf(location.getLongitude());
           // Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
            txtLatitudeLongitude.setText("lat:"+latitude+" lon:"+longitude);
        }else{
            String msg = "Ha fallado la obtención de los datos de posición";
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        }
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locationListener);


 /*       locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    if(location != null) {
                        txtLatitudeLongitude.setText("Latitude="+location.getLatitude()+" " + "Longitude="+location.getLongitude());
                    };

                    if (ContextCompat.checkSelfPermission(InsertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // Ask permission
                        ActivityCompat.requestPermissions(InsertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PETICION_PERMISO_LOCALIZACION);
                    } else {
                        // Ask last know location
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                         if(location!=null){
                            // set TextView
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                           // String msg = "New Latitude: "+location.getLatitude()+"New Longitude: "+location.getLongitude();
                            String msg = "New Latitude: "+String.valueOf(location.getLatitude())+"New Longitude: "+String.valueOf(location.getLongitude());
                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                            txtLatitudeLongitude.setText("lat:"+latitude+" lon:"+longitude);
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        }
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            };
*/
        // Definimos la imagen, el botón para cambiar la imagen y el listener asociado
        image = (ImageView) findViewById(imageView);

        btChangeImage = (Button)findViewById(R.id.btnChangeImage);

        btChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Bitmap bMap = BitmapFactory.decodeFile("/sdcard/test.png");
              //  image.setImageBitmap(bMap);

                    selectImage();
                
            }
        });

        // Enlazamos las variables con los componentes que tenemos en el XML
        btInsertItem = (Button)findViewById(R.id.inserItemButton);

        // Definimos el listener que ejcutará el método onClick del botón InserItem
        btInsertItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int chequear = 3;

                etPrice = (EditText)findViewById(R.id.priceEditText);

                if(etPrice.getText().length()!=0) {
                    // Si el price no es vacío recogemos el dato introducido
                    String price = etPrice.getText().toString();
                    chequear--;
                } else {
                    // Si el price es vacío
                    Toast.makeText(InsertActivity.this, "No se ha introducido nada en price", Toast.LENGTH_SHORT).show();
                }

                etName = (EditText)findViewById(R.id.nameEditText);

                if(etName.getText().length()!=0) {
                    // Si el name no es vacío recogemos el dato introducido
                    String name = etName.getText().toString();
                    chequear--;
                } else {
                    // Si el name es vacío
                    Toast.makeText(InsertActivity.this, "No se ha introducido nada en name", Toast.LENGTH_SHORT).show();
                }

                etDescription = (EditText)findViewById(R.id.descriptionEditText);

                if(etDescription.getText().length()!=0) {
                    // Si el description no es vacío recogemos el dato introducido
                    String description = etDescription.getText().toString();
                    chequear--;
                } else {
                    // Si el description  es vacío
                    Toast.makeText(InsertActivity.this, "No se ha introducido nada en description", Toast.LENGTH_SHORT).show();
                }

                if(chequear == 0) {
                    // Recuperamos el intent que ha llamado a esta actividad
                    final Intent intent = getIntent();
                    // Enviamos el resultado de la inserción a la actividad principal
                    intent.putExtra("result", name);


                    // Creamos el nuevo DataObject
                    DataObject new_item = new DataObject("item");
                    new_item.put("name", etName.getText());
                    new_item.put("description", etDescription.getText());
                    new_item.put("price", etPrice.getText());
                    new_item.put("image", ((BitmapDrawable) image.getDrawable()).getBitmap());

                    new_item.saveInBackground(new SaveCallback<DataObject>() {
                                                  @Override
                                                  public void done(DataObject object, DataException e) {
                                                      if (e == null) {
                                                          // La variable que introducimos en primer lugar "RESULT_OK" es de la propia actividad,
                                                          // no tenemos que declararla nosotros.
                                                          setResult(RESULT_OK, intent);
                                                          Toast.makeText(InsertActivity.this, "New item added!", Toast.LENGTH_LONG).show();
                                                          // Finalizamos la Activity para volver a la anterior
                                                          finish();
                                                      } else {
                                                          // Mostramos que se ha producido un error al intentar grabar el nuevo item

                                                      }
                                                  }
                                              });


                }


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case 101: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    } else {
                    }
                    return;
                }
            }
        } catch(SecurityException ERR_PERMISSION) { }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(InsertActivity.this);
        builder.setTitle("Add Photo! Open from:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=utility.checkPermission(InsertActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
     /*       AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
      */  }
        else{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Confirm Location");
            alertDialog.setMessage("Your Location is enabled, please enjoy");
            alertDialog.setNegativeButton("Back to interface",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }

 /*   protected void onResume(){
        super.onResume();
        isLocationEnabled();
    }
*/


}



