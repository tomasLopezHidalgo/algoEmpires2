package controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modelo.Aldeano;
import modelo.Area;
import modelo.Cuartel;
import modelo.Pieza;
import vista.CuartelVista;
import vista.MapaVista;

public class ConstruirCuartelHandler implements EventHandler<ActionEvent>{
	
	private MapaVista unMapa;
	private Aldeano unAldeano;

	public ConstruirCuartelHandler(MapaVista unMapa, Pieza unAldeano){
		this.unMapa = unMapa;
		this.unAldeano = (Aldeano)unAldeano;
	}

	@Override
	public void handle(ActionEvent event) {
		int x0 = unMapa.casillaSeleccionada().modelo().ejeX();
		int y0 = unMapa.casillaSeleccionada().modelo().ejeY();

		Area areaDeConstruccion = unMapa.obtenerTablero().definirArea(x0, y0, x0+1, y0+1);
		Cuartel cuartel = unAldeano.crearCuartel(areaDeConstruccion);
		if(cuartel != null) {
			CuartelVista cuartelVisu = new CuartelVista(x0,y0,cuartel,unMapa);
			unMapa.aniadirPieza(cuartelVisu);
		}
	}
	
}