package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import dao.PacienteDAO;
import dao.RolDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Medico;
import model.Paciente;
import model.Role;

public class RegisterPacienteController implements Initializable {

	@FXML
	private Text TextError;
	@FXML
	private TextField registerSurname;

	@FXML
	private DatePicker registerBirthdate;

	@FXML
	private TextField registerDNI;

	@FXML
	private Button registerButton;

	@FXML
	private PasswordField registerPassword;

	@FXML
	private VBox xBoxOne;

	@FXML
	private TextField registerEmail;

	@FXML
	private TextField registerPhone;

	@FXML
	private CheckBox CheckBoxP;

	@FXML
	private TextField registerName;
	@FXML
	private Hyperlink Hyperlog;

	public PacienteDAO pacienteDAO;
	private RolDAO rolDAO;
	private UserDAO userDAO;
	private Medico medico;

	public void initialize(URL location, ResourceBundle resources) {
		this.pacienteDAO = new PacienteDAO();
		this.rolDAO = new RolDAO();
		this.userDAO = new UserDAO();
	}
	
	public void initData(Medico medico) {
		System.out.println("initdata");
		this.medico = medico;
	}

	@FXML
	void On_Register_Clicked(ActionEvent event) {
		// Los datos del paciente
		String correo = registerEmail.getText();
		String nombre = registerName.getText();
		String apellidos = registerSurname.getText();
		String telefono = registerPhone.getText();
		String password = registerPassword.getText();
		String DNI = registerDNI.getText();
		LocalDate fechad = registerBirthdate.getValue();
        Date date = Date.from(fechad.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Role role = rolDAO.findByName("Paciente");

		// Consultar si ya existe el email registrado en el sistema
		int numUser = userDAO.countUser(correo);
		if (numUser == 0) {
			// Dar de alta el paciente
			Paciente paciente = new Paciente();
			paciente.setNombre(nombre);
			paciente.setApellidos(apellidos);
			paciente.setContrasena(password);
			paciente.setDNI(DNI);
			paciente.setTelefono(telefono);
			paciente.setFecha(date);
			paciente.setRole(role);
			paciente.setCorreo(correo);
			paciente.setMedico(medico);

			pacienteDAO.crearPaciente(paciente);

		} else {
			System.out.println("Este correo ya existe ");
		}

//		if(!Pattern.matches("(([A-Z]|[a-z0-9])+)@([a-z]{2,}).(com|es|net)",correo)){
//			TextError.setText("Wrong e-mail");
//
//		}else if(!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$", contra)){
//			TextError.setText("The passwords must include a capital letter,\n a not capital letter and a number.");
//
//			if(!Pattern.matches("[a-zA-Z0-9]{6,}", contra)){
//				TextError.setText("Password too short");
//
//			}
//		}else if(!Pattern.matches("([0-9]{1,8})([a-z|A-Z])", DNI)){
//			TextError.setText("DNI not valid");

	}

	@FXML
	void On_Login_Clicked(ActionEvent event) {
		Stage stageA = (Stage) Hyperlog.getScene().getWindow();
		stageA.close();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
