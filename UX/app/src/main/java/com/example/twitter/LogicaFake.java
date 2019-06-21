package com.example.twitter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.Date;

public class LogicaFake {

    public interface RespuestaLogica {
        void callback (JSONArray respuesta);
    }



    private String urlServidor;

    public LogicaFake (String urlServidor) {
        Log.d( "jorge", "constructor LogicaFake");
        this.urlServidor = urlServidor;
    } // ()


    // ----------------------------------------------------------------------------
    // nick:Texto,  -> altaUsuario () -> [Tweets?]
    // ----------------------------------------------------------------------------
    public void tweetsPorNick( String nick, final RespuestaLogica responder ){
        Log.d( "jorge", "empieza LogicaFake.altaUsuario()");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        String cuerpo="";
        elPeticionario.hacerPeticionREST("POST",  this.urlServidor + "/alta", cuerpo,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {

                        Log.d( "jorge", "LogicaFake.altaUsuario(): respuesta rest codigo=" + codigo);

                        JSONArray tws;



                        try {
                            tws = (JSONArray) new JSONTokener(cuerpo).nextValue();

                            responder.callback(tws);

                        } catch(JSONException ex){

                        }



                    }

                }
        );

    } // ()

    public String[] tweetsSeguidos(String nickLogin) {
        Log.d("jorge", "empieza LogicaFake.altaUsuario()");
        final String[] aux = new String[1];
        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/tweetsseguidos/"+nickLogin+"&20", null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        JSONArray tws;

                        int year, month, day, hour, min, seg;
                        String nick=null, text=null, date=null;
                        Long inst;

                        try{
                            tws=(JSONArray)new JSONTokener(cuerpo).nextValue();
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

                                aux[0] ="@"+nick+": "+text+"     "+date;
                            }//for

                        } catch(JSONException ex){

                        }
                    }
                }
        );
        return aux;
    }





    public String[] enviarTweet(String cuerpo) {
        Log.d("jorge", "empieza LogicaFake.altaUsuario()");
        final String[] codigoR = new String[1];
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "/tweet", cuerpo,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo == 404) {
                            codigoR[0]="404";
                        }
                        else{
                            codigoR[0]="200";
                        }

                    }
                }
        );
        return codigoR;
    }//enviarTweet
    // ----------------------------------------------------------------------------
    // nick:Texto,  pasword:Text -> comprobarPassword () -> String[]
    // ----------------------------------------------------------------------------

    public String[] comprobarPassword(final String nick, String password ){

        final String[] codigoR = new String[1];
        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/comprobar/"+nick+"&"+password, null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {

                        if (codigo!=404){

                            codigoR[0] ="200";
                        }
                        codigoR[0]="404";

                    }
                }
        );
        return codigoR;
    }



    // ----------------------------------------------------------------------------
    // nick:Texto, email:Texto, pasword:Text -> altaUsuario () -> ?
    // ----------------------------------------------------------------------------
    public void altaUsuario( String nick, String email, String password ){
        Log.d( "jorge", "empieza LogicaFake.altaUsuario()");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        String cuerpo="{\"nick\": \"" + nick + "\", \"email\": \""+email+"\", \"password\": \""+password+"\"}";
        elPeticionario.hacerPeticionREST("POST",  this.urlServidor + "/alta", cuerpo,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {

                        Log.d( "jorge", "LogicaFake.altaUsuario(): respuesta rest codigo=" + codigo);

                        if (codigo == 404) {
                            Log.d( "jorge", "LogicaFake.altaUsuario(): respuesta rest 404");


                        }
                        //Toast.makeText(otra.this,"Creado usuario.Todo OK" ,Toast.LENGTH_LONG );
                        }
                }
        );

    } // ()
} // class
