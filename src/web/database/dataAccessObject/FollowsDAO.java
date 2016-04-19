package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;

import web.database.valueObject.UsuarioVO;

//Hay dos versiones de este paquete, puede dar fallos.
//		...exceptions.jdbc4.MySQLIntegr...
//		...exceptions.MySQLIntegr...
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class FollowsDAO {
	
	//TODO: Implementar Unfollow
	
	public static boolean follow(int follower, int followed){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("INSERT INTO `seguimientos`(`seguidor`, `seguido`) VALUES (%s,%s)", follower, followed);
			stmt.execute(sql);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			
			System.out.println("El seguimiento ya existe");
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

	public static boolean unFollow(int follower, int followed){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("DELETE FROM `seguimientos` WHERE `seguidor`='%s' AND `seguido`='%s'", follower, followed);
			stmt.execute(sql);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			
			System.out.println("El seguimiento no existe");
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
	
	public static ArrayList<UsuarioVO> getFollows( int userID ){
				
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("select U._id, U.nickname, U.nombre, U.pass from usuarios U, seguimientos S where S.seguidor='%s' AND U._id=S.seguido", userID);
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<UsuarioVO> list = new ArrayList<UsuarioVO>();
			
			while( rs.next() ){
				list.add(new UsuarioVO (rs.getInt("_id"), rs.getString("nickname"), rs.getString("nombre"), rs.getString("pass"), "--add campo fecha--"));
				
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
	
	//TODO: Implementar
	public static boolean isFollower( int follower, int followed ){
		return true;
	}


}
