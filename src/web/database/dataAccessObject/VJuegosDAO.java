package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;

import web.database.valueObject.VJuegoVO;

//Hay dos versiones de este paquete, puede dar fallos.
//		...exceptions.jdbc4.MySQLIntegr...
//		...exceptions.MySQLIntegr...
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class VJuegosDAO {
	
	/** 
	 * 
	 * @param nickname
	 * @param passwd
	 * @return true si se ha anadido el usuario y false si ya existe o hay error.
	 */
	public static boolean addVJuego( String titulo, String descripcion, String desarrollador, 
			String distribuidor, String plataforma, String genero, int anyo, String imagen) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("INSERT INTO `videojuegos`(`titulo`, `descripcion`, `desarrollador`, `distribuidor`, `plataforma`, `genero`, `año`, `imagen`) " +
					"VALUES ('%s','%s','%s','%s','%s','%s','%s','%s')", titulo, descripcion, desarrollador, distribuidor, plataforma, genero, anyo, imagen);
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
	public static VJuegoVO findVJuego( int id ) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			String sql = String.format("SELECT `_id`, `titulo`, `descripcion`, `desarrollador`, `distribuidor`, " +
					"`plataforma`, `genero`, `año`, `imagen` FROM `videojuegos` WHERE _id='%s'", id);
			ResultSet rs = stmt.executeQuery(sql);
			
			if( rs.next() ){
				return new VJuegoVO( rs.getInt("_id"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("desarrollador"), 
						rs.getString("distribuidor"), rs.getString("plataforma"), rs.getString("genero"), rs.getInt("año"), rs.getString("imagen") );
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
	
	
	public static boolean updateVJuego( int vJID, String titulo, String descripcion, String desarrollador, 
			String distribuidor, String plataforma, String genero, int anyo, String imagen){
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------

			String sql = String.format("UPDATE `videojuegos` SET `titulo`='%s'," +
					"`descripcion`='%s',`desarrollador`='%s',`distribuidor`='%s'," +
					"`plataforma`='%s',`genero`='%s',`año`='%s', `imagen`='%s' WHERE _id='%s'", 
					titulo, desarrollador, descripcion, distribuidor, plataforma, genero, anyo, imagen, vJID);
			stmt.execute(sql);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			
			System.out.println("El nickname ya existe");
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
	
	public static boolean deleteVJuego( int vJID ){
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------

			String sql = String.format("DELETE FROM `videojuegos` WHERE `_id`='%s'", vJID);
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
	
	public static ArrayList<VJuegoVO> findAllVJuegos( ) throws ClassNotFoundException, SQLException{

		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
						
			String sql = "SELECT `_id`, `titulo`, `descripcion`, `desarrollador`, `distribuidor`, " +
					"`plataforma`, `genero`, `año`, `imagen` FROM `videojuegos`";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			ArrayList<VJuegoVO> list = new ArrayList<VJuegoVO>();
			
			while( rs.next() ){
				list.add( new VJuegoVO( rs.getInt("_id"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("desarrollador"), 
						rs.getString("distribuidor"), rs.getString("plataforma"), rs.getString("genero"), rs.getInt("año"), rs.getString("imagen") ));
			}
						
			return list;
			
			//Fin de la parte interesante---------------------------------------------
		} catch (ClassNotFoundException e){
			throw e;
		} catch (SQLException e){
			throw e;
		} finally {
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
		}												
	}

	
	public static ArrayList<VJuegoVO> searchVideojuego( String searchField ){

		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			searchField = searchField.toLowerCase();
			
			String sql = "SELECT `_id`, `titulo`, `descripcion`, `desarrollador`, `distribuidor`, " +
					"`plataforma`, `genero`, `año`, `imagen` FROM `videojuegos` WHERE " +
					"LOWER(titulo) LIKE '%"+ searchField + "%' " +
					"OR LOWER(descripcion) LIKE '%" + searchField + "%' " +
					"OR LOWER(desarrollador) LIKE '%" + searchField + "%' " +
					"OR LOWER(distribuidor) LIKE '%" + searchField + "%' " +
					"OR LOWER(plataforma) LIKE '%" + searchField + "s%' " +
					"OR LOWER(genero) LIKE '%" + searchField + "s%' " +
					"OR LOWER(imagen) LIKE '%" + searchField + "%'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			ArrayList<VJuegoVO> list = new ArrayList<VJuegoVO>();
			
			while( rs.next() ){
				list.add( new VJuegoVO( rs.getInt("_id"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("desarrollador"), 
						rs.getString("distribuidor"), rs.getString("plataforma"), rs.getString("genero"), rs.getInt("año"), rs.getString("imagen") ));
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

}
