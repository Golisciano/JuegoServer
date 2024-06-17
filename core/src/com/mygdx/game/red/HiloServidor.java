package com.mygdx.game.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.sprites.Ninja;

import javax.print.attribute.standard.PrinterName;

public class HiloServidor extends Thread {

    public static PantallaNivelUno pantallaJuego;
    private DatagramSocket conexion;

    private boolean fin = false;

    private int conectados = 0;
    private int maximo = 2;
    private PersonajeRed[] jugadores;
    private boolean iniciaJuego;
    public boolean termina;


    public HiloServidor() {
        jugadores = new PersonajeRed[maximo];

        try {
            // Puerto 38888
            conexion = new DatagramSocket(38888);
            System.out.println("Iniciado server");
        } catch (SocketException e) {
            fin = true;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!fin) {
            byte[] datos = new byte[1024];
            DatagramPacket dp = new DatagramPacket(datos, datos.length);
            try {
                conexion.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {

            }
        }
    }

    //envia mensaje al servidor
    public void enviaerMensaje(String msg, InetAddress ip, int puerto) {
        byte[] data = msg.getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);
        try {
            conexion.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //procesa el mensaje que le envia el cliente
    private void procesarMensaje(DatagramPacket dp) {

        // Se recibe el evento en modo string
        String msg = new String(dp.getData()).trim();
        // El evento en componentes []
        String[] mensajeEvento = msg.split("#");
        // nombrevento#data1#data2

        int nroJugador = -1;
        for (int i = 0; i < jugadores.length; i++) {
            if(jugadores[i] == null) continue;
            if (dp.getPort() == jugadores[i].puerto && dp.getAddress().equals(jugadores[i].ip)) {
                nroJugador = i; // 0 o 1
                break;
            }
        }


        switch (mensajeEvento[0]) {
            case "Conexion":
                if (conectados < maximo) {
                    jugadores[conectados] = new PersonajeRed(dp.getAddress(), dp.getPort());
                    enviarMensaje("OK#" + conectados, jugadores[conectados].ip, jugadores[conectados].puerto);
                    conectados++;
                    System.out.println("Jugador: " + dp.getAddress() + " " + dp.getPort());
                    if (conectados == maximo) {
                        enviarMensaje("Empieza");
                        iniciaJuego = true;
                        return;
                    }
                    return;
                }
                break;
            case "mover": {

                Boolean arriba = mensajeEvento[1].equals("true");
                Boolean abajo = mensajeEvento[2].equals("true");
                Boolean izq = mensajeEvento[3].equals("true");
                Boolean derecha = mensajeEvento[4].equals("true");
                if(nroJugador == 0 ) {
                    if ((arriba) && (pantallaJuego.player1.b2body.getLinearVelocity().y <= 2 && pantallaJuego.player1.b2body.getLinearVelocity().y >= 0) && !pantallaJuego.player1.getState().equals(Ninja.State.SALTANDO)) {
                        pantallaJuego.player1.b2body.applyLinearImpulse(new Vector2(0, 2f), pantallaJuego.player1.b2body.getWorldCenter(), true);
                    }
                    if ((derecha) && (pantallaJuego.player1.b2body.getLinearVelocity().x <= 4)) {
                        pantallaJuego.player1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), pantallaJuego.player1.b2body.getWorldCenter(), true);
                    }
                    if ((izq) && (pantallaJuego.player1.b2body.getLinearVelocity().x >= -7)) {
                        pantallaJuego.player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), pantallaJuego.player1.b2body.getWorldCenter(), true);
                    }

                } else {
                    if ((arriba) && (pantallaJuego.player2.b2body.getLinearVelocity().y <= 2 && pantallaJuego.player2.b2body.getLinearVelocity().y >= 0) && !pantallaJuego.player2.getState().equals(Ninja.State.SALTANDO)) {
                        pantallaJuego.player2.b2body.applyLinearImpulse(new Vector2(0, 2f), pantallaJuego.player2.b2body.getWorldCenter(), true);
                    }
                    if ((derecha) && (pantallaJuego.player2.b2body.getLinearVelocity().x <= 4)) {
                        pantallaJuego.player2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), pantallaJuego.player2.b2body.getWorldCenter(), true);
                    }
                    if ((izq) && (pantallaJuego.player2.b2body.getLinearVelocity().x >= -7)) {
                        pantallaJuego.player2.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), pantallaJuego.player2.b2body.getWorldCenter(), true);
                    }
                }


                break;
            }
            default: {


            }
        }


    }

    public void enviarMensaje(String msg, InetAddress ipDestino, int puerto) {
        byte[] mensaje = msg.getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipDestino, puerto);
            conexion.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String msg) {//Manda mensaje a todos los clientes
       
        byte[] mensaje = msg.getBytes();
        try {
            for (int i = 0; i < jugadores.length; i++) {
                if(jugadores[i] == null) continue;
                DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, jugadores[i].ip, jugadores[i].puerto);
                conexion.send(dp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fin(){
        termina = true;
        enviarMensaje("termina");
        jugadores = new PersonajeRed[2];
        conectados = 0;
    }
    public boolean isFin(){
        return fin;
    }
}
