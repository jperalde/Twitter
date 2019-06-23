package com.example.twitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class otra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
    }
    public void botonIntent(View btn){

        setResult(RESULT_CANCELED);
        finish();

    }

    public void boton_enviar_pulsado(View btn) {
        Log.d("jorge", "boton_enviar_pulsado");


        final EditText nickTXT=findViewById(R.id.nick);
        EditText emailTXT=findViewById(R.id.email);
        EditText passwordTXT=findViewById(R.id.password);

        String nick=null, email=null, password=null;

        nick= nickTXT.getText().toString();
        email= emailTXT.getText().toString();
        password= passwordTXT.getText().toString();

        MainActivity.laLogicaFake.altaUsuario(nick, email, password, new LogicaFake.RespuestaLogica() {
            @Override
            public void callback(String cuerpo) {
                Toast.makeText(otra.this, "Dado de Alta", Toast.LENGTH_LONG).show();
            }

            @Override
            public void fallo(int codigo) {
                Toast.makeText(otra.this, "ERROR", Toast.LENGTH_LONG).show();

            }
        });

    }
}

