package web.database;

import java.util.ArrayList;

import web.database.dataAccessObject.ComentariosDAO;
import web.database.dataAccessObject.FeedComentariosDAO;
import web.database.dataAccessObject.FeedPuntuacionesDAO;
import web.database.dataAccessObject.FollowsDAO;
import web.database.dataAccessObject.PuntuacionesDAO;
import web.database.dataAccessObject.UsuariosDAO;
import web.database.dataAccessObject.VJuegosDAO;
import web.database.valueObject.ComentarioVO;
import web.database.valueObject.PuntuacionVO;
import web.database.valueObject.UsuarioVO;
import web.database.valueObject.VJuegoVO;

public class Test {
	
	
	private static UsuarioVO pepe = null;
	private static UsuarioVO juan = null;
	private static UsuarioVO hector = null;
	private static UsuarioVO gandalf = null;
	
	
	
	private static VJuegoVO gow = null;
	private static VJuegoVO pok = null;
	
	
	public static void main(String[] args) {
		//Limpiamos la base de datos
		dbAdapter.ejecutarFicheroSQL("resources/limpiarBD.sql");
		
		testUsuarios();
		testVideojuegos();
		testComentarios();
		testPuntuaciones();
		testSeguimientos();
		testFeed();
	}
	
	private static void testUsuarios(){
		System.out.println("TEST DE USUARIOS");
		System.out.println("Añadimos usuarios:");
		
		UsuariosDAO.addUser("xpepe", "Pepe", "0000" );
		UsuariosDAO.addUser("juan", "Juan", "3333" );
		UsuariosDAO.addUser("hecthor", "Hector", "4444" );
		UsuariosDAO.addUser("magoblanco69", "Gandalf", "5555" );
		UsuariosDAO.addUser("hombreEfimero", "Tu Vas A Durar Poco", "9999" );
		UsuariosDAO.addUser("hombreEfimero", "fff", "fff" );//Colisión de nombre
		System.out.println("OK\n");
		
		System.out.println("Buscamos los ususarios y los mostramos");
		pepe = UsuariosDAO.findUser("xpepe");
		System.out.println(pepe);
		juan = UsuariosDAO.findUser("juan");
		System.out.println(juan);
		hector = UsuariosDAO.findUser("hecthor");
		System.out.println(hector);
		gandalf = UsuariosDAO.findUser("magoblanco69");
		System.out.println(gandalf);
		System.out.println(UsuariosDAO.findUser("hombreEfimero")); //Imprimimos a hombreEfimero
		
		System.out.println("\nGandalf serializado");
		System.out.println(gandalf.serialize());
		System.out.println("OK\n");
		

		System.out.println("Actualizamos el nick de Juan a Juanito");
		juan = UsuariosDAO.findUser(juan.get_id()); //Se hace para que el objeto actualice sus campos
		UsuariosDAO.updateUser(juan.get_id(), "juanito", juan.getNombre(), juan.getPasswd());
		System.out.println( UsuariosDAO.findUser( juan.get_id() ) );
		
		System.out.println("Actualizamos el pass de 3333 a nuevapass");
		juan = UsuariosDAO.findUser(juan.get_id()); //Se hace para que el objeto actualice sus campos
		UsuariosDAO.updateUser(juan.get_id(), juan.getNickname(), juan.getNombre(), "nuevapass");
		System.out.println( UsuariosDAO.findUser( juan.get_id() ) );
		System.out.println("OK\n");
		
		System.out.println("Borramos a hombrefimero");
		UsuariosDAO.deleteUser("hombreEfimero");
		System.out.println("OK\n");
		
		System.out.println(UsuariosDAO.findUser("hombreEfimero"));
	}
	
	private static void testVideojuegos(){

		System.out.println("TEST DE VIDEOJUEGOS");
		System.out.println("Añadimos videojuegos:");
		
		VJuegosDAO.addVJuego("God Of War", "Ese de Kratos", "Ni Idea Studios", "PizzaElHutt", "PS3", "Hostias como panes", 1995);
		VJuegosDAO.addVJuego("Pokemon", "Va de capturar bichos", "Nintendo", "Nintendo", "", "RPG", 1995);
		VJuegosDAO.addVJuego("Efimero", "No se", "Ni idea", "Apiuummmm", "Cheetos", "pa k quieres saber eso jajaja saludos", 0000);
		VJuegosDAO.addVJuego("Efimero", "ff", "ff", "ff", "ff", "ff", 0000); //Colisión de nombre
		System.out.println("OK\n");
		
		System.out.println("Buscamos los videojuegos y los mostramos");
		gow= VJuegosDAO.findVJuego(1);
		System.out.println(gow);
		pok= VJuegosDAO.findVJuego(2);
		System.out.println(pok);
		System.out.println(VJuegosDAO.findVJuego(3)); //Imprimimos la vuelo
		System.out.println("OK\n");
		
		
		System.out.println("Actualizamos la plataforma de Pokemon");
		VJuegosDAO.updateVJuego(pok.get_id(), "Pokemon", "Va de campturar bichos", "Nintendo", "Nintendo", "GameBoy", "RPG", 1995);
		pok= VJuegosDAO.findVJuego(2);
		System.out.println(pok);
		System.out.println("OK\n");
		
		System.out.println("Borramos a hombrefimero");
		VJuegosDAO.deleteVJuego(3);
		System.out.println("OK\n");
	}
	
	private static void testComentarios(){
		
		System.out.println("TEST DE COMENTARIOS");
		System.out.println("Añadimos los comentarios:");
		
		System.out.println("Pepe comenta God of War: " + "Como se sale del menu?");
		ComentariosDAO.addComentario(pepe.get_id(), gow.get_id(), "Como se sale del menu?");
		
		System.out.println("Juan comenta God of War: " + "Le doy un nueve");
		ComentariosDAO.addComentario(juan.get_id(), gow.get_id(), "Le doy un nueve");
		
		System.out.println("Hector comenta God of War: " + "Ehta to guapo illo cavesa un siete pa ti");
		ComentariosDAO.addComentario(hector.get_id(), gow.get_id(), "Ehta to guapo illo cavesa un siete pa ti");
		
		System.out.println("Gandalf comenta God of War: " + "Un diez por la precisión histórica");
		ComentariosDAO.addComentario(gandalf.get_id(), gow.get_id(), "Un diez por la precisión histórica");
		
		System.out.println("Gandalf comenta God of War: " + "Comentario efimero");
		ComentariosDAO.addComentario(gandalf.get_id(), gow.get_id(), "Comentario efimero");
		
		System.out.println("Gandalf comenta Pokemon: " + "Comentario efimero");
		ComentariosDAO.addComentario(gandalf.get_id(), pok.get_id(), "Nadie creería que esos bichos existen");
		System.out.println("OK\n");
		
		System.out.println("Listamos los comentarios de GOW");
		ArrayList<ComentarioVO> listaDeComentarios = ComentariosDAO.listComentario(gow.get_id());
		for(ComentarioVO com: listaDeComentarios){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Listamos los comentarios de gandalf");
		listaDeComentarios = ComentariosDAO.listComentarioUser(gandalf.get_id());
		for(ComentarioVO com: listaDeComentarios){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Borramos el comentario efimero");
		ComentariosDAO.deleteComentario(5);
		System.out.println("OK\n");
		
		System.out.println("Listamos los comentarios de GOW");
		listaDeComentarios = ComentariosDAO.listComentario(gow.get_id());
		for(ComentarioVO com: listaDeComentarios){
			System.out.println(com);
		}
		System.out.println("OK\n");
	}
	
	private static void testPuntuaciones(){

		System.out.println("TEST DE PUNTUACIONES");
		System.out.println("Añadimos las puntuaciones:");
		
		System.out.println("Pepe puntua God of War con un 2");
		PuntuacionesDAO.addPuntuacion(pepe.get_id(), gow.get_id(), 2);
		System.out.println("Juan puntua God of War con un 9");
		PuntuacionesDAO.addPuntuacion(juan.get_id(), gow.get_id(), 9);
		System.out.println("Hector puntua God of War con un 7");
		PuntuacionesDAO.addPuntuacion(hector.get_id(), gow.get_id(), 7);
		System.out.println("Gandalf puntua God of War con un 10");
		PuntuacionesDAO.addPuntuacion(gandalf.get_id(), gow.get_id(), 10);
		System.out.println("Gandalf puntua Pokemon con un 10");
		PuntuacionesDAO.addPuntuacion(gandalf.get_id(), pok.get_id(), 4);
		System.out.println("OK\n");
	
		System.out.println("Listamos las puntuaciones de GOW");
		ArrayList<PuntuacionVO> listaDePuntuaciones = PuntuacionesDAO.listPuntuaciones(gow.get_id());
		for(PuntuacionVO com: listaDePuntuaciones){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Listamos las puntuaciones de GOW");
		listaDePuntuaciones = PuntuacionesDAO.listPuntuaciones(gow.get_id());
		for(PuntuacionVO com: listaDePuntuaciones){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Gandalf rectifica su puntuacion God of War con un 11");
		PuntuacionesDAO.addPuntuacion(gandalf.get_id(), gow.get_id(), 11);
		System.out.println("OK\n");
		
		System.out.println("Listamos las puntuaciones de GOW");
		listaDePuntuaciones = PuntuacionesDAO.listPuntuaciones(gow.get_id());
		for(PuntuacionVO com: listaDePuntuaciones){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Listamos las puntuaciones de gandalf");
		listaDePuntuaciones = PuntuacionesDAO.listPuntuacionesUser(gandalf.get_id());
		for(PuntuacionVO com: listaDePuntuaciones){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Gandalf elimina su puntuacion God of War");
		PuntuacionesDAO.deletePuntuacion(gandalf.get_id(), gow.get_id());
		System.out.println("OK\n");
		
		System.out.println("Listamos las puntuaciones de GOW");
		listaDePuntuaciones = PuntuacionesDAO.listPuntuaciones(gow.get_id());
		for(PuntuacionVO com: listaDePuntuaciones){
			System.out.println(com);
		}
		System.out.println("OK\n");
	}
	
	private static void testSeguimientos(){
		
		System.out.println("TEST DE SEGUIMIENTOS");
		System.out.println("Añadimos los seguimientos:");
		
		System.out.println("Pepe sigue a " + "Juan, Hector y Gandalf");
		FollowsDAO.follow(pepe.get_id(), juan.get_id());
		FollowsDAO.follow(pepe.get_id(), hector.get_id());
		FollowsDAO.follow(pepe.get_id(), gandalf.get_id());
		System.out.println("Gandalf sigue a " + "Pepe y Juan");
		FollowsDAO.follow(gandalf.get_id(), pepe.get_id());
		FollowsDAO.follow(gandalf.get_id(), juan.get_id()); //TODO: Evitar autofollows
		System.out.println("OK\n");

		System.out.println("Listamos los usuarios seguidos por pepe");
		ArrayList<UsuarioVO> followsDePepe = FollowsDAO.getFollows(pepe.get_id());
		for(UsuarioVO user : followsDePepe){
			System.out.println(user);
		}
		System.out.println("OK\n");
		
		System.out.println("Listamos los usuarios seguidos por gandalf");
		ArrayList<UsuarioVO> followsDeGandalf = FollowsDAO.getFollows(gandalf.get_id());
		for(UsuarioVO user : followsDeGandalf){
			System.out.println(user);
		}
		System.out.println("OK\n");
		
		System.out.println("Comprobamos los seguimientos");
		System.out.printf("%s sigue a %s?: %s\n",gandalf.getNombre(), pepe.getNombre(), 
				FollowsDAO.isFollower( gandalf.get_id(), pepe.get_id() ));
		System.out.printf("%s sigue a %s?: %s\n",gandalf.getNombre(), hector.getNombre(), 
				FollowsDAO.isFollower( gandalf.get_id(), hector.get_id() ));
		System.out.printf("%s sigue a %s?: %s\n",gandalf.getNombre(), juan.getNombre(), 
				FollowsDAO.isFollower( gandalf.get_id(), juan.get_id() ));
		System.out.println("OK\n");
		
		System.out.println("Borramos el seguimiento de Gandalf hacia Juan");
		FollowsDAO.unFollow(gandalf.get_id(), juan.get_id());
		System.out.println("OK\n");
		
		System.out.println("Listamos los usuarios seguidos por gandalf");
		followsDeGandalf = FollowsDAO.getFollows(gandalf.get_id());
		for(UsuarioVO user : followsDeGandalf){
			System.out.println(user);
		}
		System.out.println("OK\n");
	}
	
	private static void testFeed(){
		System.out.println("TEST DE FEED");
		
		
		System.out.println("Imprimimos el feed de comentarios de Pepe:");
		ArrayList<ComentarioVO> feedComentsDePepe = FeedComentariosDAO.findFeed(pepe.get_id());
		for( ComentarioVO com : feedComentsDePepe ){
			System.out.println(com);
		}
		System.out.println("OK\n");
		
		System.out.println("Imprimimos el feed de puntuaciones de Pepe:");
		ArrayList<PuntuacionVO> feedPuntosDePepe = FeedPuntuacionesDAO.findFeed(pepe.get_id());
		for( PuntuacionVO punt : feedPuntosDePepe ){
			System.out.println(punt);
		}
		System.out.println("OK\n");
		
		System.out.println("Imprimimos el feed de comentarios de Gandalf");
		ArrayList<ComentarioVO> feedComentsDeGandalf = FeedComentariosDAO.findFeed(gandalf.get_id());
		for( ComentarioVO com : feedComentsDeGandalf ){
			System.out.println(com);
		}
		System.out.println("OK\n");
		System.out.println("Imprimimos el feed de puntuaciones de Gandalf");
		ArrayList<PuntuacionVO> feedPuntosDeGandalf = FeedPuntuacionesDAO.findFeed(gandalf.get_id());
		for( PuntuacionVO punt : feedPuntosDeGandalf ){
			System.out.println(punt);
		}
		System.out.println("OK\n");
	}
}
