package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class baja extends AppCompatActivity {
    private String nickLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        Intent log= getIntent();
        nickLogin=log.getExtras().getString("Nick");
    }

    public void btnRealizarBaja(View view) {

        String nick = null;
        String password = null;

        final EditText nickTXT = findViewById(R.id.nick);
        EditText passwordTXT = findViewById(R.id.password);

        nick = nickTXT.getText().toString();
        password = passwordTXT.getText().toString();
        if (nick.equals(nickLogin)) {
            MainActivity.laLogicaFake.comprobarPassword(nickLogin, password, new LogicaFake.RespuestaLogica() {
                @Override
                public void callback(String cuerpo) {
                    MainActivity.laLogicaFake.baja(nickLogin, new LogicaFake.RespuestaLogica() {
                        @Override
                        public void callback(String cuerpo) {
                            Toast.makeText(baja.this, "Baja Realizada", Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(baja.this, login.class);
                            startActivity(myIntent);
                        }

                        @Override
                        public void fallo(int codigo) {
                            Toast.makeText(baja.this, "ERROR baja", Toast.LENGTH_LONG);

                        }
                    });
                }

                @Override
                public void fallo(int codigo) {
                    Toast.makeText(baja.this, "ERROR contraseña erronea", Toast.LENGTH_LONG);

                }
            });
        }
    }




    public void botonIntent(View view) {

        setResult(RESULT_CANCELED);
        finish();
    }
}
