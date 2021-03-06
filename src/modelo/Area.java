package modelo;

import java.util.List;

import modelo.excepciones.CasillaOcupadaError;
import modelo.excepciones.NullPointerCasillaError;

public class Area {

	private List<Casilla> casillasDelArea;
	private int xInicial;
	private int xFinal;
	private int yInicial;
	private int yFinal;

    //TODO: Refactoring.
    private int distanciaEntre(Casilla casillaA, Casilla casillaB) {

        return casillaA.calcularDistanciaA(casillaB);
    }

	public Area(List<Casilla> casillas, int xInicial, int yInicial, int xFinal, int yFinal){
		this.xInicial = xInicial;
		this.xFinal = xFinal;
		this.yInicial = yInicial;
		this.yFinal = yFinal;
		casillasDelArea = casillas;
	}
	
	public int obtenerCantidadDeCasillas(){
		return casillasDelArea.size();
	}
	
	public List<Casilla> obtenerCasillas() {
		return casillasDelArea;
	}
	
	public void liberar() {
		for (int i = 0; i < casillasDelArea.size(); i++)
			casillasDelArea.get(i).liberar();
	}
	
	public void ocupar() {

        if(this.estaLibre()) {
            for (int i = 0; i < casillasDelArea.size(); i++)
                casillasDelArea.get(i).ocupar();
        }else{
            throw new CasillaOcupadaError();

        }
	}

	public boolean estaLibre() {

		for (int i = 0; i < casillasDelArea.size(); i++) {
            if (casillasDelArea.get(i).estaOcupada()) {
                return false;
            }
        }
        return true;
	}
	
	public void agregarCasilla(Casilla unaCasilla) {

		if(unaCasilla == null)
			throw new NullPointerCasillaError();

		casillasDelArea.add(unaCasilla);
	}

	//TODO: Refactoring .
	public int distanciaMinimaA(Casilla unaCasilla) {

		int minimaDistancia = Integer.MAX_VALUE;
		int distanciaNueva;
		for (int i = 0; i < casillasDelArea.size(); i++) {
			Casilla casillaActual = casillasDelArea.get(i);
			
			distanciaNueva = distanciaEntre(casillaActual, unaCasilla);
			if(distanciaNueva < minimaDistancia)
				minimaDistancia = distanciaNueva;
		}
		return minimaDistancia;
	}

	public int x0(){
		return xInicial;
	}
	
	public int y0(){
		return yInicial;
	}
	
	public int x1(){
		return xFinal;
	}
	
	public int y1(){
		return yFinal;
	}
	
}
