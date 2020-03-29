package model;

import java.util.Date;
import java.util.List;

public class Administrador extends User {
	private List<Long> lstMedicoId;

	public Administrador(String nombre, String apellidos, String dNI, String correo, String contrasena, Date fecha,
			Role role, String telefono, List<Long> lstMedicoId) {
		super(nombre, apellidos, dNI, correo, contrasena, fecha, role, telefono);
		this.lstMedicoId = lstMedicoId;
	}

	public List<Long> getLstMedicoId() {
		return lstMedicoId;
	}

	public void setLstMedicoId(List<Long> lstMedicoId) {
		this.lstMedicoId = lstMedicoId;
	}

}
