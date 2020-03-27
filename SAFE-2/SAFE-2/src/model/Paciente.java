package model;

import java.util.Date;

/**
 * 
 * @author Julio
 *
 */

public class Paciente extends Persona {

	// Atributos
	private long cuidadorId;
	private long familiarId;
	private String historial;

	public Paciente(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha,
			String rol, String telefono, long cuidadorId, long familiarId, String historial) {
		super(nombre, apellidos, dNI, correo, contrasena, fecha, rol, telefono);
		this.cuidadorId = cuidadorId;
		this.familiarId = familiarId;
		this.historial = historial;
	}

	public long getCuidadorId() {
		return cuidadorId;
	}

	public void setCuidadorId(long cuidadorId) {
		this.cuidadorId = cuidadorId;
	}

	public long getFamiliarId() {
		return familiarId;
	}

	public void setFamiliarId(long familiarId) {
		this.familiarId = familiarId;
	}

	public String getHistorial() {
		return historial;
	}

	public void setHistorial(String historial) {
		this.historial = historial;
	}

	// getter and getters

}
