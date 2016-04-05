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

public class sandbox {
	
	public static void main(String[] args) {
		//Limpiamos la base de datos
		dbAdapter.ejecutarFicheroSQL("resources/limpiarBD.sql");
		
		System.out.println("Listado de usuarios añadidos:");
		UsuariosDAO.addUser("xpepe", "Pepe", "0000" );
		UsuariosDAO.addUser("juan", "Juan", "3333" );
		UsuariosDAO.addUser("hecthor", "Hector", "4444" );
		UsuariosDAO.addUser("magoblanco69", "Gandalf", "5555" );
		
		UsuarioVO pepe = UsuariosDAO.findUser("xpepe");
		System.out.println(pepe);
		UsuarioVO juan = UsuariosDAO.findUser("juan");
		System.out.println(juan);
		UsuarioVO hector = UsuariosDAO.findUser("hecthor");
		System.out.println(hector);
		UsuarioVO gandalf = UsuariosDAO.findUser("magoblanco69");
		System.out.println(gandalf);
		System.out.println("Gandalf serializado");
		System.out.println(gandalf.serialize());
		
		System.out.println();
		
		//Updates de users
		System.out.println("Actualiza el nick de Juan a Juanito");
		juan = UsuariosDAO.findUser(juan.get_id());
		UsuariosDAO.updateUser(juan.get_id(), "juanito", juan.getNombre(), juan.getPasswd());
		System.out.println( UsuariosDAO.findUser( juan.get_id() ) );
		
		System.out.println("\nActualiza el pass de 3333 a nuevapass");
		juan = UsuariosDAO.findUser(juan.get_id()); //Se hace para que el objeto actualice sus campos
		UsuariosDAO.updateUser(juan.get_id(), juan.getNickname(), juan.getNombre(), "nuevapass");
		System.out.println( UsuariosDAO.findUser( juan.get_id() ) );
		
		System.out.println();
		
		//Videjuegos
		VJuegosDAO.addVJuego("God Of War", "Ese de Kratos", "Ni Idea Studios", "PizzaElHutt", "PS3", "Hostias como panes", 1995);
		VJuegosDAO.addVJuego("Pokemon", "Va de campturar bichos", "Nintendo", "Nintendo", "", "RPG", 1995);
		
		
		System.out.println("Find de God of War");
		VJuegoVO gow= VJuegosDAO.findVJuego(1);
		System.out.println(gow);
		
		System.out.println("Find de Pokémon");
		VJuegoVO pok= VJuegosDAO.findVJuego(2);
		
		System.out.println(pok);
		
		
		System.out.println("\nActualiza la plataforma de Pokemon");
		VJuegosDAO.updateVJuego(pok.get_id(), "Pokemon", "Va de campturar bichos", "Nintendo", "Nintendo", "GameBoy", "RPG", 1995);
		pok= VJuegosDAO.findVJuego(2);
		System.out.println(pok);
		
		System.out.println();
		
		//Puntuaciones
		System.out.println("Pepe puntua God of War con un 2");
		PuntuacionesDAO.addPuntuacion(pepe.get_id(), gow.get_id(), 2);
		
		System.out.println("Juan puntua God of War con un 9");
		PuntuacionesDAO.addPuntuacion(juan.get_id(), gow.get_id(), 9);
		
		System.out.println("Hector puntua God of War con un 7");
		PuntuacionesDAO.addPuntuacion(hector.get_id(), gow.get_id(), 7);
		
		System.out.println("Gandalf puntua God of War con un 10");
		PuntuacionesDAO.addPuntuacion(gandalf.get_id(), gow.get_id(), 10);
		
		System.out.println("Gandalf rectifica su puntuacion God of War con un 11");
		PuntuacionesDAO.addPuntuacion(gandalf.get_id(), gow.get_id(), 11);
		
		System.out.println("Pepe comenta God of War");
		ComentariosDAO.addComentario(pepe.get_id(), gow.get_id(), "Como se sale del menu?");
		
		System.out.println("Juan comenta God of War");
		ComentariosDAO.addComentario(juan.get_id(), gow.get_id(), "Le doy un nueve");
		
		System.out.println("Hector comenta God of War");
		ComentariosDAO.addComentario(hector.get_id(), gow.get_id(), "Ehta to guapo illo cavesa un siete pa ti");
		
		System.out.println("Gandalf comenta God of War");
		ComentariosDAO.addComentario(gandalf.get_id(), gow.get_id(), "Un diez por la precisión histórica");
		

		
		
		FollowsDAO.follow(pepe.get_id(), juan.get_id());
		FollowsDAO.follow(pepe.get_id(), hector.get_id());
		FollowsDAO.follow(pepe.get_id(), gandalf.get_id());
		FollowsDAO.follow(gandalf.get_id(), pepe.get_id());
		FollowsDAO.follow(gandalf.get_id(), juan.get_id());
		
		
		System.out.println("\nListado de usuarios seguidos por pepe");
		ArrayList<UsuarioVO> followsDePepe = FollowsDAO.getFollows(pepe.get_id());
		for(UsuarioVO user : followsDePepe){
			System.out.println(user);
		}	
		System.out.println("Feed de Pepe: Comentarios");
		ArrayList<ComentarioVO> feedComentsDePepe = FeedComentariosDAO.findFeed(pepe.get_id());
		for( ComentarioVO com : feedComentsDePepe ){
			System.out.println(com);
		}
		System.out.println("Feed de Pepe: Puntuaciones");
		ArrayList<PuntuacionVO> feedPuntosDePepe = FeedPuntuacionesDAO.findFeed(pepe.get_id());
		for( PuntuacionVO punt : feedPuntosDePepe ){
			System.out.println(punt);
		}
		
		
		
		System.out.println("\nListado de usuarios seguidos por gandalf");
		ArrayList<UsuarioVO> followsDeGandalf = FollowsDAO.getFollows(gandalf.get_id());
		for(UsuarioVO user : followsDeGandalf){
			System.out.println(user);
		}
		System.out.println("Feed de Gandalf: Comentarios");
		ArrayList<ComentarioVO> feedComentsDeGandalf = FeedComentariosDAO.findFeed(gandalf.get_id());
		for( ComentarioVO com : feedComentsDeGandalf ){
			System.out.println(com);
		}
		System.out.println("Feed de Gandalf: Puntuaciones");
		ArrayList<PuntuacionVO> feedPuntosDeGandalf = FeedPuntuacionesDAO.findFeed(gandalf.get_id());
		for( PuntuacionVO punt : feedPuntosDeGandalf ){
			System.out.println(punt);
		}
		
		
	}
}
