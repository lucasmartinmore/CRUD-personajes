package com.grupo.controlador;

import com.grupo.gestor.Grupo;
import com.grupo.interfaces.FiltroPersonaje;
import com.grupo.personaje.Arquero;
import com.grupo.personaje.Guerrero;
import com.grupo.personaje.Mago;
import com.grupo.personaje.Personaje;
import com.grupo.personaje.TipoPersonaje;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;
import java.util.stream.Collectors;
import static javafx.application.Application.launch;
import javafx.scene.control.cell.ComboBoxTableCell;

public class Controller extends Application {

    private final Grupo<Personaje> gestorPersonaje = new Grupo<>();
    private final ObservableList<Personaje> listaPersonajes = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        mostrarVentanaPrincipal(primaryStage);
    }

    public void mostrarVentanaPrincipal(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Personajes");

        try {
            // Cargar los personajes desde el archivo
            ArrayList<Personaje> personajesCargados = gestorPersonaje.cargarDesdeArchivo("grupo.dat");

            // Actualizar la lista observable
            listaPersonajes.setAll(personajesCargados);

        } catch (Exception e) {
            // Mostrar alerta en caso de error
            mostrarAlerta("Error al cargar datos", "No se pudo cargar el archivo: " + e.getMessage());
        }

        //configuración de la ventana principal
        //Configuración de la tabla (TableView) para mostrar los personajes
        TableView<Personaje> tableView = new TableView<>(listaPersonajes);
        tableView.setEditable(true);

        // Config columna de nombre
        TableColumn<Personaje, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nombreCol.setOnEditCommit(event -> event.getRowValue().setNombre(event.getNewValue()));

        //Config de columna tipo de personaje (comboBox para seleccionar entre tipos)
        TableColumn<Personaje, TipoPersonaje> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tipoCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(TipoPersonaje.values())));

        // Actualizo los valores de todos los campos
        tipoCol.setOnEditCommit(event -> event.getRowValue().setTipo(event.getNewValue())); // Actualizar el valor del tipo

        TableColumn<Personaje, Integer> nivelCol = new TableColumn<>("Nivel");
        nivelCol.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        nivelCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nivelCol.setOnEditCommit(event -> event.getRowValue().setNivel(event.getNewValue()));

        TableColumn<Personaje, Integer> pvCol = new TableColumn<>("Puntos vida");
        pvCol.setCellValueFactory(new PropertyValueFactory<>("puntosDeVida"));
        pvCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        pvCol.setOnEditCommit(event -> event.getRowValue().setPuntosDeVida(event.getNewValue()));

        // Agregar columnas al TableView
        tableView.getColumns().addAll(nombreCol, tipoCol, nivelCol, pvCol);

        //Agrego estilo a mi TableView
        tableView.setStyle("-fx-background-color: #f4f4f4;" + "-fx-control-inner-background: #ffffff;" + "-fx-background-insets: 5;" + "-fx-background-radius: 10;" + "-fx-padding: 10;");
        tableView.getColumns().forEach(column -> {
            column.setStyle("-fx-background-color: #e0e0e0;" + "-fx-text-fill: #333333;" + "-fx-font-weight: bold;");
        });
        //Estilo para filas de la tabla al pasar por encima
        tableView.setRowFactory(tv -> {
            TableRow<Personaje> row = new TableRow<>();
            row.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent transparent #e0e0e0 transparent;");
            row.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    row.setStyle("-fx-background-color: #f0f0f0;" + "-fx-border-color: transparent transparent #c0c0c0 transparent;");
                } else {
                    row.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent transparent #e0e0e0 transparent;");
                }
            });
            return row;
        });

        // Botones de mi CRUD personaje
        Button btnAgregar = new Button("Agregar");
        Button btnEliminar = new Button("Eliminar");
        Button btnGuardar = new Button("Guardar");
        Button btnSalir = new Button("Salir");
        Button btnReportetxt = new Button("Generar TXT");
        Button btnGuardarJson = new Button("Guardar JSON");
        Button btnFiltroPersonaje = new Button("Filtrar");
        Button btnOrdenarPorNivel = new Button("Ordenar Nivel");
        Button btnOrdenarPorPV = new Button("Ordenar PV");

        //Estilo de botones
        btnAgregar.setStyle("-fx-background-color: #4CAF50;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnEliminar.setStyle("-fx-background-color: #f44336;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnFiltroPersonaje.setStyle("-fx-background-color: #EB5B00;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnReportetxt.setStyle("-fx-background-color: #4793AF;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnGuardarJson.setStyle("-fx-background-color: #944E63;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnSalir.setStyle("-fx-background-color: #186F65;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnGuardar.setStyle("-fx-background-color: #CECE5A;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnOrdenarPorNivel.setStyle("-fx-background-color: #6495ED;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");
        btnOrdenarPorPV.setStyle("-fx-background-color: #FFA07A;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-border-color: #45a049;" + "-fx-border-width: 2px;" + "-fx-border-radius: 5px;" + "-fx-background-radius: 5px;");

        //Eventos de botones (ej. agregar un personaje, eliminar, guardar, filtrar)
        btnAgregar.setOnAction(e -> mostrarVentanaAgregar());
        btnEliminar.setOnAction(e -> eliminarPersonaje(tableView));
        btnGuardar.setOnAction(e -> guardarGrupo());
        btnSalir.setOnAction(e -> Platform.exit());
        btnReportetxt.setOnAction(e -> guardarEnTxt());
        btnGuardarJson.setOnAction(e -> guardarEnJson());
        btnFiltroPersonaje.setOnAction(e -> mostrarVentanaFiltrarPersonaje());
        btnOrdenarPorNivel.setOnAction(e -> {
            gestorPersonaje.ordenarPor(Comparator.comparing(Personaje::getNivel)); // Ordena la lista
            listaPersonajes.setAll(gestorPersonaje.obtenerElementos());
        });//Actualiza la tabla con la lista ordenada
        btnOrdenarPorPV.setOnAction(e -> {
            gestorPersonaje.ordenarPor(Comparator.comparing(Personaje::getPuntosDeVida)); // Ordena la lista
            listaPersonajes.setAll(gestorPersonaje.obtenerElementos()); // Actualiza la tabla con la lista ordenada
        });

        // Layout principal distrib de botones y table
        VBox botones = new VBox(10, btnAgregar, btnEliminar, btnFiltroPersonaje, btnOrdenarPorNivel, btnOrdenarPorPV, btnGuardarJson, btnReportetxt, btnGuardar, btnSalir);
        botones.setPrefWidth(100);

        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setRight(botones);

        // Configurar escena y muestro ventana principal
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarVentanaFiltrarPersonaje() {
        Stage ventanaFiltro = new Stage();
        ventanaFiltro.setTitle("Filtrar Personajes por Tipo");

        // Campos personaje
        ComboBox<TipoPersonaje> comboTipo = new ComboBox<>();
        Button btnFiltrar = new Button("Filtrar");

        // Agregar valores al ComboBox
        comboTipo.getItems().addAll(TipoPersonaje.values());

        // Layout de seleccion
        VBox layout = new VBox(10,
                new Label("Seleccione el tipo de personaje:"), comboTipo,
                btnFiltrar
        );

        layout.setPrefSize(300, 150);

        btnFiltrar.setOnAction(e -> {
            try {
                TipoPersonaje tipoSeleccionado = comboTipo.getValue();

                if (tipoSeleccionado == null) {
                    mostrarAlerta("Error", "Debe seleccionar un tipo de personaje para filtrar.");
                    return;
                }

                // Defino el filtro utilizando la interfaz funcional
                FiltroPersonaje<Personaje> filtroPorTipo = personaje -> personaje.getTipo() == tipoSeleccionado;

                // Aplico el filtro a la lista de personajes
                List<Personaje> filtrados = listaPersonajes.stream()
                        .filter(filtroPorTipo::filtrar) // método de la interfaz funcional
                        .collect(Collectors.toList());

                if (!filtrados.isEmpty()) {
                    mostrarListaFiltrada(filtrados);
                } else {
                    mostrarAlerta("Sin Resultados", "No se encontraron personajes del tipo " + tipoSeleccionado);
                }

                ventanaFiltro.close();
            } catch (Exception ex) {
                mostrarAlerta("Error", "Ocurrió un error inesperado: " + ex.getMessage());
            }
        });

        // Configurar escena
        Scene scene = new Scene(layout);
        ventanaFiltro.setScene(scene);
        ventanaFiltro.show();
    }

    private void mostrarListaFiltrada(List<Personaje> filtrados) {
        Stage ventanaFiltrados = new Stage();
        ventanaFiltrados.setTitle("Productos Filtrados");

        TableView<Personaje> tableFiltrados = new TableView<>(FXCollections.observableArrayList(filtrados));

        // Configurar columnas filtradas
        TableColumn<Personaje, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Personaje, TipoPersonaje> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Personaje, Integer> nivelCol = new TableColumn<>("Nivel");
        nivelCol.setCellValueFactory(new PropertyValueFactory<>("nivel"));

        TableColumn<Personaje, Integer> pvCol = new TableColumn<>("Puntos vida");
        pvCol.setCellValueFactory(new PropertyValueFactory<>("puntosDeVida"));

        tableFiltrados.getColumns().addAll(nombreCol, tipoCol, nivelCol, pvCol);

        Scene scene = new Scene(new BorderPane(tableFiltrados), 600, 400);
        ventanaFiltrados.setScene(scene);
        ventanaFiltrados.show();
    }

    // Método para agregar un personaje dependiendo el tipo seleccionado
    private void mostrarVentanaAgregar() {
        Stage ventanaAgregar = new Stage();
        ventanaAgregar.setTitle("Agregar Personaje");

        // Campos comunes para todos los personajes
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        TextField txtNivel = new TextField();
        txtNivel.setPromptText("Nivel");
        TextField txtPV = new TextField();
        txtPV.setPromptText("Puntos de Vida");
        //combobox para seleccionar el tipo 
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Guerrero", "Mago", "Arquero");

        VBox especifico = new VBox(10);
        especifico.setSpacing(10);

        // Layout principal y agrego estilo
        VBox layout = new VBox(10,
                new Label("Nombre:"), txtNombre,
                new Label("Nivel:"), txtNivel,
                new Label("Puntos de Vida:"), txtPV,
                new Label("Tipo de Personaje:"), comboTipo,
                especifico
        );
        layout.setSpacing(15);
        layout.setPadding(new javafx.geometry.Insets(10));  // Añadimos padding a la ventana
        layout.setStyle("-fx-background-color: #f0f0f0;");  // Estilo adicional para el fondo
        layout.setMaxWidth(400);  // Ancho máximo para el layout

        // Establecer el tamaño mínimo de la ventana
        ventanaAgregar.setMinWidth(400);
        ventanaAgregar.setMinHeight(300);

        // Dependiendo del tipo seleccionado...
        comboTipo.setOnAction(e -> {
            especifico.getChildren().clear();  // Limpiar los campos anteriores cuando cambie el tipo
            String tipoSeleccionado = comboTipo.getValue();
            TextField txtAtributoEspecifico = new TextField();

            //Atributos especifico de cada clase
            if ("Guerrero".equals(tipoSeleccionado)) {
                txtAtributoEspecifico.setPromptText("Fuerza");
                especifico.getChildren().addAll(new Label("Fuerza:"), txtAtributoEspecifico);
            } else if ("Mago".equals(tipoSeleccionado)) {
                txtAtributoEspecifico.setPromptText("Maná");
                especifico.getChildren().addAll(new Label("Maná:"), txtAtributoEspecifico);
            } else if ("Arquero".equals(tipoSeleccionado)) {
                txtAtributoEspecifico.setPromptText("Presición");
                especifico.getChildren().addAll(new Label("Presición:"), txtAtributoEspecifico);
            }

            // Guardar el personaje ingresado
            Button btnGuardar = new Button("Guardar");
            btnGuardar.setOnAction(event -> {
                try {
                    //valores ingresados
                    String nombre = txtNombre.getText();
                    int nivel = Integer.parseInt(txtNivel.getText());
                    int puntosVida = Integer.parseInt(txtPV.getText());
                    // Determinar el tipo seleccionado
                    TipoPersonaje tipo;

                    switch (tipoSeleccionado) {
                        case "Guerrero" ->
                            tipo = TipoPersonaje.GUERRERO;
                        case "Mago" ->
                            tipo = TipoPersonaje.MAGO;
                        case "Arquero" ->
                            tipo = TipoPersonaje.ARQUERO;
                        default ->
                            throw new IllegalArgumentException("Tipo de personaje no válido.");
                    }

                    // Crear el personaje y asigno
                    Personaje nuevoPersonaje;

                    if (tipo == TipoPersonaje.GUERRERO) {
                        int fuerza = Integer.parseInt(txtAtributoEspecifico.getText());
                        nuevoPersonaje = new Guerrero(fuerza, nombre, tipo, nivel, puntosVida);
                    } else if (tipo == TipoPersonaje.MAGO) {
                        int mana = Integer.parseInt(txtAtributoEspecifico.getText());
                        nuevoPersonaje = new Mago(mana, nombre, tipo, nivel, puntosVida);
                    } else {
                        int presicion = Integer.parseInt(txtAtributoEspecifico.getText());
                        nuevoPersonaje = new Arquero(presicion, nombre, tipo, nivel, puntosVida);
                    }

                    // Agregar a la lista y cerrar la ventana
                    listaPersonajes.add(nuevoPersonaje);
                    gestorPersonaje.agregarElemento(nuevoPersonaje);
                    ventanaAgregar.close();

                } catch (Exception ex) {
                    mostrarAlerta("Error", "Error al agregar el personaje: " + ex.getMessage());
                }
            });

            // Añadir el botón al layout
            especifico.getChildren().add(btnGuardar);

            // Asegurarse de que la ventana se redimensione automáticamente
            ventanaAgregar.sizeToScene();
        });

        Scene scene = new Scene(layout);
        ventanaAgregar.setScene(scene);
        ventanaAgregar.show();
    }

    private void eliminarPersonaje(TableView<Personaje> tableView) {
        Personaje seleccionado = tableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaPersonajes.remove(seleccionado);
            gestorPersonaje.eliminarElementoPorCriterio(p -> p.toString().equals(seleccionado.toString()));
        } else {
            mostrarAlerta("Error", "Debe seleccionar un personaje para eliminar.");
        }
    }

    private void guardarGrupo() {
        try {
            gestorPersonaje.guardarEnArchivo("grupo.dat");
            mostrarAlerta("Guardar Personajes", "Grupo personajes guardado exitosamente.");
        } catch (Exception e) {
            mostrarAlerta("Error al guardar", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void guardarEnTxt() {
        try {
            gestorPersonaje.guardarEnArchivoTXT("grupo.txt");
            mostrarAlerta("Guardar TXT", "Grupo personaje guardado en archivo TXT exitosamente.");
        } catch (Exception e) {
            mostrarAlerta("Error al guardar TXT", e.getMessage());
        }
    }

    private void guardarEnJson() {
        try {
            gestorPersonaje.guardarEnArchivoJSON("grupo.json");
            mostrarAlerta("Guardar JSON", "Grupo personaje guardado en archivo JSON exitosamente.");
        } catch (Exception e) {
            mostrarAlerta("Error al guardar JSON", e.getMessage());
        }
    }

}
