package controllers;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Persona;

public class ControllerRegPaciente  implements Initializable{

	 	@FXML
	    private Text TextError;

	    @FXML
	    private TextField registerSurname;

	    @FXML
	    private DatePicker registerBirthdate;

	    @FXML
	    private Hyperlink Hyperlog;

	    @FXML
	    private TextField registerDNI;

	    @FXML
	    private TextField registerPhone;

	    @FXML
	    private Button registerButton;

	    @FXML
	    private PasswordField registerPassword;

	    @FXML
	    private VBox xBoxOne;

	    @FXML
	    private TextField registerEmail;

	    @FXML
	    private TextField registerName;

	public void initialize(URL location, ResourceBundle resources) {
	       // TODO (don't really need to do anything here).
	}
	@FXML
	void On_Login_Clicked(ActionEvent event){
		Stage stageA = (Stage) Hyperlog.getScene().getWindow();
	    stageA.close();
	    ControlerCuidador.VentanaCuidador.show();
		/*try {
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Login.fxml"));

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
		Map<String, Persona> map = new LinkedHashMap<String, Persona>();
		String[] Datos = new String[11];
		correo = registerEmail.getText();
		String nombre = registerName.getText();
		String apellido = registerSurname.getText();
		String telefono = registerPhone.getText();
		String contra = registerPassword.getText();
		String DNI = registerDNI.getText();
		LocalDate fechad = registerBirthdate.getValue();
		try {
			LocalDate fechaAct = LocalDate.now();
			int fechaMin = fechaAct.getYear()-150;
			if(fechaMin <= fechad.getYear() && fechad.getYear() <= fechaAct.getYear() && fechad.getDayOfYear() <= fechaAct.getDayOfYear()){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
				String fecha = null;

				fecha = fechad.format(formatter);

				TextError.setText("Enter birthdate");

				map = Main.leerArchivo();
				Persona DatosP = map.get(correo);
				if(DatosP != null) {
					System.out.println("This email adress already exists in the system");
					TextError.setText("User already exist");

				}else if(!Pattern.matches("(([A-Z]|[a-z0-9])+)@([a-z]{2,}).(com|es|net)",correo)){
					TextError.setText("Wrong e-mail");

				}else if(!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$", contra)){
          TextError.setText("The password must contain one upercase letter,\n a lowercase letter and a number");

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
					Datos[4] = "Paciente";
					Datos[5] = DNI;
					Datos[6] = telefono;
					Datos[7] = ControladorLogin.PERSONA.getCorreo();
					Datos[8] = null;
					Datos[9] = null;
					System.out.println(correo + Datos[0] + Datos[1] + Datos[2] + Datos[3] + Datos[4] + Datos[5]+ Datos[6]);
					Main.guardar(correo, Datos);
					CambPaciente(correo);
					try {
						File Sensores = new File("sensores/" + correo + "-sensores.txt");
						FileWriter f = new FileWriter(Sensores);
						f.write("Caidas=true|Pulsometro=true\n");
						f.write("false\n");
						f.close();
						File chat = new File("chat/" + correo + "-chat.txt");
						FileWriter g = new FileWriter(chat);
						g.close();
						File historial = new File("historial/" + correo + "-historial.txt");
						FileWriter h = new FileWriter(historial);
						g.close();
					}catch(Exception e){
						System.out.println(e);
					}
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
    private void CambPaciente(String Correo) {
		// TODO Auto-generated method stub
//    	String[] Datos = new String[10];
//    	String correo = ControladorLogin.PERSONA.getCorreo();
//    	Datos[0] = ControladorLogin.PERSONA.getContrasena();
//    	Datos[1] = ControladorLogin.PERSONA.getNombre();
//    	Datos[2] = ControladorLogin.PERSONA.getApellidos();
//    	Datos[3] = ControladorLogin.PERSONA.getEdad();
//    	Datos[4] = ControladorLogin.PERSONA.getCuidadoroPaciente();
//    	Datos[5] = ControladorLogin.PERSONA.getDNI();
//    	Datos[6] = ControladorLogin.PERSONA.getTelefono();
//    	Datos[7] = ControladorLogin.CUIDADOR.getAdministrador();
//    	if(!ControladorLogin.CUIDADOR.getPaciente().equals("null")){
//    		Datos[8] = ControladorLogin.CUIDADOR.getPaciente() + "|" + Correo;
//    	}else {
//    		Datos[8] = Correo;
//    	}
//    	Datos[9] = null;
//    	Main.guardar(correo, Datos);
    	File abre = new File ("src/Imagen/FotoPerfilDefecto.png");
    	File dest = new File("src/Imagen/FotoPerfil" +Correo + ".png");
    	try {
			controllers.ControlerCambioPerfil.copyFileUsingJava7Files(abre, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
