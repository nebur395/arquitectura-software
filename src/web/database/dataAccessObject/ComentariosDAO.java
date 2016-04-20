package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;

import web.database.valueObject.ComentarioVO;

//Hay dos versiones de este paquete, puede dar fallos.
//		...exceptions.jdbc4.MySQLIntegr...
//		...exceptions.MySQLIntegr...
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class ComentariosDAO {
	
	
	public static boolean addComentario( int userID, int vJuegoID, String comentario){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			
			String sql = String.format("INSERT INTO `comentarios`( `usuario`, `videojuego`, `comentario`) " +
					"VALUES ('%s', '%s', '%s')", userID, vJuegoID, comentario);
			stmt.execute(sql);
			
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			
			System.out.println("Comentario ya existente");
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		return false;
	}
		
	public static ArrayList<ComentarioVO> listComentario( int vJID ){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("SELECT _id, usuario, videojuego, comentario, tiempo FROM comentarios " +
					"WHERE videojuego='%s'", vJID);
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<ComentarioVO> list = new ArrayList<ComentarioVO>();
			
			while( rs.next() ){
		
				list.add(new ComentarioVO(rs.getInt("_id"), rs.getInt("usuario"), rs.getInt("videojuego"), 
						rs.getString("comentario"),  rs.getString("tiempo")));
			}			
			return list;
			
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
		
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		
		return null;
	}
	
	//TODO: Testear listComentarioUser
	public static ArrayList<ComentarioVO> listComentarioUser( int idUser ){
				
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("SELECT _id, usuario, videojuego, comentario, tiempo FROM comentarios " +
					"WHERE usuario='%s'", idUser);
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<ComentarioVO> list = new ArrayList<ComentarioVO>();
			
			while( rs.next() ){
		
				list.add(new ComentarioVO(rs.getInt("_id"), rs.getInt("usuario"), rs.getInt("videojuego"), 
						rs.getString("comentario"),  rs.getString("tiempo")));
			}			
			return list;
			
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
		
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		
		return null;		
	}
	
	public static boolean deleteComentario( int commentID ){
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("DELETE FROM `comentarios` WHERE `_id`='%s'",commentID);
			stmt.execute(sql);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			
			System.out.println("El nickname no existe");
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		return false;
	}

}
