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

public class buscarUsuario extends AppCompatActivity {
    private String nickLogin;

    private ArrayAdapter list_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_usuario);
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

    public void boton_enviar_pulsado(View view) {
        Log.d("jorge", "btncargar");
        list_adapter.clear();
        EditText nickSearchTXT =findViewById(R.id.ux);
        String nickSearch =nickSearchTXT.getText().toString();
        MainActivity.laLogicaFake.tweetsPorNick(nickSearch, new LogicaFake.RespuestaLogica() {
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
                        nick=tws.getJSONObject(i).getString("nick");
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

    public void btnSeguir(View view) {
        EditText nickSearchTXT =findViewById(R.id.ux);
        final String nickSearch =nickSearchTXT.getText().toString();

        MainActivity.laLogicaFake.seguirUsuario(nickLogin, nickSearch, new LogicaFake.RespuestaLogica() {
                @Override
                public void callback(String cuerpo) {
                    Toast.makeText(buscarUsuario.this, "Has Seguido a "+nickSearch, Toast.LENGTH_LONG).show();
                    Button btn = findViewById(R.id.btnSeg);
                    btn.setEnabled(false);

                }

                @Override
                public void fallo(int codigo) {
                    Button btn = findViewById(R.id.btnSeg);
                    btn.setEnabled(false);
                    Toast.makeText(buscarUsuario.this, "Ya Sigues a este usuario", Toast.LENGTH_LONG).show();
                }
            });
    }

}


