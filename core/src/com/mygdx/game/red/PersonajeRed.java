package com.mygdx.game.red;

import java.net.InetAddress;

public class PersonajeRed {

    public float x,y;
    public InetAddress ip;
    public int puerto;


    public PersonajeRed(InetAddress ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
}
