//mmservidor

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

public class mmserver {
	
	private static int PORT = 8000;
	private static int MAX_PLAYERS = 2;
	private static int plazas = MAX_PLAYERS;
	private static int players = 0;
	private static int turn = 1;
	private static int tirada = 0;
	
	private static int negras = 0, blancas = 0;
	
	private static Socket socket1, socket2;
	private static BufferedReader reader1, reader2;
    private static PrintWriter writer1, writer2;
	private static OutputStream out1, out2;
	private static String comandos;
	
	private static String combinacion = "";
	private static char[] colores = {'R','Y','A','M'};

	
    public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(PORT);

        System.out.println("Servidor de MasterMind: ON");
		System.out.println("Puerto: " + server.getLocalPort());
		System.out.println("Jugadores actuales: " + players);
		System.out.println("Plazas restantes: " + plazas);
		System.out.println("Esperando jugadores...");
		
		int num;
		
		for(int i = 0; i < colores.length; i++)
		{
			num = (int) (Math.random() * 4);
			combinacion = combinacion + colores[num];
		}
		
		System.out.println("La combinacion es: " + combinacion);
		
        try {
			socket1 = server.accept();
			System.out.println("Jugador 1 conectado");
			System.out.println("Jugadores actuales: " + ++players);
			reader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
			out1 = socket1.getOutputStream();
			writer1 = new PrintWriter(out1, true);
			
			writer1.println("Esperando jugadores...");
			
			socket2 = server.accept();
			System.out.println("Jugador 2 conectado");
			System.out.println("Jugadores actuales: " + ++players);
			
			reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			out2 = socket2.getOutputStream();
			writer2 = new PrintWriter(out2, true);
			
			writer2.println("Esperando jugadores...");
			
			writer1.println("La partida va a empezar");
			writer2.println("La partida va a empezar");
			

            while (true) {
				if(turn == 1){
					writer1.println("Tu turno: ");
					
					String jugada = reader1.readLine();
					
					writer1.println("Tu jugada ha sido: " + jugada);
					
					if(compruebaJugada(jugada)) 
					{
						writer1.println("Has ganado, la combinacion era: " + combinacion); //ganador
						writer2.println("Has perdido, la combinacion era: " + combinacion); //perdedor
						turn = 0;
						break;
					}
					else writer1.println("Prueba mejor la proxima vez: " + jugada + " N" + negras + "B" + blancas);// mostrar info
					
					negras = 0;
					blancas = 0;
					
					turn = 2;	
				}
				
				else if(turn == 2){
					writer2.println("Tu turno: ");
					
					String jugada = reader2.readLine();
					
					writer2.println("Tu jugada ha sido: " + jugada);
					
					tirada++;
					System.out.println("Tirada numero: " + tirada);
					
					if(compruebaJugada(jugada))
					{
						writer2.println("Has ganado, la combinacion era: " + combinacion); //ganador
						writer1.println("Has perdido, la combinacion era: " + combinacion); //perdedor
						turn = 0;
						break;
					}
					else
					{
						if(tirada == 15) 
						{
							writer2.println("Has empatado, la combinacion era: " + combinacion); //empate
							writer1.println("Has empatado, la combinacion era: " + combinacion); //empate
						}
						writer2.println("Prueba mejor la proxima vez: " + jugada + " N" + negras + "B" + blancas);// mostrar info
					}					
					negras = 0;
					blancas = 0;
					turn = 1;	
				}
				else{
					break;
				}
				
			
            }
        } finally {
			System.out.println("Fin del juego");
            server.close();
        }
    }
	
	
	private static boolean compruebaJugada(String jugada)
	{
		for(int i = 0; i < combinacion.length(); i++)
		{
			char c = jugada.charAt(i);
			if(combinacion.charAt(i) == c) negras++;
			else if(combinacion.indexOf(c)>=0) blancas++;
		}
		
		if(negras == 4) return true;
		return false;
	}	
}