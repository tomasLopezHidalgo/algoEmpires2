package vista;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuVista extends BorderPane {
	
	Scene escenaSiguiente;

    public MenuVista(Stage stagePrincipal) throws Exception{
    	//-------------------------------------------------------
        this.prepararEscenaSiguiente(stagePrincipal);
    	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    	//--------------- Imagen de fondo -----------------------
        Image background = new Image("Imagenes/ElementosMenu/menuBackground3.png");
        ImageView backgroundVista = new ImageView(background);
        backgroundVista.setFitWidth(1280);
        backgroundVista.setFitHeight(800);
        
    	StackPane panel = new StackPane(backgroundVista);
        setCenter(panel);
        //--------------- Setup Botones -------------------------
        VBox botones = new VBox(40);
        
        Image imagenComenzar = new Image("Imagenes/ElementosMenu/Botones/comenzar3.png");
        ImageView comenzar = new ImageView(imagenComenzar);
        comenzar.setFitWidth(300);
        comenzar.setFitHeight(37);
        
        BotonPersonalizado elBotonComenzar = new BotonPersonalizado(comenzar);
        elBotonComenzar.setOnMousePressed(event -> stagePrincipal.setScene(escenaSiguiente));

        Image imagenSalir = new Image("Imagenes/ElementosMenu/Botones/salir3.png");
        ImageView salir = new ImageView(imagenSalir);
        salir.setFitWidth(300);
        salir.setFitHeight(37);
        
        BotonPersonalizado elBotonSalir = new BotonPersonalizado(salir);
        elBotonSalir.setOnMousePressed(event ->  stagePrincipal.close());
    	
        botones.getChildren().addAll(elBotonComenzar, elBotonSalir);
        panel.getChildren().add(botones);
        botones.setTranslateY(primaryScreenBounds.getHeight()*0.6);
    }

	private void prepararEscenaSiguiente(Stage stagePrincipal) throws Exception {
        this.escenaSiguiente = new Scene(new ConfiguracionVista(stagePrincipal));
	}

}
