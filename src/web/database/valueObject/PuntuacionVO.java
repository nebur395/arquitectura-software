package web.database.valueObject;

public class PuntuacionVO {
	private int usuarioID;
	private int vJuego;
	private int puntuacion;
	private String fecha;
	
	public PuntuacionVO( int usuarioID, int vJuego, int puntuacion, String fecha) {
		this.usuarioID=usuarioID;
		this.vJuego=vJuego;
		this.puntuacion=puntuacion;
		this.fecha=fecha;
	}
	
	public int getUsuarioID() {
		return usuarioID;
	}
	
	public int getvJuego() {
		return vJuego;
	}
	
	public int getPuntuacion() {
		return puntuacion;
	}
	public String getFecha() {
		return fecha;
	}
	
	@Override
	public String toString() {
		return String.format("userID: %s vJuegoID: %s Puntuacion: %s fecha: %s;", usuarioID, vJuego, puntuacion, fecha);
		
	}
	
	public String serialize(){	
		return String.format("{\"usuarioID\": \"%s\", \"vJuego\": \"%s\", \"puntuacion\": \"%s\", \"fecha\": \"%s\" }", usuarioID, vJuego, puntuacion, fecha);
	}
}
