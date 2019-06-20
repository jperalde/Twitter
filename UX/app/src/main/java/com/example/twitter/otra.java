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
        Log.d("clienterestandroid", "boton_enviar_pulsado");
        //this.elTexto.setText("pulsado");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

		/*

		   enviarPeticion( "hola", function (res) {
		   		res
		   })

        elPeticionario.hacerPeticionREST("GET",  "http://158.42.144.126:8080/prueba", null,
			(int codigo, String cuerpo) => { } );

		   */
        String nick=null, email=null, password=null;

        final EditText nickTXT=findViewById(R.id.nick);
        EditText emailTXT=findViewById(R.id.email);
        EditText passwordTXT=findViewById(R.id.password);

        nick= nickTXT.getText().toString();
        email= emailTXT.getText().toString();
        password= passwordTXT.getText().toString();
        String cuerpo="{\"nick\": \"" + nick + "\", \"email\": \""+email+"\", \"password\": \""+password+"\"}";
        elPeticionario.hacerPeticionREST("POST",  "http://192.168.43.111:8080/alta", cuerpo,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo == 404) {
                            Toast.makeText(otra.this,"ERROR" ,Toast.LENGTH_LONG );
                        }
                        Toast.makeText(otra.this,"Creado usuario.Todo OK" ,Toast.LENGTH_LONG );


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

    }
}

