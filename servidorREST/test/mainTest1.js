// ........................................................
// mainTest1.js
// ........................................................
var request = require ('request')
var assert = require ('assert')
// ........................................................
// ........................................................
const IP_PUERTO="http://localhost:8080"






// ........................................................
// main ()
// ........................................................
describe( "Test 1 : Recuerda arrancar el servidor", function() {
//........................................................................................
//   GET/prueba
//.......................................................................................
	it( "probar que GET /prueba responde ¡Funciona!", function( hecho ) {
		request.get({ url : IP_PUERTO+"/prueba", headers : { 'User-Agent' : 'jordi' }},
			function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				assert.equal( carga, "¡Funciona!", "¿La carga no es ¡Funciona!?" )
				hecho()
			} // callback()
		) // .get
	}) // it

//........................................................................................
//   POST/alta
//.......................................................................................
	it( "probar POST /alta", function( hecho ) {
		var datosUsuario = { nick : "prueba1", email : "prueba1@tweet.com", password : "prueba1"}
			request.post({ url : IP_PUERTO+"/alta",headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
			body : JSON.stringify( datosUsuario )
			},
			function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				assert.equal( carga, "Alta confirmada", "¿La carga no es OK" )
				hecho()
			} // callback
			) // .post
	}) // it
//........................................................................................
//   POST/tweet
//.......................................................................................
	it( "probar POST /tweet", function( hecho ) {
		var datosTweet = { nick : "prueba1", texto : "prueba del POST/tweet", instante : "16062019"}
			request.post({ url : IP_PUERTO+"/tweet",headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
			body : JSON.stringify( datosTweet )
			},
			function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				assert.equal( carga, "Enviado", "¿La carga no es OK" )
				hecho()
			} // callback
			) // .post
	}) // it
//........................................................................................
//   POST/seguir
//.......................................................................................

	it( "probar POST /seguir", function( hecho ) {
		var datosUsuarios = { nickSeguidor : "u1", nickSeguido : "prueba1"}
			request.post({ url : IP_PUERTO+"/seguir",headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
			body : JSON.stringify( datosUsuarios )
			},
			function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				assert.equal( carga, "Siguiendo", "¿La carga no es OK" )
				hecho()
			} // callback
			) // .post
	}) // it
//........................................................................................
//   GET/searchnick
//.......................................................................................


	it("probar GET/searchnick/:nick",function (hecho){
		request.get({ url : IP_PUERTO+"/searchnick/prueba1",headers : { 'User-Agent' : 'jordi' }},function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				var PERSONA = JSON.parse( carga )
				
				hecho()
			} // callback
		) // .get


	})
//........................................................................................
//   GET/searchemail
//.......................................................................................


	it("probar GET/searchemail/:email",function (hecho){
		request.get({ url : IP_PUERTO+"/searchemail/prueba1@tweet.com",headers : { 'User-Agent' : 'jordi' }},function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
				var PERSONA = JSON.parse( carga )
				
				hecho()
			} // callback
		) // .get


	})
	it("probar DELETE/baja/:nick",function (hecho){
		request.delete({ url : IP_PUERTO+"/baja/prueba1@tweet.com",headers : { 'User-Agent' : 'jordi' }},function( err, respuesta, carga ) {
				assert.equal( err, null, "¿ha habido un error?" )
				assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" )
			
				
				hecho()
			} // callback
		) // .get


	})

})


