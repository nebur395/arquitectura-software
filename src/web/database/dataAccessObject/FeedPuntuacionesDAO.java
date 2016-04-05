package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;

import web.database.valueObject.PuntuacionVO;

public class FeedPuntuacionesDAO {
	
	public static ArrayList<PuntuacionVO> findFeed(int userID){
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			
			String sql = String.format("SELECT N.usuario, N.videojuego, N.nota, N.tiempo FROM notas N, seguimientos S " +
					"WHERE S.seguidor='%s' AND S.seguido=N.usuario", userID);
			
			
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<PuntuacionVO> list = new ArrayList<PuntuacionVO>();
			
			while( rs.next() ){
				list.add(new PuntuacionVO( rs.getInt("usuario"), rs.getInt("videojuego"), 
						rs.getInt("nota"), rs.getString("tiempo") ));
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
