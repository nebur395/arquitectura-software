package web.database.valueObject;

public class ComentarioVO {
	private int _id;
	private int usuarioID;
	private int vJuego;
	private String comentario;
	private String fecha;
	
	public ComentarioVO(int _id, int usuarioID, int vJuego, String comentario, String fecha) {
		this._id=_id;
		this.usuarioID=usuarioID;
		this.vJuego=vJuego;
		this.comentario=comentario;
		this.fecha=fecha;
	}
	
	public int get_id() {
		return _id;
	}
	
	public int getUsuarioID() {
		return usuarioID;
	}
	
	public int getvJuego() {
		return vJuego;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %s userID: %s vJuegoID: %s Comentario: %s fecha: %s;", _id, usuarioID, vJuego, comentario, fecha);
		
	}
	
	public String serialize(){	
		return String.format("{\"id\": %s, \"usuarioID\": \"%s\", \"vJuego\": \"%s\", \"comentario\": \"%s\", \"fecha\": \"%s\" }", _id, usuarioID, vJuego, comentario, fecha);
	}
}
