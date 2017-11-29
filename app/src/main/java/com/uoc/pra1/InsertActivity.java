package com.uoc.pra1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.R.attr.name;

public class InsertActivity extends AppCompatActivity {

    Button btSelectImage, btInsertItem;
    EditText etPrice, etName, etDescription;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PR2 :: Insert");



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
                    Intent intent = getIntent();
                    // Enviamos el resultado de la inserción a la actividad principal
                    intent.putExtra("result", name);
                    // La variable que introducimos en primer lugar "RESULT_OK" es de la propia actividad,
                    // no tenemos que declararla nosotros.
                    setResult(RESULT_OK, intent);

                    // Finalizamos la Activity para volver a la anterior
                    finish();
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


}
