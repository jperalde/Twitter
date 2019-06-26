package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cambiarContra extends AppCompatActivity {
    private String nickLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Intent log= getIntent();
        nickLogin=log.getExtras().getString("Nick");

        Log.d("jorge", nickLogin);
    }


    public void botonIntent(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void boton_enviar_pulsado(View view) {
        Log.d("jorge", "boton_enviar_pulsado");


        final EditText nickTXT=findViewById(R.id.nick);
        EditText oldTXT=findViewById(R.id.old);
        EditText newpasswordTXT=findViewById(R.id.newP);

        String nick=null, newP=null, old=null;

        nick= nickTXT.getText().toString();
        newP= oldTXT.getText().toString();
        old=newpasswordTXT.getText().toString();
        if (nickLogin.equals(nick)) {
            MainActivity.laLogicaFake.cambiarPaaword(nick, newP, old, new LogicaFake.RespuestaLogica() {
                @Override
                public void callback(String cuerpo) {
                    Toast.makeText(cambiarContra.this, "Cambiada la contraseña ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void fallo(int codigo) {
                    Toast.makeText(cambiarContra.this, "ERROR en la contraseña ", Toast.LENGTH_LONG).show();


                }
            });
        }//if
    }

}
