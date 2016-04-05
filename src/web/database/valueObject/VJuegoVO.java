package web.database.valueObject;

public class VJuegoVO {
	private int _id;
	private String titulo;
	private String desarrollador;
	private String descripcion;
	private String distribuidor;
	private String plataforma;
	private String genero;
	private int anyo;
	
	public VJuegoVO (int _id, String titulo, String descripcion, String desarrollador, String distribuidor, String plataforma, String genero, int anyo){
		this._id =_id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.desarrollador = desarrollador;
		this.distribuidor = distribuidor;
		this.plataforma = plataforma;
		this.genero = genero;
		this.anyo = anyo;
	}
	
	public int get_id() {
		return _id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getDesarrollador() {
		return desarrollador;
	}
	public String getDistribuidor() {
		return distribuidor;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public String getGenero() {
		return genero;
	}
	public int getAnyo() {
		return anyo;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %s titulo: %s desarrollador: %s descripcion: %s distribuidor: %s plataforma: %s genero: %s anyo: %s;",
				_id, titulo, desarrollador, descripcion, distribuidor, plataforma, genero, anyo);
		
	}
	
	public String serialize(){	
		return String.format("{\"id\": %s, \"titulo\": \"%s\", \"desarrollador\": \"%s\", \"descripcion\": \"%s\"," +
				" \"distribuidor\": \"%s\", \"plataforma\": \"%s\", \"genero\": \"%s\", \"fecha\": \"%s\" }", 
				_id, titulo, desarrollador, descripcion, distribuidor, plataforma, genero, anyo);
	}
		
}
