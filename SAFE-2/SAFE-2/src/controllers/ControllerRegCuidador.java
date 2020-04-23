package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import model.User;

public class ControllerRegCuidador implements Initializable{
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
	    AdministradorController.VentanaAdministrador.show();
		
	}





	@FXML
	void On_Register_Clicked(ActionEvent event){
//		System.out.println(Main1.ControladorLogin.ADMINISTRADOR.getCuidador());
		String correo;
		Map<String, User> map = new LinkedHashMap<String,User>();
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

				map = Main.leerArchivo();
				User DatosP = map.get(correo);
				if(DatosP != null) {
					System.out.println("This email adress already exists in the system");
					TextError.setText("Username already taken");

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
					Datos[4] = "Cuidador";
					Datos[5] = DNI;
					Datos[6] = telefono;
					Datos[7] = user.getCorreo();
					Datos[8] = null;
					Datos[9] = null;
					System.out.println(correo + Datos[0] + Datos[1] + Datos[2] + Datos[3] + Datos[4] + Datos[5]+ Datos[6]);
					Main.guardar(correo, Datos);
					CambPaciente(correo);
					On_Login_Clicked(null);
				}
			}else{
				TextError.setText("Date is not valid");
			}
		}catch(Exception e) {
			TextError.setText("Oops! Something went wrong");
			e.printStackTrace();
		}

	}
    private void CambPaciente(String Correo) {
		// TODO Auto-generated method stub
    	String[] Datos = new String[10];
    	String correo = user.getCorreo();
    	Datos[0] = user.getContrasena();
    	Datos[1] = user.getNombre();
    	Datos[2] = user.getApellidos();
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	Datos[3] = sf.format(user.getFecha());
    	Datos[4] = user.getRole().getRoleName();
    	Datos[5] = user.getDNI();
    	Datos[6] = user.getTelefono();
    	Datos[7] = null;
//    	if(!Main1.ControladorLogin.ADMINISTRADOR.getCuidador().equals("null")){
//    		Datos[8] = Main1.ControladorLogin.ADMINISTRADOR.getCuidador() + "|" + Correo;
//    	}else {
//    		Datos[8] = Correo;
//    	}
    	Datos[9] = null;
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

}
