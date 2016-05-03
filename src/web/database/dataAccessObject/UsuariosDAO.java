package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import web.database.conection.gestorDeConexiones;
import web.database.valueObject.UsuarioVO;

//Hay dos versiones de este paquete, puede dar fallos.
//		...exceptions.jdbc4.MySQLIntegr...
//		...exceptions.MySQLIntegr...
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class UsuariosDAO {
	
	/** Inserta el ususario en la base de datos
	 * 
	 * @param nickname
	 * @param passwd
	 * @return true si se ha anadido el usuario y false si ya existe o hay error.
	 */
	public static boolean addUser( String nickname, String nombre, String passwd) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			if(conn==null){ System.out.printf("****CONN NULO"); }
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("insert into usuarios (nickname, nombre, pass) values ('%s', '%s', '%s')", nickname, nombre, passwd);
			stmt.execute(sql);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			throw e;
		} catch (SQLException e){
			throw e;
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
		return false;
	}
	
	/**Busca y devuelve un usuario por nickname
	 * 
	 * @param nickname
	 * @return UsuarioVO poblado si existe el ususario y null si no existe
	 */
	public static UsuarioVO findUser( String nickname ) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("select _id, nickname, nombre, pass, fecha from usuarios where nickname='%s'", nickname);
			ResultSet rs = stmt.executeQuery(sql);
			
			if( rs.next() ){
				return new UsuarioVO (rs.getInt("_id"), rs.getString("nickname"), rs.getString("nombre"), rs.getString("pass"), rs.getString("fecha"));
			}
			//El usuario no existe
			return null;
			
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			throw e;
		} catch (SQLException e){
			throw e;
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
	}
	
	/**Busca y devuelve un usuario por id
	 * 
	 * @param nickname
	 * @return UsuarioVO poblado si existe el ususario y null si no existe
	 */
	public static UsuarioVO findUser( int id ) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("select _id, nickname, nombre, pass, fecha from usuarios where _id='%s'", id);
			ResultSet rs = stmt.executeQuery(sql);
			
			if( rs.next() ){
				return new UsuarioVO (rs.getInt("_id"), rs.getString("nickname"), rs.getString("nombre"), rs.getString("pass"), rs.getString("fecha"));
			}
			System.out.println("El usuario no existe");
			return null;
			
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			throw e;
		} catch (SQLException e){
			throw e;
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
	}
	
	public static void updateUser( int userID, String newNickname, String newNombre, String newPasswd) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("UPDATE `usuarios` SET `nickname`='%s', `nombre`='%s',`pass`='%s' WHERE _id='%s'", 
					newNickname, newNombre, newPasswd, userID);
			stmt.execute(sql);
		} catch (ClassNotFoundException e){
			throw e;
		} catch (SQLException e){
			throw e;
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}
	}
	
	public static boolean deleteUser( String Nickname ){
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("DELETE FROM `usuarios` WHERE `nickname`='%s'", Nickname);
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
