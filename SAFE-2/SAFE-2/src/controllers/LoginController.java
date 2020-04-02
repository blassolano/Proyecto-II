package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Administrador;
import model.Familiar;
import model.Medico;
import model.Paciente;
import model.Role;
import model.User;

public class LoginController implements Initializable {

	public static Stage VentanaLogin = null;
	public User user = null;
	public static Paciente PACIENTE = null;
	public static Medico CUIDADOR = null;
	public static Familiar FAMILIAR = null;
	public static Administrador ADMINISTRADOR = null;
	public UserDAO personaDAO;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private BorderPane CorderPaneLog;
	@FXML
	private TextField loginMail;
	@FXML
	private TextField loginPassword;
	@FXML
	private HBox xBox360;
	@FXML
	private Text MensajeAlerta;
	@FXML
	private Button LoginButton;
	@FXML
	private Hyperlink newAccount;
	@FXML
	private Hyperlink change;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO (don't really need to do anything here).
		this.personaDAO = new UserDAO();
	}
	/*
	 * private void nombre_personaKeyPressed(java.awt.event.KeyEvent evt) {
	 * 
	 * System.out.println(evt.getKeyCode()); if (evt.getKeyCode() ==
	 * KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {
	 * System.out.println("PULSO ENTER"); on_LoginButton_Clicked(null); } }
	 */

	@FXML
	void on_LoginButton_Clicked(ActionEvent event) {
		String correointr = loginMail.getText();
		String contraintr = loginPassword.getText();

		user = personaDAO.buscarUsuarioLogin(correointr, contraintr);
		if (user != null) {
			Role role = user.getRole();
			String roleName = role.getRoleName().toLowerCase();

			switch (roleName) {
			case "medico":
				openMedico();
				break;
			case "familiar":
				openFamiliar();
				break;
			case "paciente":
				openPaciente();
				break;
			case "admministrador":
				openAdministrador();
				break;
			}
		}else {
			System.out.println("No exsite el usuario");
		}

		// codigo nuevo
		/*
		 * System.out.println("Boton pulsado"); ClassPersona Datos; String correo =
		 * loginMail.getText(); String contra = loginPassword.getText(); Map<String,
		 * ClassPersona> map; map = Main3.leerArchivo(); if (map.containsKey(correo)) {
		 * Datos = map.get(correo); System.out.println("Contra: " +
		 * Datos.getContrasena() + " " + contra); correo = loginMail.getText(); if
		 * (contra.equals(Datos.getContrasena())) {
		 * System.out.println("    Datos correctos"); // ClassPersona cliente = new
		 * ClassPersona(correo, Datos[0], Datos[1], Datos[2], // Datos[3], Datos[4],
		 * Datos[5]); System.out.println("Paciente? " + Datos.getCuidadoroPaciente());
		 * PERSONA = Datos; if ((Datos.getCuidadoroPaciente()).equals("Paciente")) {
		 * PACIENTE = Paciente.ControlerPaciente.DatosPaciente(correo); openCliente();
		 * 
		 * } else if ((Datos.getCuidadoroPaciente()).equals("Cuidador")) { CUIDADOR =
		 * Cuidador.ControlerCuidador.DatosCuidador(correo); openCuidador();
		 * 
		 * } else if ((Datos.getCuidadoroPaciente()).equals("Familiar")) { FAMILIAR =
		 * Familiar.ControlerFamiliar.DatosFamiliar(correo); openFamiliar();
		 * 
		 * } else if ((Datos.getCuidadoroPaciente()).equals("Administrador")) {
		 * ADMINISTRADOR =
		 * Administrador.ControlerAdministrador.DatosAdministrador(correo);
		 * openAdministrador(); } } else { System.out.println("Contrasena incorrecta");
		 * MensajeAlerta.setText("Wrong e-mail or Password "); } } else {
		 * MensajeAlerta.setText("Wrong e-mail or Password ");
		 * 
		 * }
		 */
	}

	private void openAdministrador() {
		// TODO Auto-generated method stub
		try {
			Stage stageA = (Stage) LoginButton.getScene().getWindow();
			stageA.close();
			Parent root = FXMLLoader.load(getClass().getResource("/Administrador/administrador interface.fxml"));

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void openFamiliar() {
		try {
			Stage stageA = (Stage) LoginButton.getScene().getWindow();
			stageA.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/familiar.fxml"));
			
			
			Stage stage = new Stage();
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
			FamiliarController familiarController = loader.<FamiliarController>getController();
			familiarController.initData(user);
			// BorderPane root = new BorderPane();
			// Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openMedico() {
		try {
			Stage stageA = (Stage) LoginButton.getScene().getWindow();
			stageA.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medico.fxml"));
			
			
			Stage stage = new Stage();
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
			MedicoController medicoController = loader.<MedicoController>getController();
			medicoController.initData(user);
//			String css = this.getClass().getResource("../application.css").toExternalForm();
//			scene.getStylesheets().add(css);
			
			
			// // Cargamos los datos
			
			
			stage.setMinHeight(400);
			stage.setMinWidth(500);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openPaciente() {
		try {
			Stage stageA = (Stage) LoginButton.getScene().getWindow();
			stageA.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Paciente.fxml"));
			
			
			Stage stage = new Stage();
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
			PacienteController pacienteController = loader.<PacienteController>getController();
			pacienteController.initData(user);

			stage.setTitle("Bienvenido, Bienvenido paciente " + user.getNombre());

			// secondScene.getStylesheets()
			// .add(ControladorSupervisorReg.class.getResource("/Estilos/estilos.css").toExternalForm());
			// newWindow.initModality(Modality.APPLICATION_MODAL);

			stage.setMinHeight(580);
			stage.setMinWidth(830);

			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { Stage stageA = (Stage) LoginButton.getScene().getWindow();
		 * stageA.close(); Parent root =
		 * FXMLLoader.load(getClass().getResource("/Paciente/paciente.fxml"));
		 * 
		 * Scene scene = new Scene(root); Stage stage = new Stage(); // BorderPane root
		 * = new BorderPane(); // Scene scene = new Scene(root,400,400); //
		 * scene.getStylesheets().add(getClass().getResource("application.css").
		 * toExternalForm()); stage.setScene(scene); stage.setTitle("SAVE");
		 * stage.getIcons().add(new Image("/Imagen/logoS.png")); stage.show(); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}

	@FXML
	public void on_change_Clicked(ActionEvent event) {

		try {
			Stage stageA = (Stage) change.getScene().getWindow();
			VentanaLogin = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/CambContra.fxml"));
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
