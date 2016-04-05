package web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import web.database.dataAccessObject.UsuariosDAO;
import web.database.valueObject.UsuarioVO;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean error = false;
		String cabecera = request.getHeader("Authorization");
		cabecera = cabecera.substring(6);
		byte [] decoded = Base64.getDecoder().decode(cabecera);
		String info = new String (decoded, "UTF-8");
		int indice = info.indexOf(":");
		String usuario  = info.substring(0, indice);
		String pass = info.substring(indice+1);
		
		if((usuario==null) || (usuario.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			error = true;
		}
		if((pass==null) || (pass.trim().equals(""))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			error = true;
		}
		
		if(!error){
			UsuarioVO vo = UsuariosDAO.findUser(usuario);
			//Si no existe ese usuario
			if (vo==null){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			//Si la contraseña no coincide
			else if(!(vo.getPasswd()).equals(pass)){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			//Si todo ha ido correctamente
			else{
				response.setStatus(HttpServletResponse.SC_OK);
				JSONObject user = new JSONObject();
				user.element("nombreUsuario", vo.getNickname());
				user.element("id", vo.get_id());
				user.element("nombreApellidos", vo.getNombre());
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(user.toString());
			}	
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
