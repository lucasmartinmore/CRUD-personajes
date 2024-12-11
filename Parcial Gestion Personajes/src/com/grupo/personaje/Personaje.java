package com.grupo.personaje;

import java.io.Serializable;

public abstract class Personaje implements Comparable<Personaje>, Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private TipoPersonaje tipo;
    private int nivel;
    private int puntosDeVida;

    public Personaje(String nombre, TipoPersonaje tipo, int nivel, int puntosDeVida) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = 1;
        this.puntosDeVida = 100;
    }

    // Método abstracto específico
    public abstract void atacar();

    public abstract void mostrarDetallePersonaje();

    //Gett
    public String getNombre() {
        return nombre;
    }

    public TipoPersonaje getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    //Sett
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setTipo(TipoPersonaje tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de personaje no puede ser nulo");
        }
        this.tipo = tipo;
    }

    public void setNivel(int nivel) {
        if (nivel < 1 || nivel > 100) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y 100");
        }
        this.nivel = nivel;
    }

    public void setPuntosDeVida(int puntosDeVida) {
        if (puntosDeVida < 0) {
            throw new IllegalArgumentException("Los puntos de vida no pueden ser negativos");
        }
        this.puntosDeVida = puntosDeVida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personaje{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", nivel=").append(nivel);
        sb.append(", puntosDeVida=").append(puntosDeVida);
        sb.append(", tipo=").append(tipo);
        sb.append('}');
        return sb.toString();
    }

    // Implementación de Comparable
    @Override
    public int compareTo(Personaje otroPersonaje) {
        return Integer.compare(this.nivel, otroPersonaje.nivel);
    }

}
