package model;

import java.util.Date;
import java.util.List;


public class Paciente extends User {
	private Medico medico;
	private List<Familiar> lstFamiliar;
	private String historial;
	
	public Paciente() {
		
	}
	
	public Paciente(User user) {
		super(user);
	}

	public Paciente(long id, String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha, Role rol,
			String telefono, Medico medico, String historial) {
		super(id, nombre, apellidos, dNI, correo, contrasena, fecha, rol, telefono);
		this.medico = medico;
		this.historial = historial;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Familiar> getLstFamiliar() {
		return lstFamiliar;
	}

	public void setLstFamiliar(List<Familiar> lstFamiliar) {
		this.lstFamiliar = lstFamiliar;
	}

	public String getHistorial() {
		return historial;
	}

	public void setHistorial(String historial) {
		this.historial = historial;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", DNI=" + DNI + ", nombre=" + this.nombre + ", apellidos=" + apellidos + ", correo="
				+ correo + ", contrasena=" + contrasena + ", fecha=" + fecha + ", role=" + role + ", telefono="
				+ telefono + ", medico=" + medico + ", lstFamiliar=" + lstFamiliar + ", historial=" + historial +"]\n";
	}

	

}
