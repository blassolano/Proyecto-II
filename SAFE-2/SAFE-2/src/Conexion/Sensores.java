package Conexion;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import controllers.ControlerPaciente;
import javafx.scene.chart.XYChart;
import model.DatosSensor;
import model.Persona;


public class Sensores{// implements SerialPortEventListener {
	
	
	private ControlerPaciente controlerPaciente;

	DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar cale;
	String fecha;

//	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/cu.usbmodem14101" };
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;

	private Persona persona;
	
	private DatosSensorDAO datosSensorDAO;

	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// https://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		//System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		this.datosSensorDAO = new DatosSensorDAO();

		 
//		CommPortIdentifier portId = null;
//		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
//
//		// First, Find an instance of serial port as set in PORT_NAMES.
//		while (portEnum.hasMoreElements()) {
//			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
//			for (String portName : PORT_NAMES) {
//				if (currPortId.getName().equals(portName)) {
//					portId = currPortId;
//					break;
//				}
//			}
//		}
//		if (portId == null) {
//			System.out.println("Could not find COM port.");
//			return;
//		}

//		try {
//			// open serial port, and use class name for the appName.
//			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
//
//			// set port parameters
//			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
//					SerialPort.PARITY_NONE);
//
//			// open the streams
//			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
//			output = serialPort.getOutputStream();
//
//			// add event listeners
//			serialPort.addEventListener(this);
//			serialPort.notifyOnDataAvailable(true);
//		} catch (Exception e) {
//			System.err.println(e.toString());
//		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent port
	 * locking on platforms like Linux.
	 */
	public synchronized void close() {
//		if (serialPort != null) {
//			serialPort.removeEventListener();
//			serialPort.close();
//		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
//	public synchronized void serialEvent(SerialPortEvent oEvent) {
//		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			try {
//				String[] datos = input.readLine().split(" ");
//				String id = datos[0];
//
//				switch (id) {
//				case "1":
//					System.out.println("Se ha detectado el RFID");
//					detectadoRFID(datos[5] + " " + datos[6] + " " + datos[7] + " " + datos[8]);
//					break;
//				case "2":
//					System.out.println("Se ha detectado Movimiento");
//					if (datos.length == 3)
//						detectadoMovimiento(datos[1] + " " + datos[2]);
//					if (datos.length == 4)
//						detectadoMovimiento(datos[1] + " " + datos[2] + " " + datos[3]);
//					break;
//				case "3":
//					System.out.println("Se ha detectado pulso");
//					double pulsoNum = Double.parseDouble(datos[3]);
//					detectadoPulso(pulsoNum);
//				}
//			} catch (Exception e) {
//				System.err.println(e.toString());
//			}
//		}
//	}

	private void detectadoPulso(double pulsoNum) {
		cale = Calendar.getInstance();
		fecha = dateFormate.format(cale.getTime());

		DatosSensor valorsensor = new DatosSensor(0, fecha, String.valueOf((int) pulsoNum), 2,
				Integer.parseInt(persona.getDNI()));
		datosSensorDAO.insertarValorSensor(valorsensor);

		String a = String.valueOf(this.controlerPaciente.contadorGrafica);
		this.controlerPaciente.series.getData().add(new XYChart.Data<String, Number>(a, pulsoNum));
		this.controlerPaciente.contadorGrafica++;
		this.controlerPaciente.linechart.getData().add(controlerPaciente.series);

	}

	private void detectadoMovimiento(String suceso) {
		cale = Calendar.getInstance();
		fecha = dateFormate.format(cale.getTime());

		DatosSensor valorsensor = new DatosSensor(0, fecha, suceso, 3, Integer.parseInt(persona.getDNI()));
		datosSensorDAO.insertarValorSensor(valorsensor);

		this.controlerPaciente.txtareapresencia.appendText(suceso + " - " + fecha + "\n");

	}

	private void detectadoRFID(String id) {
		cale = Calendar.getInstance();
		fecha = dateFormate.format(cale.getTime());

		DatosSensor valorsensor = new 	DatosSensor(0, fecha, "Puerta abierta" + " - ID de la tarjeta: " + id, 1,
				Integer.parseInt(persona.getDNI()));
		
		datosSensorDAO.insertarValorSensor(valorsensor);

		this.controlerPaciente.txtarearfdi
				.appendText("Puerta abierta" + " - ID de la tarjeta: " + id + " - " + fecha + "\n");

	}

	public void setControladorVentanaPrincipalSupervisado(
			ControlerPaciente controlerPaciente) {
		this.controlerPaciente = controlerPaciente;

	}

	

	public void setUsuario(Persona persona) {
		this.persona = persona;
	}
}