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

        elPeticionario.hacerPeticionREST("GET",  "http://192.168.43.111:8080/tweetsseguidos/"+nickLogin+"&20", null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        JSONArray tws;

                        int year, month, day, hour, min, seg;
                        String nick=null, text=null, date=null,aux=null;
                        Long inst;

                        try{
                            tws=(JSONArray)new JSONTokener(cuerpo).nextValue();
                            list_adapter.clear();
                            for(int i=0;i<tws.length();i++){
                                //extraer datos al json
                                nick=tws.getJSONObject(i).getString("nick");
                                text=tws.getJSONObject(i).getString("texto");
                                inst=tws.getJSONObject(i).getLong("instante");

                                //conversion del instante a fecha

                                Date date1= new Date(inst);
                                Calendar calendar=Calendar.getInstance();
                                calendar.setTime(date1);
                                //obtener fecha en calendario segun el instante
                                year=calendar.get(Calendar.YEAR);
                                month=calendar.get(Calendar.MONTH);
                                day=calendar.get(Calendar.DAY_OF_MONTH);
                                hour=calendar.get(Calendar.HOUR_OF_DAY);
                                min=calendar.get(Calendar.MINUTE);
                                seg=calendar.get(Calendar.SECOND);

                                date=" "+day+"/"+(month+1)+"/"+year+"   "+hour+":"+min+":"+seg;

                                aux ="@"+nick+": "+text+"     "+date;

                                list_adapter.add(aux);//Añadir el tweet a la lista
                            }//for

                        } catch(JSONException ex){

                        }
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

    } // pulsado ()

    public void btncuenta(View view) {
        Intent myIntent = new Intent(this, miCuenta.class);
        myIntent.putExtra("Nick", nickLogin);
        this.startActivity(myIntent);
    }

    public void btntweet(View view) {
        PeticionarioREST elPeticionario = new PeticionarioREST();

        String nick=null, email=null, password=null;

        EditText tweetTXT=findViewById(R.id.tweet);


        String tweet = tweetTXT.getText().toString();

        String cuerpo="{\"nick\": \"" + nickLogin + "\", \"texto\": \""+tweet+"\"}";
        elPeticionario.hacerPeticionREST("POST",  "http://192.168.43.111:8080/tweet", cuerpo,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo == 404) {
                            Toast.makeText(tablon.this,"ERROR" ,Toast.LENGTH_LONG );
                        }
                        Toast.makeText(tablon.this,"Creado usuario.Todo OK" ,Toast.LENGTH_LONG );


                        //elTexto.setText ("codigo respuesta= " + codigo + " <-> \n" + cuerpo);
                    }
                }
        );
    }
}
