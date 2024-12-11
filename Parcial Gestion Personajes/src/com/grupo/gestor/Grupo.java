package com.grupo.gestor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import com.grupo.interfaces.FiltroPersonaje;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Grupo<T extends Serializable> implements Iterable<T> {

    private List<T> elementos = new ArrayList<>();

    public void agregarElemento(T elemento) {
        elementos.add(elemento);
    }
    
    // Método para consultar un elemento por su índice, devuelve un Optional que puede estar vacío si no existe el elemento
    public Optional<T> consultarElemento(int index) {
        if (index < 0 || index >= elementos.size()) {
            return Optional.empty();
        }
        return Optional.of(elementos.get(index));
    }
    
     // Método que devuelve una nueva lista con todos los elementos del grupo
    public List<T> obtenerElementos() {
        return new ArrayList<>(elementos);
    }
    
    // Método para actualizar un elemento en el grupo si el índice es válido
    public void actualizarElemento(int index, T nuevoElemento) {
        if (index >= 0 && index < elementos.size()) {
            elementos.set(index, nuevoElemento);
        }
    }
    
    // Método para eliminar un elemento del grupo basado en el criterio que se pase
    public boolean eliminarElementoPorCriterio(Predicate<T> criterio) {
        Optional<T> itemAEliminar = elementos.stream().filter(criterio).findFirst();
        itemAEliminar.ifPresent(elementos::remove); // Elimina el primer elemento que cumple con el criterio
        return itemAEliminar.isPresent(); // Retorna true si se eliminó un elemento
    }
    
    public void eliminarElemento(T elemento) {
        elementos.remove(elemento);
    }

    // Método para filtrar los elementos del grupo según un filtro proporcionado (uso de interfaz funcional)
    public List<T> filtrarElementos(FiltroPersonaje<T> filtro) {
        List<T> filtrados = new ArrayList<>();
        for (T item : elementos) {
            if (filtro.filtrar(item)) {//Si el filtro pasa, agrego
                filtrados.add(item);
            }
        }
        return filtrados;
    }

    // Método que permite iterar sobre los elementos del grupo
    @Override
    public Iterator<T> iterator() {
        return elementos.iterator();
    }
    
     // Método para ordenar los elementos de acuerdo con un comparador proporcionado
    public void ordenarPor(Comparator<? super T> comparator) {
        elementos.sort(comparator);
    }

    // Método para guardar los elementos en un archivo binario
    public void guardarEnArchivo(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(elementos);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //Método para cargar elementos desde un archivo binario
    public ArrayList<T> cargarDesdeArchivo(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            // Leer directamente el objeto y verificar el tipo
            Object elementosCargados = ois.readObject();
            if (elementosCargados instanceof ArrayList<?>) {
                elementos = (ArrayList<T>) elementosCargados; // Actualizar la lista de la clase
            } else {
                elementos = new ArrayList<>(); // Vaciar la lista si el contenido no es válido
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>(elementos);
    }
    
    // Método para guardar los elementos en un archivo de texto
    public void guardarEnArchivoTXT(String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (T elemento : elementos) {
                writer.write(elemento.toString());
                writer.newLine();
            }
        }
    }
    // Método para guardar los elementos en un archivo JSON utilizando la biblioteca Gson
    public void guardarEnArchivoJSON(String archivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(elementos, writer);
        }
    }
    
    // Método para exportar los elementos a un archivo CSV
    public void exportarCSV(String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (T elemento : elementos) {
                writer.println(elemento.toString());
            }
        }
    }
}
