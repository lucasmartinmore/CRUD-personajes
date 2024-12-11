package com.grupo.controlador;

/**
 *
 * @author Lucas
 */
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {
     @Override
    public void start(Stage primaryStage) {
        // Crear instancia del controlador GUIController
        Controller controller = new Controller();
        
        // Configurar y mostrar la ventana principal
        controller.mostrarVentanaPrincipal(primaryStage);
    }

    public static void main(String[] args) {
        // Lanzar la aplicación
        launch(args);
    }
}
