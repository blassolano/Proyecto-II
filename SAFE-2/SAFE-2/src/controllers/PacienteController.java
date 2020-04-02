package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import dao.DatosSensorDAO;
import dao.Sensores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.DatosSensor;
import model.Paciente;
import model.User;

public class PacienteController implements Initializable {
	public static Stage VentanaPaciente = null;
	private static String CorreoChat = null;
//		@FXML
//	    private MenuButton Nombre;
//
//	    @FXML
//	    private CheckBox OnOfPulsometro;
//
//	    @FXML
//	    private ImageView FotoPaciente;
//
//	    @FXML
//	    private ImageView Foto;
//
//	    @FXML
//	    private CheckBox DetectorCaidas1;
//
//	    @FXML
//	    private TextArea chatStreaming;
//
//	    @FXML
//	    private BorderPane bp;
//
//	    @FXML
//	    private Text FechaNacimiento;
//
//	    @FXML
//	    private Text Correo;
//
//	    @FXML
//	    private HBox infoPulsometro;
//
//	    @FXML
//	    private Button enviar;
//
//	    @FXML
//	    private Text Apellido1;
//
//	    @FXML
//	    private Text FechaNacimiento1;
//
//	    @FXML
//	    private HBox infoCaidas;
//
//	    @FXML
//	    private Text NombreCuidador;
//
//	    @FXML
//	    private Text Apellido;
//
//	    @FXML
//	    private Text Correo1;
//
//	    @FXML
//	    private Text NumTelefono;
//
//	    @FXML
//	    private ImageView FotoCuidador;
//
//	    @FXML
//	    private MenuItem Perfil;
//
//	    @FXML
//	    private Button newAccount;
//
//	    @FXML
//	    private MenuButton menuChat;
//
//	    @FXML
//	    private VBox DetectorCaidas;
//
//	    @FXML
//	    private Text NombrePaciente;
//
//	    @FXML
//	    private Text DNI1;
//
//	    @FXML
//	    private Text NumTelefono1;
//
//	    @FXML
//	    private MenuItem LogOut;
//
//	    @FXML
//	    private Button Next;
//
//	    @FXML
//	    private Button Previous;
//
//	    @FXML
//	    private TextField mensaje;
//
//	    @FXML
//	    private Button OnOff;
//
//	    @FXML
//	    private Text DNI;
//	    
//	    @FXML
//	    private TextArea historialStreaming;
//	    
//	    @FXML
//	    private Circle CirculoFall;

	@FXML
	public TextArea txtarearfdi;

	@FXML
	public TextArea txtareapresencia;

	@FXML
	public LineChart<String, Number> linechart;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	@FXML
	private Tab TabHistorial;

	@FXML
	private TextArea historialStreaming;

	@FXML
	private Button newAccount;

	@FXML
	private ImageView FotoPaciente;

	@FXML
	private Text NombrePaciente;

	@FXML
	private Text Apellido1;

	@FXML
	private Text FechaNacimiento1;

	@FXML
	private Text DNI1;

	@FXML
	private Text Correo1;

	@FXML
	private Text NumTelefono1;

	@FXML
	private Button Previous;

	@FXML
	private Button Next;

	@FXML
	private ImageView FotoCuidador;

	@FXML
	private Text NombreCuidador;

	@FXML
	private Text Apellido;

	@FXML
	private Text FechaNacimiento;

	@FXML
	private Text DNI;

	@FXML
	private Text Correo;

	@FXML
	private Text NumTelefono;

	@FXML
	private BorderPane bp;

	@FXML
	private TextArea chatStreaming;

	@FXML
	private TextField mensaje;

	@FXML
	private Button enviar;

	@FXML
	private MenuButton menuChat;

	@FXML
	private MenuButton Nombre;

	@FXML
	private MenuItem Perfil;

	@FXML
	private MenuItem LogOut;

	@FXML
	private ImageView Foto;

	private User user;

	public XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	public int contadorGrafica = 0;
	private Sensores sensores;
	private Thread t;

	public void initData(User user) {
		System.out.println("init data");
		this.user = user;
		postInit();
	}

	private void postInit() {
		System.out.println("postinit");
		Nombre.setText(user.getNombre() + " " + user.getApellidos());
		try {
			File dest = new File("src/Imagen/FotoPerfil" + user.getCorreo() + ".png");
			String thePath = dest.toURI().toURL().toExternalForm();
			Image image = new Image(thePath);
			Foto.setImage(image);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		NombrePaciente.setText(user.getNombre());
		Apellido1.setText(user.getApellidos());
		NumTelefono1.setText(user.getTelefono());
		DNI1.setText(user.getDNI());
		Correo1.setText(user.getCorreo());

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		FechaNacimiento1.setText(sf.format(user.getFecha()));

		try {
			String DireccionFotoP = "Imagen/FotoPerfil" + user.getCorreo() + ".png";
			System.out.println(DireccionFotoP);
			Image image = new Image(DireccionFotoP);
			FotoPaciente.setImage(image);
		} catch (Exception e) {
			String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
			System.out.println(DireccionFotoP);
			Image image = new Image(DireccionFotoP);
			FotoPaciente.setImage(image);
		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO (don't really need to do anything here).
		// System.load("/Usersâ?©/Suabcâ?©/â?¨Downloadsâ?©/RXTXcomm.jar");
		// System.setProperty("java.library.path", "/path/to/library");
		System.out.println("Initialize");
		lanzarHiloEscuchadorDeSensores();

		// codigo nuevo

		recogerSensores(user);

		// codigo nuevo

		// Nombre y foto MenuButton

		// DatosFamiliar
		// String FamiliarDat = Main1.ControladorLogin.PACIENTE.getFamiliar().replace("
		// ","");
		/*
		 * String FamiliarDat =
		 * Main1.ControladorLogin.PAERSONA.getFamiliar().replace(" ","");
		 * 
		 * Map<String, ClassPersona> mapFamily = Main1.Main3.leerArchivo(); ClassPersona
		 * FamiliarDatos = mapFamily.get(FamiliarDat);
		 * 
		 * 
		 * //Datos Cuidador
		 * 
		 * String Cuidador =
		 * Main1.ControladorLogin.PACIENTE.getCuidador().replace(" ",""); Map<String,
		 * ClassPersona> map = Main1.Main3.leerArchivo(); ClassPersona CuidadorDatos =
		 * map.get(Cuidador); NombreCuidador.setText(CuidadorDatos.getNombre());
		 * Apellido.setText(CuidadorDatos.getApellidos());
		 * NumTelefono.setText(CuidadorDatos.gettelefono());
		 * DNI.setText(CuidadorDatos.getDNI());
		 * Correo.setText(CuidadorDatos.getCorreo());
		 * FechaNacimiento.setText(CuidadorDatos.getEdad()); try { String DireccionFotoP
		 * = "Imagen/FotoPerfil" + CuidadorDatos.getCorreo() + ".png";
		 * System.out.println(DireccionFotoP); Image image = new Image(DireccionFotoP);
		 * FotoCuidador.setImage(image); }catch(Exception e){ e.printStackTrace(); }
		 * //Datos Familiares
		 * 
		 * //System.out.println(Main1.controler1.CUIDADOR.getPaciente()); Map<String,
		 * String[]> mapC = Main1.Main3.leerArchivoClase(); String[] p =
		 * mapC.get(Main1.ControladorLogin.PERSONA.getCorreo()); String CorreoAnt =
		 * Correo1.getText(); if(!p[2].equals("null")) { p[2] = p[2].replace(" ","");
		 * String[] Pacientes = p[2].split("\\|");
		 * 
		 * Map<String, ClassPersona> mapF = Main1.Main3.leerArchivo(); int l =
		 * Pacientes.length; System.out.println("Pacientes[0] = " + Pacientes [0]);
		 * System.out.println("Numero pacientes " + l); ClassPersona[] Paciente = new
		 * ClassPersona[l]; for(int i = 0; i<l; i++) {
		 * System.out.println("Datos paciente " + i + " " + mapF.get(Pacientes[i]));
		 * Paciente[i] = mapF.get(Pacientes[i]); }
		 * NombrePaciente.setText(Paciente[0].getNombre());
		 * Apellido1.setText(Paciente[0].getApellidos());
		 * NumTelefono1.setText(Paciente[0].gettelefono());
		 * DNI1.setText(Paciente[0].getDNI()); Correo1.setText(Paciente[0].getCorreo());
		 * FechaNacimiento1.setText(Paciente[0].getEdad()); try { String DireccionFotoP
		 * = "Imagen/FotoPerfil" + Paciente[0].getCorreo() + ".png";
		 * System.out.println(DireccionFotoP); Image image = new Image(DireccionFotoP);
		 * FotoPaciente.setImage(image); }catch(Exception e){ e.printStackTrace(); } }
		 * //Sensores
		 * 
		 * try {
		 * 
		 * //Leo el fichero al reves
		 * 
		 * FileReader fr = new FileReader("sensores/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-sensores.txt"); BufferedReader
		 * bf = new BufferedReader(fr); LinkedList<String> list = new
		 * LinkedList<String>(); String sCadena; while ((sCadena = bf.readLine())!=null)
		 * { list.add(sCadena); } Iterator<String> it = list.descendingIterator();
		 * boolean encontrado = false; while(it.hasNext() && encontrado == false) {
		 * String Linea = it.next(); System.out.println(Linea); String[] info =
		 * Linea.split("\\|"); String[] infoC = info[0].split("=");
		 * if(infoC[0].equals("Caidas")) { encontrado = true;
		 * if(infoC[1].equals("true")) { DetectorCaidas1.setSelected(true); }else {
		 * DetectorCaidas1.setSelected(false); } String[] infoP = info[1].split("=");
		 * if(infoP[1].equals("true")) { OnOfPulsometro.setSelected(true); }else {
		 * OnOfPulsometro.setSelected(false); } }
		 * 
		 * } } catch (FileNotFoundException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 * 
		 * if(OnOfPulsometro.isSelected()) { try { String path = "sensores/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-sensores.txt"; //Utilizo una
		 * lista para leer el fichero alreves FileReader fw = new FileReader(path);
		 * BufferedReader bf = new BufferedReader(fw); LinkedList<String> list = new
		 * LinkedList<String>(); String sCadena; while ((sCadena = bf.readLine())!=null)
		 * { list.add(sCadena); } Iterator<String> it = list.descendingIterator();
		 * //Creo tabla
		 * 
		 * final NumberAxis xAxis = new NumberAxis(); final NumberAxis yAxis = new
		 * NumberAxis(); xAxis.setLabel("Hours ago"); //creating the chart final
		 * LineChart<Number,Number> lineChart = new
		 * LineChart<Number,Number>(xAxis,yAxis); XYChart.Series series = new
		 * XYChart.Series(); String linea; int cont = 0; int valor; while(it.hasNext()
		 * && cont < 15) { linea = it.next(); linea = linea.replace(" ",""); try { valor
		 * = Integer.parseInt(linea); series.getData().add(new XYChart.Data(cont,
		 * valor)); cont++; }catch(Exception e){
		 * 
		 * }
		 * 
		 * } bf.close(); lineChart.getData().add(series);
		 * infoPulsometro.getChildren().add(lineChart); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } }
		 * if(DetectorCaidas1.isSelected()) { try { String path = "sensores/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-sensores.txt"; FileReader fw =
		 * new FileReader(path);
		 * 
		 * //Utilizo una lista para leer el fichero alreves
		 * 
		 * BufferedReader bf = new BufferedReader(fw); LinkedList<String> list = new
		 * LinkedList<String>(); String sCadena; while ((sCadena = bf.readLine())!=null)
		 * { list.add(sCadena); } Iterator<String> it = list.descendingIterator();
		 * String Valor = "n"; while(it.hasNext() && !Valor.equals("false") &&
		 * !Valor.equals("true")) { Valor = it.next(); if(Valor.equals("false")) {
		 * CirculoFall.setFill(Color.LIGHTGREEN); }else if(Valor.equals("true")) {
		 * CirculoFall.setFill(Color.RED); } } bf.close();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } DetectorCaidas1.setDisable(false);
		 * OnOfPulsometro.setDisable(false); //chat
		 * 
		 * MenuItem menuItem1 = new MenuItem(); menuItem1 = new MenuItem("Cuidador: " +
		 * CuidadorDatos.getCorreo() + " (" + CuidadorDatos.getNombre() + " " +
		 * CuidadorDatos.getApellidos() + ")"); menuItem1.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { try { String path = "chat/"
		 * + Main1.ControladorLogin.PERSONA.getCorreo() + "-chat.txt"; String content;
		 * content = readFile(path); chatStreaming.setText(content); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * }); menuChat.getItems().add(menuItem1); if(!p[2].equals("null")) { p[2] =
		 * p[2].replace(" ",""); String[] Familiares = p[2].split("\\|"); ClassPersona[]
		 * Familiar = new ClassPersona[Familiares.length]; for(int i = 0;
		 * i<Familiares.length; i++) { int r = i; Familiar[i] = map.get(Familiares[i]);
		 * menuItem1 = new MenuItem("Familiar: " + Familiar[i].getCorreo() + " (" +
		 * Familiar[i].getNombre() + "" + Familiar[i].getApellidos()+ ")");
		 * menuItem1.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { CorreoChat =
		 * Familiar[r].getCorreo(); try { String path = "chat/" +
		 * Familiar[r].getCorreo() + "-chat.txt"; String content; content =
		 * readFile(path); chatStreaming.setText(content); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } } });
		 * menuChat.getItems().add(menuItem1); } }
		 * 
		 * // Historial medico try { String path = "historial/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-historial.txt"; String
		 * content; content = readFile(path); historialStreaming.setText(content); }
		 * 
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 */
	}

	private void lanzarHiloEscuchadorDeSensores() {
		// TODO Auto-generated method stub
		sensores = new Sensores();
		sensores.setControladorVentanaPrincipalSupervisado(this);
		sensores.setUsuario(user);

		sensores.initialize();
		t = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("LISTENERS DE SENSORES ACTIVADOS");
	}

	private void recogerSensores(User persona) {
		txtarearfdi.clear();
		txtareapresencia.clear();
		linechart.getData().clear();

		x.setLabel("tiempo");
		y.setLabel("pulso");

		series = new XYChart.Series<String, Number>();

		series.setName("Pulsaciones");

		DatosSensorDAO datosSensorDAO = new DatosSensorDAO();
		////////////////////// lineas base de datos
		List<DatosSensor> listaSensores = datosSensorDAO.recogerSensores(persona);

		for (int i = 0; i < listaSensores.size(); i++) {
			if (listaSensores.get(i).getSensores_key() == 1) {
				txtarearfdi.appendText(listaSensores.get(i).getDato().toString() + "\n");
			} else if (listaSensores.get(i).getSensores_key() == 2) {
				int numero = Integer.parseInt(listaSensores.get(i).getDato().toString());
				String a = String.valueOf(contadorGrafica);
				series.getData().add(new XYChart.Data<String, Number>(a, numero));
				contadorGrafica++;
			} else if (listaSensores.get(i).getSensores_key() == 3) {
				txtareapresencia
						.appendText(listaSensores.get(i).getDato() + " - " + listaSensores.get(i).getFecha() + "\n");

			} else {
				System.out.println("ControladorVentanaPrincipalSupervisado-Otro valor en sensor");
			}
		}

		linechart.getData().add(series);
	}

	@FXML
	void on_newAccount_Clicked(ActionEvent event) {
		try {
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			VentanaPaciente = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Paciente/RegisterFamiliar.fxml"));
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

	public static Paciente DatosPaciente(String correo) {
		return null;
		/*
		 * System.out.print("Entro en DatosPaciente");
		 * 
		 * Map<String, String[]> map = Main1.Main3.leerArchivoClase(); String[] Datos =
		 * map.get(correo); ClassPaciente paciente = null; try { paciente = new
		 * ClassPaciente(Datos[1], Datos[2], Datos[3]);
		 * System.out.println("\tCuidador: " + paciente.getCuidador());
		 * System.out.println("\tFamiliar: " + paciente.getFamiliar());
		 * System.out.println("\tRuta: " + paciente.getHistorial()); }catch(Exception
		 * e){ System.out.println(e); }
		 * 
		 * return paciente;
		 * 
		 */
	}

	@FXML
	void on_LogOut_Clicked(ActionEvent event) {
		try {
			Main.imagenPerfilControladorVentana = null;
			// Main1.Main3.FotoPerfil = null;
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			stageA.close();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("SAVE");
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/Imagen/logoS.png"));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@FXML
	void on_Perfil_Clicked(ActionEvent event) {

		try {
			Stage stageA = (Stage) Nombre.getScene().getWindow();
			VentanaPaciente = stageA;
			stageA.hide();
			Parent root = FXMLLoader.load(getClass().getResource("/Main1/Perfil.fxml"));
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
			System.out.println(e);
		}

	}

	private String readFile(String file) throws IOException {
		return file;
		/*
		 * BufferedReader reader = new BufferedReader(new FileReader (file)); String
		 * line = null; StringBuilder stringBuilder = new StringBuilder(); String ls =
		 * System.getProperty("line.separator");
		 * 
		 * try { while((line = reader.readLine()) != null) { stringBuilder.append(line);
		 * stringBuilder.append(ls); }
		 * 
		 * return stringBuilder.toString(); } finally { reader.close(); }
		 */
	}

	@FXML
	void caidas_Actioned() {
		/*
		 * try { CirculoFall.setFill(Color.SNOW); String path = "sensores/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-sensores.txt"; FileWriter fw =
		 * new FileWriter(path, true); if(DetectorCaidas1.isSelected()) {
		 * if(OnOfPulsometro.isSelected()) { fw.write("Caidas=true|Pulsometro=true\n");
		 * }else { fw.write("Caidas=true|Pulsometro=false\n"); } //String content =
		 * readFile(path); //Text contenido = new Text(content);
		 * 
		 * //HBox infoCaidas = new HBox(); //infoCaidas.getChildren().add(contenido);
		 * try { FileReader fr = new FileReader(path);
		 * 
		 * //Utilizo una lista para leer el fichero alreves
		 * 
		 * BufferedReader bf = new BufferedReader(fr); LinkedList<String> list = new
		 * LinkedList<String>(); String sCadena; while ((sCadena = bf.readLine())!=null)
		 * { list.add(sCadena); } Iterator<String> it = list.descendingIterator();
		 * String Valor = "n"; while(it.hasNext() && !Valor.equals("false") &&
		 * !Valor.equals("true")) { Valor = it.next(); if(Valor.equals("false")) {
		 * CirculoFall.setFill(Color.LIGHTGREEN); }else if(Valor.equals("true")) {
		 * CirculoFall.setFill(Color.RED); } } bf.close(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } }else {
		 * if(OnOfPulsometro.isSelected()) { fw.write("Caidas=false|Pulsometro=true\n");
		 * }else { fw.write("Caidas=false|Pulsometro=false\n"); } } fw.close(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@FXML
	void pulsometroActioned() {
		/*
		 * infoPulsometro.getChildren().clear(); try { String path = "sensores/" +
		 * Main1.ControladorLogin.PERSONA.getCorreo() + "-sensores.txt"; FileWriter fw =
		 * new FileWriter(path, true); if(OnOfPulsometro.isSelected()) {
		 * if(DetectorCaidas1.isSelected()) { fw.write("Caidas=true|Pulsometro=true\n");
		 * }else { fw.write("Caidas=false|Pulsometro=true\n"); } //String content =
		 * readFile(path); //Text contenido = new Text(content);
		 * 
		 * //HBox infoCaidas = new HBox();
		 * //infoPulsometro.getChildren().add(contenido); try { //Utilizo una lista para
		 * leer el fichero alreves FileReader fr = new FileReader(path); BufferedReader
		 * bf = new BufferedReader(fr); LinkedList<String> list = new
		 * LinkedList<String>(); String sCadena; while ((sCadena = bf.readLine())!=null)
		 * { list.add(sCadena); } Iterator<String> it = list.descendingIterator();
		 * //Creo tabla
		 * 
		 * final NumberAxis xAxis = new NumberAxis(); final NumberAxis yAxis = new
		 * NumberAxis(); xAxis.setLabel("Hours ago"); //creating the chart final
		 * LineChart<Number,Number> lineChart = new
		 * LineChart<Number,Number>(xAxis,yAxis); XYChart.Series series = new
		 * XYChart.Series(); String linea; int cont = 0; int valor; while(it.hasNext()
		 * && cont < 15) { linea = it.next(); linea = linea.replace(" ",""); try { valor
		 * = Integer.parseInt(linea); series.getData().add(new XYChart.Data(-cont,
		 * valor)); cont++; }catch(Exception e){
		 * 
		 * }
		 * 
		 * } lineChart.getData().add(series);
		 * infoPulsometro.getChildren().add(lineChart); bf.close(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }else {
		 * if(DetectorCaidas1.isSelected()) {
		 * fw.write("Caidas=true|Pulsometro=false\n"); }else {
		 * fw.write("Caidas=false|Pulsometro=false\n"); } } fw.close(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@FXML
	void enviarActioned() {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String aux = mensaje.getText();

		try {
			String path = "chat/" + CorreoChat + "-chat.txt";
			FileWriter fw = new FileWriter(path, true);
			fw.write(System.lineSeparator());
			fw.write(user.getNombre());
			fw.write("\t");
			fw.write(timeStamp);
			fw.write("\t");
			fw.write(aux);
			fw.write(System.lineSeparator());
			fw.flush();
			String content = readFile(path);
			// Text contenido = new Text(content);
			chatStreaming.setText(content);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mensaje.clear();
		mensaje.requestFocus();
	}

	@FXML
	void On_Previous_Clicked() {
		Map<String, String[]> mapC = Main.leerArchivoClase();
		String[] p = mapC.get(user.getCorreo());
		String CorreoAnt = Correo1.getText();
		if (!p[2].equals("null")) {
			p[2] = p[2].replace(" ", "");
			String[] Pacientes = p[2].split("\\|");

			Map<String, User> map = Main.leerArchivo();
			int l = Pacientes.length;
			System.out.println("Pacientes[0] = " + Pacientes[0]);
			System.out.println("Numero pacientes " + l);
			User[] Paciente = new User[l];
			int numPac = 0;
			for (int i = 0; i < l; i++) {
				System.out.println("Datos paciente " + i + " " + map.get(Pacientes[i]));
				Paciente[i] = map.get(Pacientes[i]);
				// System.out.println("Paciente: " + Paciente[i] + " Paciente anterior: " +
				// CorreoAnt);
				if (CorreoAnt.equals(Pacientes[i])) {
					numPac = i - 1;
					// System.out.println("Numero Paciente " + numPac);
					if (numPac < 0) {
						numPac = l - 1;
						// System.out.println("Numero Paciente " + numPac);
					}
				}
			}

			NombrePaciente.setText(user.getNombre());
			Apellido1.setText(user.getApellidos());
			NumTelefono1.setText(user.getTelefono());
			DNI1.setText(user.getDNI());
			Correo1.setText(user.getCorreo());

			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			FechaNacimiento1.setText(sf.format(user.getFecha()));

			try {
				String DireccionFotoP = "Imagen/FotoPerfil" + user.getCorreo() + ".png";
				System.out.println(DireccionFotoP);
				Image image = new Image(DireccionFotoP);
				FotoPaciente.setImage(image);
			} catch (Exception e) {
				String DireccionFotoP = "Imagen/FotoPerfilDefecto.png";
				System.out.println(DireccionFotoP);
				Image image = new Image(DireccionFotoP);
				FotoPaciente.setImage(image);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void On_Next_Clicked() {
		Map<String, String[]> mapC = Main.leerArchivoClase();
		String[] p = mapC.get(user.getCorreo());
		String CorreoAnt = Correo1.getText();
		if (!p[2].equals("null")) {
			p[2] = p[2].replace(" ", "");
			String[] Pacientes = p[2].split("\\|");

			Map<String, User> map = Main.leerArchivo();
			int l = Pacientes.length;
			// System.out.println("Pacientes[0] = " + Pacientes [0]);
			// System.out.println("Numero pacientes " + l);
			User[] Paciente = new User[l];
			int numPac = 0;
			for (int i = 0; i < l; i++) {
				// System.out.println("Datos paciente " + i + " " + map.get(Pacientes[i]));
				Paciente[i] = map.get(Pacientes[i]);
				// System.out.println("Paciente: " + Pacientes[i] + " Paciente anterior: " +
				// CorreoAnt);
				if (CorreoAnt.equals(Pacientes[i])) {
					numPac = i + 1;
					// System.out.println("Numero Paciente " + numPac);
					if (numPac >= l) {
						numPac = 0;
						// System.out.println("Numero Paciente " + numPac);
					}
				}
			}

		}
	}

}
