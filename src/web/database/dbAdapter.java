package web.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

import javax.imageio.stream.FileImageInputStream;

import web.database.conection.gestorDeConexiones;
import web.database.dataAccessObject.UsuariosDAO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class dbAdapter {
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/test";

	// Database credentials
	static final String USER = "test";
	static final String PASS = "testpass";

	public static void main(String[] args) {
		// test code

		//ejecutarFicheroSQL("resources/desplegarBD.sql");	
		
	}

	
	protected static void ejecutarFicheroSQL(String ruta) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			stmt = conn.createStatement();

			Scanner in = new Scanner(new File(ruta));
			String sql ="";
			
			while (in.hasNext()){
				sql = in.nextLine();
				//System.out.println(sql);
				stmt.execute(sql);
			}
			stmt.close();
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Entrada ya existente");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			
			if ( conn != null ) gestorDeConexiones.releaseConnection(conn);
			
		}// end try
	}

	public static boolean addUser(String user, String password) {
		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "INSERT INTO `usuarios` (`nickname`, `pass`) VALUES (`"
					+ user + "`, `" + password + "`)";

			stmt.execute(sql); // Si hay una violacion saltara una exception

			stmt.close();
			conn.close();

			return true;
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Entrada ya existente");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
		return false;
	}

	public static boolean checkPassword(String user, String password) {
		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT _id, nickname FROM usuarios WHERE pass=`"
					+ password + "` and nickname=`" + user + "`";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println("true");
				rs.close();
				stmt.close();
				conn.close();
				return true;
			} else {
				System.out.println("false");
				rs.close();
				stmt.close();
				conn.close();
				return false;
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
		return false;
	}

}
