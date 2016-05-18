package web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import web.database.dataAccessObject.VJuegosDAO;

/**
 * Servlet implementation class Ajustes
 */
 public class Administrador extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Administrador() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = "";
		String distribuidora = "";
		String genero = "";
		String plataforma = "";
		String desarrolladora = "";
		int lanzamiento;
		String descripcion = "";
		String imagen = "";
		
		JSONObject json = null;
		try{
			json = readJSON(request.getReader());
		}
		catch (Exception e){
			System.out.printf("Error al leer el JSON");
		}
		nombre = json.getString("nombre");
		distribuidora = json.getString("distribuidora");
		genero = json.getString("genero");
		plataforma = json.getString("plataforma");
		desarrolladora = json.getString("desarrolladora");
		lanzamiento = json.getInt("lanzamiento");
		descripcion = json.getString("descripcion");
		imagen = json.getString("imagen");
		
		try{
			if(VJuegosDAO.addVJuego(nombre, descripcion, desarrolladora, distribuidora,
										plataforma, genero, lanzamiento, imagen)){
				response.setStatus(HttpServletResponse.SC_OK);
			}
			else{
				response.setContentType("text/html; charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("El videojuego ya existe.");
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
}	
		
