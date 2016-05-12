package web.servlets;

import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.util.Iterator;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import web.database.dataAccessObject.VJuegosDAO;
import web.database.dataAccessObject.PuntuacionesDAO;
import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.VJuegoVO;
import web.database.valueObject.PuntuacionVO;
import web.database.valueObject.UsuarioVO;
import web.utils.PuntuacionesUtils;


public class Buscar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Buscar() {
        super();
        // TODO Auto-generated constructor stub
    }
    
        /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bUsuarios = request.getHeader("usuario");
		JSONArray ja = new JSONArray();
		try{
			if(bUsuarios.equals("true")){
				Iterator<UsuarioVO> iterador = (UsuariosDAO.findAllUsers()).iterator();
				while(iterador.hasNext()){
					UsuarioVO vo = iterador.next();
					JSONObject usuario = JSONObject.fromObject(vo.serialize());
					ja.add(usuario);
				}
			}
			else{
				Iterator<VJuegoVO> iterador = (VJuegosDAO.findAllVJuegos()).iterator();
				while(iterador.hasNext()){
					VJuegoVO vo = iterador.next();
					JSONObject juego = JSONObject.fromObject(vo.serialize());
					juego.element("valoracion", PuntuacionesUtils.calcularPuntuacion(PuntuacionesDAO.listPuntuaciones(vo.get_id())));
					ja.add(juego);
				}
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(ja.toString());
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error interno en el servidor. Vuelva intentarlo más tarde");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
