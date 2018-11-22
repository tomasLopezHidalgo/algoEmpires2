package modelo;

import modelo.excepciones.CasillaOcupadaError;
import modelo.excepciones.Excepcion;

public class Plaza extends Edificio {
	
	public static final int TAMANIO_LADO = 2;
	
	public Plaza(Area areaAOcupar) {
		super(areaAOcupar);
		vida = 450;
		vidaMaxima = vida;
		costo = 100;
		tiempoDeConstruccion = 3;
		cantidadDeCuracion = 25;
	}
	
	public Plaza(Area areaAOcupar, boolean yaConstruida) {
		super(areaAOcupar);
		vida = 450;
		vidaMaxima = vida;
		costo = 100;
		
		tiempoDeConstruccion = 3;
		if(yaConstruida) {
			tiempoDeConstruccion = 0;
		}
		
		cantidadDeCuracion = 25;
	}

	public Aldeano crearAldeano(Area unEspacio) {
		siYaJugoElTurnoError();
		
		turnoJugado = true;
		Aldeano unAldeano = new Aldeano(unEspacio);
		return unAldeano;
	}
}
