package com.grupo.personaje;

public class Guerrero extends Personaje {
    private static final long serialVersionUID = 1L;
    private int fuerza;

    public Guerrero(int fuerza, String nombre, TipoPersonaje tipo, int nivel, int puntosDeVida) {
        super(nombre, tipo, nivel, puntosDeVida);
        this.fuerza = fuerza;
    }
    
    //Gett y Sett
    public int getFuerza() {return fuerza;}

    public void setFuerza(int fuerza) {
        if (fuerza < 0) {
            throw new IllegalArgumentException("La fuerza asignada no ser negativa");
        }
        this.fuerza = fuerza;
    }
    
    @Override
    public void atacar() {
        if(fuerza>60)
        {
           System.out.println("Ataque de guerrero con espada!"); 
        }
        else
        {
            System.out.println("Se requiere mas fuerza para atacar");
        }
        
    }  
    
    @Override
    public void mostrarDetallePersonaje()
    {
        System.out.println("Guerrero: " + this.toString());
    }
}
