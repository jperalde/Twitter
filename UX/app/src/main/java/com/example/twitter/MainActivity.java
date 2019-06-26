package com.example.twitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String nickLogin;
   // private TextView elTexto;
    private Button elBotonEnviar;
    private ArrayAdapter list_adapter;

    public final static LogicaFake laLogicaFake = new LogicaFake("http://10.236.21.86:8080" );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

 	    //this.elTexto = (TextView) findViewById(R.id.elTexto);
        this.elBotonEnviar = (Button) findViewById(R.id.botonEnviar);

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

        elPeticionario.hacerPeticionREST("GET",  "http://192.168.43.111:8080/tweetsnick/u1&5", null,
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
