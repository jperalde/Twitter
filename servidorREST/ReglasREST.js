// .....................................................................
// ReglasREST.js         
// .....................................................................
module.exports.cargar = function( servidorExpress,laLogica ) {
// .......................................................
// GET /prueba
// .......................................................
	servidorExpress.get('/prueba/', function( peticion, respuesta ){
		console.log( " * GET /prueba " )
		respuesta.send( "¡Funciona!" )
	}) // get /prueba

// .......................................................
// POST /alta
// .......................................................
	servidorExpress.post("/alta", async function( peticion, respuesta ){
		console.log( " * POST /alta " )
		var datos = JSON.parse( peticion.body )
		console.log( datos.nick )
		console.log( datos.email )
		console.log( datos.password)
// supuesto procesamiento
		try {
			await laLogica.altaUsuario(datos)
			respuesta.send("Alta confirmada")
			
		} catch {
			respuesta.status(404).send("ERROR dando de alta")
		}
// 404 = not found
	}) 
	
// .......................................................
// POST /tweet
// .......................................................	
	servidorExpress.post("/tweet", async function( peticion, respuesta ){
		console.log( " * POST /tweet " )
		var datos = JSON.parse( peticion.body )
		console.log( datos.nick )
		console.log( datos.texto )
// supuesto procesamiento
		try {
			await laLogica.enviarTweet(datos)
			respuesta.send("Enviado")
			
		} catch {
			respuesta.status(404).send("Error al enviar tweet")
		}
// 404 = not found
	}) 

// .......................................................
// POST /seguir
// .......................................................	
	servidorExpress.post("/seguir", async function( peticion, respuesta ){
		console.log( " * POST /seguir " )
		var datos = JSON.parse( peticion.body )
		console.log( datos.nickSeguidor )
		console.log( datos.nickSeguido )
// supuesto procesamiento
		try {
			await laLogica.seguirA(datos)
			respuesta.send("Siguiendo")
			
		} catch {
			respuesta.status(404).send("Error al seguir")
		}
// 404 = not found
	}) 

// .......................................................
// GET /searchnick/<nick>
// .......................................................
	servidorExpress.get("/searchnick/:nick", async function( peticion, respuesta ){
		console.log( " * GET /searchnick " )
		var nick = peticion.params.nick

		var res=await laLogica.buscarUsuarioPorNick(nick)
		if( res.length != 1 ) {
// 404: not found
			respuesta.status(404).send( "No existe Usuario" )
			return
		}
// todo ok
		respuesta.send( JSON.stringify( res[0] ) )
		
	}) // get /searchnick

// .......................................................
// GET /searchemail/<email>
// .......................................................
	servidorExpress.get("/searchemail/:email", async function( peticion, respuesta ){
		console.log( " * GET /searchemail " )
		var email = peticion.params.email

		var res=await laLogica.buscarUsuarioPorEmail(email)
		if( res.length != 1 ) {
// 404: not found
			respuesta.status(404).send( "No existe Usuario con ese Email" )
			return
		}
// todo ok
		respuesta.send( JSON.stringify( res[0] ) )
		
	}) // get /searchnick

// .......................................................
// GET /tweetsnick/<nick>&<cuantos>
// .......................................................
	servidorExpress.get("/tweetsnick/:nick&:cuantos", async function( peticion, respuesta ){
		console.log( " * GET /tweetsnick " )
		console.log( peticion.params.nick )
		console.log( peticion.params.cuantos )
		
		var datos ={
			nick:peticion.params.nick,
			cuantos:peticion.params.cuantos
		}
		try{
		var res=await laLogica.buscarTweetsPorNick(datos)
		respuesta.send( JSON.stringify( res ) )
		}catch {
// 404: not found
			respuesta.status(404).send( "ERROR buscando tweets por nick" )
			
		}
// todo ok
		
		
	}) //

// .......................................................
// GET /seguidos/<nick>
// .......................................................
	servidorExpress.get("/seguidos/:nick", async function( peticion, respuesta ){
		console.log( " * GET /seguidos " )
		console.log( peticion.params.nick )
		
		var nick=  peticion.params.nick
		try{
		var res=await laLogica.buscarAQuienSigue(nick)
		respuesta.send( JSON.stringify( res ) )
		}catch {
// 404: not found
			respuesta.status(404).send( "ERROR buscando los seguidos" )
			
		}
// todo ok
		
		
	}) //
// .......................................................
// GET /tweetsseguidos/<nick>&<cuantos>
// .......................................................
	servidorExpress.get('/tweetsseguidos/:nick&:cuantos', async function( peticion, respuesta ){
		console.log( " * GET /tweetsseguidos " )
		console.log( peticion.params.nick )
		console.log( peticion.params.cuantos )
		
		var datos =
		{
			nick:peticion.params.nick,
			cuantos:peticion.params.cuantos
		}
		try{
		var res=await laLogica.buscarTweetsDeQuienSigo(datos)
		respuesta.send( JSON.stringify( res ) )
		}catch {
// 404: not found
			respuesta.status(404).send( "ERROR buscando tweets de seguidos" )
			
		}
// todo ok
		
		
	}) //

// .......................................................
// GET /comprobar/<nick>
// .......................................................
	servidorExpress.get("/comprobar/:nick&:password", async function( peticion, respuesta ){
		console.log( " * GET /comprobar " )
		console.log( peticion.params.nick )
		console.log( peticion.params.password )
		
		var datos =
		{
			nick:peticion.params.nick,
			password:peticion.params.password
		}


		var res=await laLogica.comprobarPassword(datos)
		if( res.length != 1 ) {
// 404: not found
			respuesta.status(404).send( "Contraseña incorrecta" )
			return
		}
// todo ok
		respuesta.send( "Contraseña correcta" )
		
	}) // get /searchnick

// .......................................................
// PUT /cambiarpassword
// .......................................................
	servidorExpress.put('/cambiarpassword', async function( peticion, respuesta ){
		console.log( " * PUT /cambiarpassword " )
	
		var datos =JSON.parse(peticion.body)

		console.log( peticion.params.nick )
		console.log( peticion.params.nuevoPassword )
		console.log( peticion.params.oldPassword )

		try{
		var res=await laLogica.cambiarPassword(datos)
		respuesta.send( "Contraseña cambiada" )
		}catch {
// 404: not found
			respuesta.status(404).send( "ERROR cambiando contraseña" )
			
		}
// todo ok
		
		
	}) //

// .......................................................
// DELETE /baja/<nick>
// .......................................................
	servidorExpress.delete("/baja/:nick", async function( peticion, respuesta ){
		console.log( " * DELETE /seguidos " )
		console.log( peticion.params.nick )
		
		var nick=  peticion.params.nick
		try{
		var res=await laLogica.bajaUsuario(nick)
		respuesta.send( JSON.stringify( res ) )
		}catch {
// 404: not found
			respuesta.status(404).send( "Baja Fallida" )
			
		}
// todo ok
		
		
	}) //
} // ()
// .....................................................................
// .....................................................................

