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
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        //this.elTexto.setText("pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();


        EditText nickTXT=findViewById(R.id.nick);
        EditText passwordTXT=findViewById(R.id.password);

        final String nick= nickTXT.getText().toString();
        String password= passwordTXT.getText().toString();
		/*

		   enviarPeticion( "hola", function (res) {
		   		res
		   })

        elPeticionario.hacerPeticionREST("GET",  "http://158.42.144.126:8080/prueba", null,
			(int codigo, String cuerpo) => { } );

		   */

        elPeticionario.hacerPeticionREST("GET",  "http://192.168.43.111:8080/comprobar/"+nick+"&"+password, null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {

                        if (codigo!=404){
                            Intent i= new Intent(login.this,tablon.class);
                            i.putExtra("Nick", nick);
                            startActivity(i);
                        }
                        Toast.makeText(login.this, "ERROR", Toast.LENGTH_LONG);
                        //elTexto.setText ("codigo respuesta= " + codigo + " <-> \n" + cuerpo);
                    }
                }
        );

        // (int codigo, String cuerpo) -> { elTexto.setText ("lo que sea"=; }

        // String textoJSON = "{ 'dni': '" + "23847618" + "' }";



        /*


		/* otro ejemplo:
		elPeticionario.hacerPeticionREST("POST", "http://192.168.1.113:8080/mensaje",
				"{\"dni\": \"A9182342W\", \"nombre\": \"Android\", \"apellidos\": \"De Los Palotes\"}",
				new PeticionarioREST.RespuestaREST () {
					@Override
					public void callback(int codigo, String cuerpo) {
						elTexto.setText ("cÂ—digo respuesta: " + codigo + " <-> \n" + cuerpo);
					}
		});
		*/


        //  elPeticionario.hacerPeticionREST("GET",  "https://jsonplaceholder.typicode.com/posts/2", ...
    }//
}



