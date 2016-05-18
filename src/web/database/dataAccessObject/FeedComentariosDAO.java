package web.database.dataAccessObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import web.database.conection.gestorDeConexiones;

import web.database.valueObject.ComentarioVO;

public class FeedComentariosDAO {
	
	public static ArrayList<ComentarioVO> findFeed(int userID) throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		try{
			Class.forName(gestorDeConexiones.JDBC_DRIVER);
			conn = gestorDeConexiones.requestConnection();
			Statement stmt = conn.createStatement();
			
			//Parte intertesante--------------------------------------------------------
			
			
			String sql = String.format("SELECT C._id, C.usuario, C.videojuego, C.comentario, C.tiempo FROM comentarios C, seguimientos S " +
					"WHERE S.seguidor='%s' AND S.seguido=C.usuario", userID);
			
			
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<ComentarioVO> list = new ArrayList<ComentarioVO>();
			
			while( rs.next() ){
				list.add(new ComentarioVO( rs.getInt("_id"), rs.getInt("usuario"), rs.getInt("videojuego"), 
						rs.getString("comentario"), rs.getString("tiempo") ));
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
}
