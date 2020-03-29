package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Medico extends User {

	// atributo
	private List<Paciente> lstPaciente;

	public Medico(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha, Role role,
			String telefono, List<Long> lstPacienteId, String especificaciones) {
		super(nombre, apellidos, dNI, correo, contrasena, fecha, role, telefono);
		this.lstPaciente = new ArrayList<Paciente>();
	}

	public List<Paciente> getLstPaciente() {
		return lstPaciente;
	}

	public void setLstPaciente(List<Paciente> lstPaciente) {
		this.lstPaciente = lstPaciente;
	}

}
