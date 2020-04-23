package model;

import java.util.Date;

public class Familiar extends User {

	// Atributos

	private long pacienteId;
	private String gradoControl;
	
	public Familiar(User user) {
		super(user);
	}

	public Familiar(long id, String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha,
			Role role, String telefono, long pacienteId, String gradoControl) {
		super(id, nombre, apellidos, dNI, correo, contrasena, fecha, role, telefono);
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
