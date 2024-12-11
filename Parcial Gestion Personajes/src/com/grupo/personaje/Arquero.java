package com.grupo.personaje;


public class Arquero extends Personaje{
    private static final long serialVersionUID = 1L;
    private int presicion;

    public Arquero(int presicion, String nombre, TipoPersonaje tipo, int nivel, int puntosDeVida) {
        super(nombre, tipo, nivel, puntosDeVida);
        this.presicion = presicion;
    }
    
    //Gett y Sett
    public int getPresicion() {return presicion;}

    public void setPresicion(int presicion) {
        this.presicion = presicion;
    }
    
    @Override
    public void atacar() {
        if(presicion>60)
        {
            System.out.println("Ataque de arquero con flecha!");
        }
        else
        {
            System.out.println("Se requiere de mas presicion para atacar");
        }
        
    }
    
    @Override
    public void mostrarDetallePersonaje()
    {
        System.out.println("Arquero: " + this.toString());
    }
}
