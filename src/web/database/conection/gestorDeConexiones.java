package web.database.conection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Stack;

public class gestorDeConexiones {
	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/UnitedGames";

	// Database credentials
	//TODO: Añadir soporte para .properties
	static final String USER = "tomcat";
	static final String PASS = "0000";
	
	private static Stack<Connection> poolDeConexiones = null;
	
	private static void createConnections(){
		Connection conn = null;
		
		try {
			poolDeConexiones = new Stack<Connection>();
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			poolDeConexiones.push(conn);
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			
			e.printStackTrace();
		}
	}
	
	public static synchronized Connection requestConnection(){
		if( poolDeConexiones == null){
			createConnections();
		}
		
		if ( !poolDeConexiones.empty() ){
			return poolDeConexiones.pop();
		} else {
			return null;
		}
		
	}
	
	public static synchronized void releaseConnection(Connection conn){
				
		poolDeConexiones.push(conn);
	
		
	}
		
}
