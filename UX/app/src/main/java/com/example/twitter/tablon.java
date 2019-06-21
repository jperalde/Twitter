package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.Date;

public class tablon extends AppCompatActivity {
    private String nickLogin;
    // private TextView elTexto;
    private Button elBotonEnviar;
    private ArrayAdapter list_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablon);

        //this.elTexto = (TextView) findViewById(R.id.elTexto);
        this.elBotonEnviar = (Button) findViewById(R.id.botonEnviar);
        Intent log= getIntent();
        nickLogin=log.getExtras().getString("Nick");


        Log.d("clienterestandroid", "fin onCreate()");

        configInter();

    }

    private void configInter() {
        ListView lv=(ListView)findViewById(R.id.lv);
        this.list_adapter=new ArrayAdapter(this, R.layout.fila_layout,R.id.textfila);
        lv.setAdapter(this.list_adapter);
    }

    public void boton_enviar_pulsado (View quien) {
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        list_adapter.clear();
        String tweet=MainActivity.laLogicaFake.tweetsSeguidos(nickLogin)[0];
        list_adapter.add(tweet);//Añadir el tweet a la lista
        configInter();
    } // pulsado ()

    public void btncuenta(View view) {
        Intent myIntent = new Intent(this, miCuenta.class);
        myIntent.putExtra("Nick", nickLogin);
        this.startActivity(myIntent);
    }

    public void btntweet(View view) {
        Log.d("jorge", "Boton btntweet pulsado");


        EditText tweetTXT=findViewById(R.id.tweet);

        String tweet = tweetTXT.getText().toString();

        String cuerpo="{\"nick\": \"" + nickLogin + "\", \"texto\": \""+tweet+"\"}";

        String codigoR=MainActivity.laLogicaFake.enviarTweet(cuerpo)[0];
        Log.d("jorge", "empieza LogicaFake.enviarTweet()");

        if(codigoR.equals("200")) {

            Toast.makeText(tablon.this, "Enviado", Toast.LENGTH_LONG).show();

        }
        else{

            Toast.makeText(tablon.this, "ERROR", Toast.LENGTH_LONG).show();

        }
    }
}
