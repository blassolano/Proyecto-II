package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.User;


public class ControllerRegFamiliar implements Initializable{

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

    @FXML
    private RadioButton Nivel1;

    @FXML
    private RadioButton Nivel2;

    @FXML
    private RadioButton Nivel3;

    private User user;

	public void initData(User user) {
		this.user = user;
	}

	public void initialize(URL location, ResourceBundle resources) {
	       // TODO (don't really need to do anything here).
	}

	@FXML
	void On_Login_Clicked(ActionEvent event){
		Stage stageA = (Stage) Hyperlog.getScene().getWindow();
	    stageA.close();
	    ControlerPaciente.VentanaPaciente.show();
		/*try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
				stage.setTitle("SAVE");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}

	@FXML
	void On_Register_Clicked(ActionEvent event){

		String correo;
		Map<String, User> map = new LinkedHashMap<String, User>();
		String[] Datos = new String[11];
		correo = registerEmail.getText();
		String nombre = registerName.getText();
		String apellido = registerSurname.getText();
		String telefono = registerPhone.getText();
		String contra = registerPassword.getText();
		String DNI = registerDNI.getText();
		boolean Nivel = Nivel1.isSelected();
		String nivel;
		System.out.println("Nivel1: " + Nivel);
		if(Nivel == true) {
			nivel = "1";
		}else {
			Nivel = Nivel2.isSelected();
			System.out.println("Nivel2: " + Nivel);
			if(Nivel == true) {
				nivel = "2";
			}else {
				Nivel = Nivel3.isSelected();
				System.out.println("Nivel3: " + Nivel);
				if(Nivel == true){
					nivel = "3";
				}else {
					System.out.println("Error: Nivel = " + Nivel);
					nivel = "1";

				}
			}
		}
		LocalDate fechad = registerBirthdate.getValue();
		try {
			LocalDate fechaAct = LocalDate.now();
			int fechaMin = fechaAct.getYear()-150;
			if(fechaMin <= fechad.getYear() && fechad.getYear() <= fechaAct.getYear() && fechad.getDayOfYear() <= fechaAct.getDayOfYear()){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
				String fecha = fechad.format(formatter);
				map = Main.leerArchivo();
				User DatosP = map.get(correo);
				if(DatosP != null) {
					System.out.println("Este correo ya existe ");
					TextError.setText("User already exist");

				}else if(!Pattern.matches("(([A-Z]|[a-z0-9])+)@([a-z]{2,}).(com|es|net)",correo)){
					TextError.setText("Wrong e-mail");

				}else if(!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$", contra)){
					TextError.setText("The passwords must include a capital letter,\n a not capital letter and a number.");

					if(!Pattern.matches("[a-zA-Z0-9]{6,}", contra)){
						TextError.setText("Password too short");

					}
				}else if(!Pattern.matches("([A-Z0-9]{8})([A-Z])", DNI)){
					TextError.setText("DNI not valid");

				}else if(!Pattern.matches("([6][0-9]{8})", telefono)){
					TextError.setText("Phone number not valid");

				}else if(nombre == null && apellido == null){
					TextError.setText("Name or surname does not exist");
				}else {
					//ClassPaciente D = ControlerPaciente.DatosPaciente(correo);
					Datos[0] = contra;
					Datos[1] = nombre;
					Datos[2] = apellido;
					Datos[3] = fecha;
					Datos[4] = "Familiar";
					Datos[5] = DNI;
					Datos[6] = telefono;
					Datos[7] = user.getCorreo();
					Datos[8] = nivel;
					Datos[9] = null;
					System.out.println(correo + Datos[0] + Datos[1] + Datos[2] + Datos[3] + Datos[4] + Datos[5]+ Datos[6]);
					Main.guardar(correo, Datos);
					CambFamiliares(correo);
					File chat = new File("chat/" + correo + "-chat.txt");
					FileWriter g = new FileWriter(chat);
					g.close();
					On_Login_Clicked(null);
				}
			}else{
				TextError.setText("Date is not valid");
			}
		}catch(Exception e) {
			TextError.setText("Oops! Something went wrong");
			System.out.println(e);
		}

	}
    private void CambFamiliares(String Correo) {
		// TODO Auto-generated method stub
    	String[] Datos = new String[10];
    	String correo = user.getCorreo();
//    	Datos[0] = ControladorLogin.PERSONA.getContrasena();
//    	Datos[1] = ControladorLogin.PERSONA.getNombre();
//    	Datos[2] = ControladorLogin.PERSONA.getApellidos();
//    	Datos[3] = ControladorLogin.PERSONA.getEdad();
//    	Datos[4] = ControladorLogin.PERSONA.getCuidadoroPaciente();
//    	Datos[5] = ControladorLogin.PERSONA.getDNI();
//    	Datos[6] = ControladorLogin.PERSONA.getTelefono();
//    	Datos[7] = ControladorLogin.PACIENTE.getCuidador();
//    	if(!ControladorLogin.PACIENTE.getFamiliar().equals("null")){
//    		Datos[8] = ControladorLogin.PACIENTE.getFamiliar() + "|" + Correo;
//    	}else {
//    		Datos[8] = Correo;
//    	}
//    	Datos[9] =ControladorLogin.PACIENTE.getHistorial();
    	Main.guardar(correo, Datos);
    	File abre = new File ("src/Imagen/FotoPerfilDefecto.png");
    	File dest = new File("src/Imagen/FotoPerfil" +Correo + ".png");
    	try {
			controllers.ControlerCambioPerfil.copyFileUsingJava7Files(abre, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
    void On_nivel1_Clicked(ActionEvent event) {
    	//OpenAutorizacion(3);
    	System.out.println("Autorizacion1");
    	File pdfFile = new File("pdf/Autorizaciones/AutorizacionNivel1.pdf");
    	 try {
			Desktop.getDesktop().open(pdfFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	@FXML
    void On_nivel2_Clicked(ActionEvent event) {
		//OpenAutorizacion(2);
		System.out.println("Autorizacion2");
		File pdfFile = new File("pdf/Autorizaciones/AutorizacionNivel2.pdf");
   	 try {
			Desktop.getDesktop().open(pdfFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void On_nivel3_Clicked(ActionEvent event) {
    	//OpenAutorizacion(1);
    	System.out.println("Autorizacion3");
    	File pdfFile = new File("pdf/Autorizaciones/AutorizacionNivel3.pdf");
   	 try {
			Desktop.getDesktop().open(pdfFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
