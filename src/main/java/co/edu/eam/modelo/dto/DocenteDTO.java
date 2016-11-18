package co.edu.eam.modelo.dto;
import java.io.Serializable;


public class DocenteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long codigoPrograma;
	private String nombrePrograma;
	private String codigoDocente;
	private String nombreDocente;
	private String codigoMateria;
	private String nombreMateria;
	private String grupo;
	private String jornada;
	
	
	
	
		
	
	public String getCodigoMateria() {
		return codigoMateria;
	}
	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}
	public String getNombreMateria() {
		return nombreMateria;
	}
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getJornada() {
		return jornada;
	}
	public void setJornada(String jornada) {
		this.jornada = jornada;
	}
	public long getCodigoPrograma() {
		return codigoPrograma;
	}
	public void setCodigoPrograma(long codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
	public String getCodigoDocente() {
		return codigoDocente;
	}
	public void setCodigoDocente(String codigoDocente) {
		this.codigoDocente = codigoDocente;
	}
	public String getNombreDocente() {
		return nombreDocente;
	}
	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}
	
	
	
	
}
