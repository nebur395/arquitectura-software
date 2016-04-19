package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;
import web.database.valueObject.PuntuacionVO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class PuntuacionesDAO {
	
	public static boolean addPuntuacion( int userID, int vJuegoID, int puntuacion){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			
			String sql = String.format("INSERT INTO `notas`( `usuario`, `videojuego`, `nota`) " +
					"VALUES ('%s', '%s', '%s')", userID, vJuegoID, puntuacion);
			
			try{
				stmt.execute(sql);
			}catch (MySQLIntegrityConstraintViolationException e) {
				
				sql = String.format("UPDATE `notas` SET `nota`='%s' WHERE `usuario`='%s' and `videojuego`='%s' ", puntuacion, userID, vJuegoID);
				stmt.execute(sql);
				
		
				//Fin de la parte interesante---------------------------------------------
			}
			
			
			return true;
		}  catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		return false;
	}
		
	public static ArrayList<PuntuacionVO> listPuntuaciones( int id ){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("SELECT usuario, videojuego, nota, tiempo FROM notas " +
					"WHERE videojuego='%s'", id);
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<PuntuacionVO> list = new ArrayList<PuntuacionVO>();
			
			while( rs.next() ){
				
				list.add( new PuntuacionVO(rs.getInt("usuario"), rs.getInt("videojuego"), rs.getInt("nota"), rs.getString("tiempo")) );
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
	
	//TODO: Testear listPuntuacionesUser
	public static ArrayList<PuntuacionVO> listPuntuacionesUser( int idUser ){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("SELECT usuario, videojuego, nota, tiempo FROM notas " +
					"WHERE usuario='%s'", idUser);
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<PuntuacionVO> list = new ArrayList<PuntuacionVO>();
			
			while( rs.next() ){
				
				list.add( new PuntuacionVO(rs.getInt("usuario"), rs.getInt("videojuego"), rs.getInt("nota"), rs.getString("tiempo")) );
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
	
	public static boolean deletePuntuacion( int usuarioID, int vJID  ){
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("DELETE FROM `notas` WHERE  `usuario`='%s' and `videojuego`='%s'", usuarioID, vJID);
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
