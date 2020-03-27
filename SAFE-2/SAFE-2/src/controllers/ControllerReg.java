package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
import main.Main;


public class ControllerReg implements Initializable{

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



	public void initialize(URL location, ResourceBundle resources) {
	       // TODO (don't really need to do anything here).
	}

	@FXML
	void On_Login_Clicked(ActionEvent event){
		Stage stageA = (Stage) Hyperlog.getScene().getWindow();
	    stageA.close();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Login.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
      stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void On_Register_Clicked(ActionEvent event){

		String correo;
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		String[] Datos;
		System.out.println("Introduce tu correo: ");
		correo = registerEmail.getText();
		String nombre = registerName.getText();
		String apellido = registerSurname.getText();
		String telefono = registerPhone.getText();
		String contra = registerPassword.getText();
		String DNI = registerDNI.getText();
		LocalDate fechad = registerBirthdate.getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String fecha = fechad.format(formatter);
		/*Boolean rollb = CheckBoxP.isSelected();
		String roll;
		if(rollb == true) {
			roll = "Paciente";
		}else{
			roll = "cuidador";
		}*/
		map = Main.leerArchivo();
		Datos = map.get(correo);
		if(Datos == null) {
			Datos = new String[6];
			Datos[1] = "-1";
		}
		if(Datos[0] != null) {
			System.out.println("Este correo ya existe ");
			TextError.setText("Username taken");

		}else if(!Pattern.matches("(([A-Z]|[a-z0-9])+)@([a-z]{2,}).(com|es|net)",correo)){
			TextError.setText("Wrong e-mail");

		}else if(!Pattern.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$", contra)){
			TextError.setText("The passwords must include a capital letter,\n a not capital letter and a number.");

			if(!Pattern.matches("[a-zA-Z0-9]{6,}", contra)){
				TextError.setText("Password too short");

			}
		}else if(!Pattern.matches("([0-9]{1,8})([a-z|A-Z])", DNI)){
			TextError.setText("DNI not valid");

		}else {
			Datos[0] = contra;
			Datos[1] = nombre;
			Datos[2] = apellido;
			Datos[3] = fecha;
			//Datos[4] = this.Roll;
			Datos[5] = DNI;

			System.out.println(correo + Datos[0] + Datos[1] + Datos[2] + Datos[3] + Datos[4] + Datos[5]);
			Main.guardar(correo, Datos);
			On_Login_Clicked(null);
		}

	}
}
