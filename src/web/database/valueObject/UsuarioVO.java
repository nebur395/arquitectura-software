package web.database.valueObject;

public class UsuarioVO {
	private int _id;
	private String nickname;
	private String nombre;
	private String passwd;
	private String fecha;
	private boolean admin;
	
	
	public UsuarioVO (int _id, String nickname, String nombre, String passwd, String fecha, boolean admin){
		this._id = _id; 
		this.nickname = nickname;
		this.nombre = nombre;
		this.passwd = passwd;
		this.fecha = fecha;
		this.admin = admin;
	}
	
	public int get_id() {
		return _id;
	}
	public String getNickname() {
		return nickname;
	}
	public String getNombre() {
		return nombre;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getFecha() {
		return fecha;
	}
	public boolean getAdmin(){
		return admin;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %s Nickname: %s Nombre: %s Pass: %s Fecha: %s;", _id, nickname, nombre, passwd, fecha);
		
	}
	
	public String serialize(){	
		return String.format("{\"id\": %s, \"nombreUsuario\": \"%s\",  \"nombreReal\": \"%s\", \"fecha\": \"%s\", \"admin\": %s }", 
				_id, nickname, nombre, fecha, admin);
	}
		
}
