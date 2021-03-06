package controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modelo.ArmaDeAsedio;
import vista.ArmaDeAsedioVista;
import vista.PiezaVista;

public class AccionarArmaDeAsedioHandler implements EventHandler<ActionEvent> {

	private ArmaDeAsedioVista arma;

	public AccionarArmaDeAsedioHandler(PiezaVista arma){
		this.arma = (ArmaDeAsedioVista)arma;
	}

	@Override
	public void handle(ActionEvent event) {
		//TODO lo podemos accionar infinitas veces por turno? (depende del modelo)
		((ArmaDeAsedio)(arma.modelo())).accionar();
	}
	
}
