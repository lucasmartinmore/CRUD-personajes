package com.grupo.personaje;


public class Mago extends Personaje{
    private static final long serialVersionUID = 1L;
    private int mana;

    public Mago(int mana, String nombre, TipoPersonaje tipo, int nivel, int puntosDeVida) {
        super(nombre, tipo, nivel, puntosDeVida);
        this.mana = mana;
    }
    
    //Gett y Sett
    public int getMana() {return mana;}

    public void setMana(int mana) {
    if (mana < 0) {
        throw new IllegalArgumentException("Mana asignada no ser negativa");
    }
    this.mana = mana;
    }
    
    @Override
    public void atacar(){
        if(mana>60)
        System.out.println("Ataque mágico de hechicero!");
        else
        {
            System.out.println("Necesitas recargar mana");
        }
    }    
    
    @Override
    public void mostrarDetallePersonaje()
    {
        System.out.println("Mago: " + this.toString());
    }
}

