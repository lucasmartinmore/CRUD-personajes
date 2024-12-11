package com.grupo.interfaces;

/**
 *
 * @author Lucas
 */
@FunctionalInterface
public interface FiltroPersonaje <T> {
    boolean filtrar(T personaje);  // Método para aplicar el filtro
}
