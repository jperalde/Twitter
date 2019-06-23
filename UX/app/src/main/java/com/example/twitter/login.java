package com.example.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

    }


    public void botonAlta(View btn) {
        Log.d("exp", "intent to second");
        Intent myIntent = new Intent(this, otra.class);
        this.startActivity(myIntent);
    }

    public void botoncomprobar(View btn){
        Log.d("jorge", "boton_enviar_pulsado");
        //this.elTexto.setText("pulsado");

        EditText nickTXT=findViewById(R.id.nick);
        EditText passwordTXT=findViewById(R.id.password);

        final String nick= nickTXT.getText().toString();
        String password= passwordTXT.getText().toString();

        MainActivity.laLogicaFake.comprobarPassword(nick, password, new LogicaFake.RespuestaLogica() {
            @Override
            public void callback(String cuerpo) {
                Log.d("jorge", "Llamado metodo laLogicaFake.comprobarPassword() desde LOGIN Intent a TABLON");
                Intent i = new Intent(login.this, tablon.class);
                i.putExtra("Nick", nick);
                startActivity(i);
                Log.d("jorge", "Comenzada actividad TABLON");

            }

            @Override
            public void fallo(int codigo) {
                Log.d("jorge", "ERROR en metodo laLogicaFake.comprobarPassword() desde LOGIN");
                Toast.makeText(login.this,"ERROR" ,LENGTH_LONG ).show();
            }
        });

    }//
}



