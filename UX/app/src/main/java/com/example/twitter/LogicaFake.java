package com.example.twitter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.Date;

public class LogicaFake {

    public interface RespuestaLogica {
        void callback (String cuerpo);
        void fallo(int codigo);
    }



    private String urlServidor;

    public LogicaFake (String urlServidor) {
        Log.d( "jorge", "constructor LogicaFake");
        this.urlServidor = urlServidor;
    } // ()


    // ----------------------------------------------------------------------------
    // nick:Texto,  -> tweetsPorNick () -> [Tweets?]
    // ----------------------------------------------------------------------------
    public void tweetsPorNick(String nickLogin, final RespuestaLogica respuestaLogica) {
        Log.d("jorge", "empieza LogicaFake.tweetsPorNick()");

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/tweetsnick/" + nickLogin+"&100", null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo==404){
                            respuestaLogica.fallo(codigo);
                        }
                        else {
                            respuestaLogica.callback(cuerpo);
                        }
                    }
                }
        );

    }
    // ----------------------------------------------------------------------------
    // nick:Texto  -> baja()
    // ----------------------------------------------------------------------------

    public void baja(String nick,  final RespuestaLogica respuestaLogica) {
        Log.d("jorge", "empieza LogicaFake.tweetsPorNick()");

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("DELETE",  this.urlServidor+"/baja/"+nick , null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo==404){
                            respuestaLogica.fallo(codigo);
                        }
                        else {
                            respuestaLogica.callback(cuerpo);
                        }
                    }
                }
        );

    }

    // ----------------------------------------------------------------------------
    // nick:Texto, email:Texto, pasword:Text -> altaUsuario () -> ?
    // ----------------------------------------------------------------------------

    public void buscarNick( String nick,  final RespuestaLogica respuestaLogica){
        Log.d("jorge", "empieza LogicaFake.tweetsPorNick()");

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/searchnick/" + nick, null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo==404){
                            respuestaLogica.fallo(codigo);
                        }
                        else {
                            respuestaLogica.callback(cuerpo);
                        }
                    }
                }
        );

    }

    // ----------------------------------------------------------------------------
    // nick:Texto, email:Texto, pasword:Text -> altaUsuario () -> ?
    // ----------------------------------------------------------------------------

    public void seguidos(String nick,  final RespuestaLogica respuestaLogica){
        Log.d("jorge", "empieza LogicaFake.tweetsPorNick()");

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/seguidos/" + nick, null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo==404){
                            respuestaLogica.fallo(codigo);
                        }
                        else {
                            respuestaLogica.callback(cuerpo);
                        }
                    }
                }
        );

    }





    // ----------------------------------------------------------------------------
    // nickLogin:Texto,  -> tweetsSeguidos () -> [Tweets?]
    // ----------------------------------------------------------------------------

    public void tweetsSeguidos(String nickLogin, final RespuestaLogica respuestaLogica) {
        Log.d("jorge", "empieza LogicaFake.tweetsSeguidos()");

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/tweetsseguidos/"+nickLogin+"&100", null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo==404){
                            respuestaLogica.fallo(codigo);
                        }
                        else {
                            respuestaLogica.callback(cuerpo);
                        }
                    }
                });

    }


    // ----------------------------------------------------------------------------
    // cuerpo:Texto,  -> enviarTweet () -> OK
    // ----------------------------------------------------------------------------


    public void enviarTweet(String cuerpo, final RespuestaLogica respuestaLogica) {
        Log.d("jorge", "empieza LogicaFake.altaUsuario()");
        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "/tweet", cuerpo,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        if (codigo == 404) {
                            respuestaLogica.fallo(codigo);
                        }
                        else{
                            respuestaLogica.callback(null);
                        }

                    }
                }
        );
    }//enviarTweet
    // ----------------------------------------------------------------------------
    // nick:Texto,  password:Text -> comprobarPassword () -> String[]
    // ----------------------------------------------------------------------------

    public void comprobarPassword(final String nick, String password , final RespuestaLogica respuestaLogica){

        PeticionarioREST elPeticionario = new PeticionarioREST();
        elPeticionario.hacerPeticionREST("GET",  this.urlServidor+"/comprobar/"+nick+"&"+password, null,
                new PeticionarioREST.RespuestaREST () {
                    @Override
                    public void callback(int codigo, String cuerpo) {

                        if (codigo!=404){

                           respuestaLogica.callback(null);
                        }
                        else {
                            respuestaLogica.fallo(codigo);

                        }
                    }
                }
        );
    }



    // ----------------------------------------------------------------------------
    // nick:Texto, email:Texto, pasword:Text -> altaUsuario () -> ?
    // ----------------------------------------------------------------------------
    public void altaUsuario( String nick, String email, String password , final RespuestaLogica respuestaLogica){
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

    // ----------------------------------------------------------------------------
    // nick:Texto, email:Texto, pasword:Text -> altaUsuario () -> ?
    // ----------------------------------------------------------------------------

    public void seguirUsuario( String nick, String nickSeguido, final RespuestaLogica respuestaLogica){
        Log.d( "jorge", "empieza LogicaFake.altaUsuario()");

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();

        String cuerpo="{\"nickSeguidor\": \"" + nick + "\", \"nickSeguido\": \""+nickSeguido+"\"}";
        elPeticionario.hacerPeticionREST("POST",  this.urlServidor + "/seguir", cuerpo,
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



   // public void bajaUsuario()
} // class
