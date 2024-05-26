package com.mygdx.game.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.mygdx.game.pantallas.PantallaNivelUno;

public class HiloServidor extends Thread {
	
	private DatagramSocket conexion;
	
	private boolean fin = false;
	
	private DireccionRed[] clientes = new DireccionRed[2];
	
	private int cantCliente = 0;
	

	
	public HiloServidor() {
	
		try {
			conexion = new DatagramSocket(1113);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}
	
	//envia mensaje al servidor
	public void enviaerMensaje(String msg, InetAddress ip, int puerto) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto );
		try {
			conexion.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	

	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				conexion.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dp);
		}while(!fin);
	}


	//procesa el mensaje que le envia el cliente
	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		
		int nroCliente = -1;
		
		//agurada la conexion hasta que halla dos jugadores
		if(cantCliente > 1) {
			for(int i = 0; i < clientes.length; i++) {
				if(dp.getPort() == clientes[i].getPuerto() && dp.getAddress().equals(clientes[i].getIp())) {
					nroCliente = i;
				}
			}
		}
		
		if(cantCliente < 2) {
			if(msg.equals("Conexion")) {
				System.out.println("Llega msg conexion cliente " + cantCliente);
				if(cantCliente < 2) {
					clientes[cantCliente] = new DireccionRed(dp.getAddress(), dp.getPort());
					enviaerMensaje("OK", clientes[cantCliente].getIp(), clientes[cantCliente++].getPuerto());
					if(cantCliente == 2) {
						for (int i = 0; i < clientes.length; i++) {
							enviaerMensaje("Empieza", clientes[i].getIp(), clientes[i].getPuerto());
						}
			
					}
				}
				
			}
		}  else {
			if(cantCliente != -1) {
				if(msg.equals("ApreteArriba")) { 
					if(nroCliente == 0) {
						
					}
				} else if(msg.equals("ApreteAbajo")) {
					
				} else if(msg.equals("ApreteDerecha")) {
					
				}else if(msg.equals("ApreteIzquierda")) {
					
				} else if(msg.equals("DejeApretarArriba")) {
					
				}else if(msg.equals("DejeApretarDerecha")) {
					
				}else if(msg.equals("DejeApretarIzquierda")) {
					
				}
			}

		}
		
	}
}
