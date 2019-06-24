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
import org.json.JSONObject;
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
        MainActivity.laLogicaFake.tweetsSeguidos(nickLogin,new LogicaFake.RespuestaLogica(){
            @Override
            public void callback(String cuerpo) {
                int year;
                int month;
                int day;
                int hour;
                int min;
                int seg;
                String nick = null;
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
                    e.printStackTrace();
                }
            }

            @Override
            public void fallo(int codigo) {
                Toast.makeText(tablon.this, "ERROR", Toast.LENGTH_LONG).show();

            }
        });
       // list_adapter.add(tweet);//Añadir el tweet a la lista
        configInter();
    } // pulsado ()

    public void btncuenta(View view) {
        Intent myIntent = new Intent(tablon.this, miCuenta.class);
        myIntent.putExtra("Nick", nickLogin);
        this.startActivity(myIntent);
    }

    public void btntweet(View view) {
        Log.d("jorge", "Boton btntweet pulsado");


        EditText tweetTXT=findViewById(R.id.tweet);

        String tweet = tweetTXT.getText().toString();

        String cuerpo="{\"nick\": \"" + nickLogin + "\", \"texto\": \""+tweet+"\"}";
        Log.d("jorge", "empieza LogicaFake.enviarTweet()");
        MainActivity.laLogicaFake.enviarTweet(cuerpo, new LogicaFake.RespuestaLogica() {
            @Override
            public void callback(String cuerpo) {
                Toast.makeText(tablon.this, "Enviado", Toast.LENGTH_LONG).show();
                Log.d("jorge", "Acabada LogicaFake.enviarTweet()"+cuerpo);

            }

            @Override
            public void fallo(int codigo) {
                Toast.makeText(tablon.this, "ERROR", Toast.LENGTH_LONG).show();
                Log.d("jorge", "Acabada LogicaFake.enviarTweet()"+codigo);

            }
        });


    }
}
