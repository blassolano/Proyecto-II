package model;

import java.util.Date;

public class Familiar extends Persona {

	// Atributos

	private long pacienteId;
	private String gradoControl;

	public Familiar(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha,
			String rol, String telefono, long pacienteId, String gradoControl) {
		super(nombre, apellidos, dNI, correo, contrasena, fecha, rol, telefono);
		this.pacienteId = pacienteId;
		this.gradoControl = gradoControl;
	}

	public long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public String getGradoControl() {
		return gradoControl;
	}

	public void setGradoControl(String gradoControl) {
		this.gradoControl = gradoControl;
	}

}
