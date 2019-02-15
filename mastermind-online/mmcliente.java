//mmcliente.java
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.net.*;
import java.io.*;
import java.util.*;

/*
Servidor MasterMind 
Creadores:
Victor Muñoz Gonzalez
Francisco Andujar De Andres
Proyecto final Redes
*/

public class mmcliente{

	private static boolean playing = true;

	// Gestion de interfaz grafica
	private JFrame frame = new JFrame("MasterMind");
    private JTextField dataField = new JTextField(30);
    private JTextArea messageArea = new JTextArea(30, 30);
	
	
	public static void main(String[] args){
		// Configurar conexion
		try{

			// Pedir direccion
			String serverAddress = JOptionPane.showInputDialog("Introduce la IP (localhost) del servidor:");
			serverAddress = serverAddress.trim();
			if (serverAddress.isEmpty()) serverAddress = "localhost";
			
			// pedir puerto
			String sport = JOptionPane.showInputDialog("Introduce el puerto (8000):");
			sport = sport.trim();
			if (sport.isEmpty()) sport = "8000";
			int port = Integer.parseInt(sport);
			
			// Gestion de redes comunicacion
			Socket socket;
			BufferedReader reader;
			PrintWriter writer;
			OutputStream out;
			String line;
			Scanner scanner;
			
			// Crear socket con la conexion pedida
			socket = new Socket(serverAddress, port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = socket.getOutputStream();
			writer =  new PrintWriter(out, true);
			scanner = new Scanner(System.in);
			
			line = reader.readLine();
			System.out.println(line); // mensaje esperando jugadores
			line = reader.readLine();
			System.out.println(line); // mensaje la partida va a empezar
			
			
			while(true) {
				line = reader.readLine();
				System.out.println(line); // Tu turno
				
				if(line.startsWith("Has")) break;
				
				System.out.println("Escribe algo: ");
                String jugada = scanner.nextLine(); // pedir jugada
				jugada = jugada.trim().toUpperCase();
				
				while(jugada.length() != 4)
				{
					System.out.println("Escribe algo: ");
					jugada = scanner.nextLine(); // pedir jugada
					jugada = jugada.trim().toUpperCase();
				}
				
				writer.println(jugada); // envia tu jugada
				
				line = reader.readLine();
				System.out.println(line); // muestra tu jugada
				
				line = reader.readLine();
				if(line.startsWith("Has"))
				{
					System.out.println(line); // resultado de tu jugada
					break;
				}
				else
				System.out.println(line); // resultado de tu jugada

			}
		} catch (IOException ex) {
			System.exit(0);
		}
	}
}