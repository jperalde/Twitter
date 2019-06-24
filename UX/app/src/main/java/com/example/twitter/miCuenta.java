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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.Date;

public class miCuenta extends AppCompatActivity {
    private String nickLogin;
    // private TextView elTexto;
    private Button elBotonEnviar;
    private ArrayAdapter list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Intent log= getIntent();
        nickLogin=log.getExtras().getString("Nick");
        configInter();
        Log.d("jorge", nickLogin);

    }

    private void configInter() {
        ListView lv=findViewById(R.id.lv);
        this.list_adapter=new ArrayAdapter(miCuenta.this, R.layout.fila_layout,R.id.textfila);
        lv.setAdapter(this.list_adapter);
    }



    public void btnVolver(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    public void btnBaja(View view) {
        Intent myIntent = new Intent(this, baja.class);
        myIntent.putExtra("Nick", nickLogin);
        this.startActivity(myIntent);
    }
    public void btncargar (View quien) {
        Log.d("jorge", "btncargar");
        list_adapter.clear();
        MainActivity.laLogicaFake.tweetsPorNick(nickLogin, new LogicaFake.RespuestaLogica() {
            @Override
            public void callback(String cuerpo) {
                int year;
                int month;
                int day;
                int hour;
                int min;
                int seg;
                String nick=null;
                String text = null;
                String date = null;
                String aux = null;
                Long inst;
                JSONArray tws;
                try {
                    tws = (JSONArray) new JSONTokener(cuerpo).nextValue();
                    for (int i = 0; i < tws.length(); i++) {
                        //extraer datos al json
                        nick = tws.getJSONObject(i).getString("nick");

                        text = tws.getJSONObject(i).getString("texto");
                        inst = tws.getJSONObject(i).getLong("instante");

                        //conversion del instante a fecha

                        Date date1 = new Date(inst);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        //obtener fecha en calendario segun el instante
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        hour = calendar.get(Calendar.HOUR_OF_DAY);
                        min = calendar.get(Calendar.MINUTE);
                        seg = calendar.get(Calendar.SECOND);

                        date = " " + day + "/" + (month + 1) + "/" + year + "   " + hour + ":" + min + ":" + seg;

                        aux = "@" + nick + ": " + text + "     " + date;
                        list_adapter.add(aux);//Añadir el tweet a la lista

                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void fallo(int codigo) {

            }
        });

    }

    public void btnBuscar(View view) {
        Intent myIntent = new Intent(this, buscarUsuario.class);
        myIntent.putExtra("Nick", nickLogin);
        this.startActivity(myIntent);
    }
}
