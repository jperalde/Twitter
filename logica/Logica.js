// Logica.js
//
const sqlite3 = require( "sqlite3" )
// .....................................................................
// .....................................................................
module.exports = class Logica {
// .................................................................
// nombreBD: Texto
// –>
// constructor () –>
// .................................................................
	constructor( nombreBD, cb ) {
		this.laConexion = new sqlite3.Database(
		nombreBD,
		( err ) => {
			if( ! err ) {
				this.laConexion.run( "PRAGMA foreign_keys = ON" )
			}
			cb( err)
		})
	} // ()

// .................................................................
// datos:{email:Texto, nick:Texto: password:Texto}
// –>
// altaUsuario() –> / .................................................................
	altaUsuario( datos ) {
		var textoSQL ="insert into Usuario values( $nick,$email, $password );"
		var valoresParaSQL = {$nick:datos.nick, $email:datos.email,  $password:datos.password }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.run( textoSQL, valoresParaSQL, function( err ) {
				( err ? rechazar(err) : resolver(0) )
			})
		})
	} // ()

// .................................................................
// datos:{texto:Texto, nick:Texto: instante:Texto}
// –>
// enviarTweet() –> / .................................................................
	enviarTweet( datos ) {
		var textoSQL ="insert into Tweet values( $nick,$texto, $instante );"
		var date=new Date();
		var valoresParaSQL = {$nick:datos.nick, $texto:datos.texto,  $instante:date}
		return new Promise( (resolver, rechazar) => {
			this.laConexion.run( textoSQL, valoresParaSQL, function( err ) {
				( err ? rechazar(err) : resolver(0) )
			})
		})
	} // ()

// .................................................................
// datos:nickSeguidor:Texto: nickSeguido:Texto}
// –>
// seguirA() –> / .................................................................
	seguirA( datos ) {
		var textoSQL ="insert into Seguidores values( $nickSeguidor, $nickSeguido );"
		var valoresParaSQL = {$nickSeguidor:datos.nickSeguidor, $nickSeguido:datos.nickSeguido }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.run( textoSQL, valoresParaSQL, 
				(err)=> ( err ? rechazar(err) : resolver(0) ))
		})
	} // ()	
// .................................................................
// nick:Texto
// –>
// 	buscarUsuarioPorNick() <–
// <–
// datos:{email:Texto, nick:Texto: password:Texto}
// .................................................................
	buscarUsuarioPorNick( nick ) {
		var textoSQL = "select * from Uusuario where nick=$nick;"
		var valoresParaSQL = { $nick: nick }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL,	(err,res)=> ( err ? rechazar(err) : resolver(res) ))
		})
	} // ()
// .................................................................
// 
// –>
// 	buscarUsuarioPorNick() <–
// <–
// datos:{nick:Texto: password:Texto}
// .................................................................
	comprobarPassword( datos ) {
		var textoSQL = "select password from Uusuario where (nick=$nick) and password=$password;"
		var valoresParaSQL = { $nick: datos.nick, $password:datos.password }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL,	(err,res)=> ( err ? rechazar(err) : resolver(res) ))
		})
	} // ()
// .................................................................
// email:Texto
// –>
// 	buscarUsuarioPorEmail() <–
// <–
// .................................................................
	buscarUsuarioPorEmail( email ) {
		var textoSQL = "select * from Uusuario where email=$email;"
		var valoresParaSQL = { $email: email }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL,
				(err,res)=> ( err ? rechazar(err) : resolver(res) ))
		})
	} // ()
// .................................................................
// nick:Texto
// –>
// 	buscarUsuarioPorEmail() <–
// <–
// .................................................................
	buscarAQuienSigue( nick ) {
		var textoSQL = "select nickSeguido from Seguidores where nickSeguidor=$nick;"
		var valoresParaSQL = { $nick: nickSeguidor }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL, 
				(err,res)=> ( err ? rechazar(err) : resolver(res) ))
		})
	} // ()
// .................................................................
// –>
// 	buscarTweetsPorNick() <–
// <–
// datos:{nick:Texto, cuantos:Texto}
// .................................................................
	buscarTweetsPorNick( datos) {
		var textoSQL = "select * from Tweet where nick=$nick ORDER BY instante DESC limit $cuantos;"
		var valoresParaSQL = { $nick: datos.nick, $cuantos:datos.cuantos }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL,
				(err,res)=> ( err ? rechazar(err) : resolver(res) )

			)
		})
	} // ()
// .................................................................
// –>
// 	buscarTweetsDeQuienSigo() <–
// <–
// datos:{nick:Texto, cuantos:Texto}
// .................................................................
	buscarTweetsDeQuienSigo( datos) {
		var textoSQL = "select * from Tweet where nick in (select nickSeguido from Seguidores where nickSeguidor=$nick) ORDER BY instante DESC limit $cuantos;"
		var valoresParaSQL = { $nick: datos.nick, $cuantos:datos.cuantos }
		return new Promise( (resolver, rechazar) => {
			this.laConexion.all( textoSQL, valoresParaSQL, 
				(err,res)=> ( err ? rechazar(err) : resolver(res) )

			)
		})
	} // ()
// .................................................................
// 
// –>
// bajaUsuario( nick ) –>
// .................................................................
	bajaUsuario( nick ) {
		return new Promise( (resolver, rechazar) => {
			this.laConexion.run(
			"delete from Usuario where nick= '" + nick + "';", 
			(err)=> ( err ? rechazar(err) : resolver(0) )
			)
		})
	} // ()

// .................................................................
// 
// –>
// cambiarPassword( datos ) –>
// datos:{nick:Texto, cuantos:Texto}
// 
// .................................................................
	cambiarPassword( datos ) {
		return new Promise( (resolver, rechazar) => {
			this.laConexion.run(
			"update Usuario set password= '" + datos.nuevoPassword +"' where(nick= '"+datos.nick+"') and (password= '"+datos.oldPassword+ "');",
			(err)=> ( err ? rechazar(err) : resolver(0) )
			)
		})
	} // ()

// .................................................................
// cerrar() –>
// .................................................................
	cerrar() {
		return new Promise( (resolver, rechazar) => {
			this.laConexion.close( (err)=>{
			( err ? rechazar(err) : resolver() )
			})
		})
	} // ()
} // class
// .....................................................................
// .....................................................................

