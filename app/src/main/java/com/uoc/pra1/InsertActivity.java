package com.uoc.pra1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
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


    private static double longitude;
    private static double latitude;

    LocationManager locationManager;

    private Location location;

    private String[] permissions;

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
        locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
       // locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, locationListener);


        // Presentamos los datos de Longitud y Latitud
        txtLatitudeLongitude=(TextView)this.findViewById(R.id.textViewLatitudeLongitude);


       // Location location;

        // double latitude = location.getLatitude();
        // double longitude = location.getLongitude();
        //String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
        //Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
        //txtLatitude.setText("Latitude: " + latitude);
        //txtLongitude.setText("Longitude: " + longitude);


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
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            //Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
            //txtLatitude.setText("Latitude: " + latitude);
            //txtLongitude.setText("Longitude: " + longitude);
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

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

}



