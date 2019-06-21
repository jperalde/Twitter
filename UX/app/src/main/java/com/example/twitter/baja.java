package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class baja extends AppCompatActivity {
    private String nickLogin;
    // private TextView elTexto;
    private Button elBotonEnviar;
    private ArrayAdapter list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja);
        Intent log= getIntent();
        nickLogin=log.getExtras().getString("Nick");
    }

    public void btnRealizarBaja(View view) {
        PeticionarioREST elPeticionario = new PeticionarioREST();

		/*

		   enviarPeticion( "hola", function (res) {
		   		res
		   })

        elPeticionario.hacerPeticionREST("GET",  "http://158.42.144.126:8080/prueba", null,
			(int codigo, String cuerpo) => { } );

		   */
        String nick = null, email = null, password = null;

        final EditText nickTXT = findViewById(R.id.nick);
        EditText passwordTXT = findViewById(R.id.password);

        nick = nickTXT.getText().toString();
        password = passwordTXT.getText().toString();
        if (nick.equals(nickLogin)) {
            String cuerpo = "{\"nick\": \"" + nick + "\",\"password\": \"" + password + "\"}";
            elPeticionario.hacerPeticionREST("DELETE", "http://10.236.21.86:8080/baja", cuerpo,
                    new PeticionarioREST.RespuestaREST() {
                        @Override
                        public void callback(int codigo, String cuerpo) {
                            if (codigo == 404) {
                                Toast.makeText(baja.this, "ERROR", Toast.LENGTH_LONG);
                            }
                            Toast.makeText(baja.this, "Baja Realizada", Toast.LENGTH_LONG);
                            Intent myIntent = new Intent(baja.this, login.class);
                            startActivity(myIntent);


                            //elTexto.setText ("codigo respuesta= " + codigo + " <-> \n" + cuerpo);
                        }
                    }
            );

        }//if
    }


    public void botonIntent(View view) {

        setResult(RESULT_CANCELED);
        finish();
    }
}
