package model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Julio
 *
 */

public class Medico extends Persona {

	// atributo
	private List<Long> lstPacienteId;
	private String especificaciones;

	public Medico(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha, String rol,
			String telefono, List<Long> lstPacienteId, String especificaciones) {
		super(nombre, apellidos, dNI, correo, contrasena, fecha, rol, telefono);
		this.lstPacienteId = lstPacienteId;
		this.especificaciones = especificaciones;
	}

	public List<Long> getLstPacienteId() {
		return lstPacienteId;
	}

	public void setLstPacienteId(List<Long> lstPacienteId) {
		this.lstPacienteId = lstPacienteId;
	}

	public String getEspecificaciones() {
		return especificaciones;
	}

	public void setEspecificaciones(String especificaciones) {
		this.especificaciones = especificaciones;
	}

	// getters and getters

}
