package modelo;

import java.util.ArrayList;
import java.util.List;

import modelo.excepciones.CasillaInvalidaError;
import modelo.excepciones.OroInsuficienteError;
import modelo.excepciones.PoblacionLimiteSuperadaError;

public class Jugador {

    static final int POBLACION_MAX = 50;

    //------------------------------------------
    private Castillo elCastillo;
    private List<Aldeano> losAldeanos = new ArrayList<>();
    private List<Edificio> losEdificios = new ArrayList<>();
    private List<Unidad> losSoldados = new ArrayList<>();
    //------------------------------------------
    private String nombreJugador;
    private int cantidadDeOro;
    private int poblacion;

    /*          Constructor         */
    public Jugador(String unNombre){
    	this.nombreJugador = unNombre;
    	this.cantidadDeOro = 100;
    	this.poblacion = 0;
    }

    private void finalizarTurnoDePiezas() {
        for(int i = 0; i < losAldeanos.size(); i++) {
            Aldeano aldeanoActual = losAldeanos.get(i);
            cantidadDeOro += aldeanoActual.generarOro();
            aldeanoActual.nuevoTurno();
        }
        
        for(int i = 0; i < losEdificios.size(); i++) {
            Edificio edificioActual = losEdificios.get(i);
            edificioActual.nuevoTurno();
        }
        
        for(int i = 0; i < losSoldados.size(); i++) {
            Unidad soldadoActual = losSoldados.get(i);
            soldadoActual.nuevoTurno();
        }
        
        elCastillo.nuevoTurno();
    }
    
    public int hpCastillo() {
    	//TODO poner un metodo, no acceder a variable?
    	return elCastillo.vida;
    }
    
    public int cantidadSoldados() {
    	return losSoldados.size();
    }
    
    public int cantidadAldeanos() {
    	return losAldeanos.size();
    }
    
    public int cantidadEdificios() {
    	return losEdificios.size();
    }

    public int getPoblacion(){
        return this.poblacion;
    }

    public void finalizarTurno(){
        finalizarTurnoDePiezas();
    }
    
    public String obtenerNombre() {
    	return nombreJugador;
    }

    public int obtenerOro(){
        return this.cantidadDeOro;
    }
    
    //TODO tom explicame para que es esto
	public void setListener(Juego unJuego){
	    elCastillo.setCastilloListener(unJuego);    
	}
    
    //TODO hace falta eso o con el Listener ya estamos?
    public boolean castilloFueDestruido(){
    	return (elCastillo.estaDestruida());
    }
    
    public void cobrar(int monto) {
    	if(this.cantidadDeOro < monto) {
    		throw new OroInsuficienteError();
    	}	
    	cantidadDeOro -= monto;
    }
    
    
	//----------------------------------------------------------------------------
	//---------------------      Manejo de Piezas     ----------------------------
    
    public void agregar(Castillo unCastillo) {
    	//borra el castillo anterior si habia y asigna el nuevo
    	if(elCastillo != null) {
    		elCastillo.recibirDanio(1000);
    	}
    	elCastillo = unCastillo;
    }
    
    public void agregar(Unidad soldado) {
		if(soldado instanceof Aldeano) {
			agregar((Aldeano) soldado);
		}
		else {
	    	int poblacionLibre = POBLACION_MAX - poblacion;
	    	
	    	if(poblacionLibre < 1) {
	            throw new PoblacionLimiteSuperadaError();
	    	}
	    	
			losSoldados.add(soldado);
			actualizarPoblacion();
		}
    }
    
    public void agregar(Edificio edificio) {
    	losEdificios.add(edificio);
    }

    public void agregar(Aldeano aldeano){
    	int poblacionLibre = POBLACION_MAX - poblacion;
    	
    	if(poblacionLibre < 1) {
            throw new PoblacionLimiteSuperadaError();
    	}
    	
		losAldeanos.add(aldeano);
		actualizarPoblacion();
    }
    
    public void remover(Aldeano aldeano){
		losAldeanos.remove(aldeano);
		actualizarPoblacion();
    }
    
    public void remover(Edificio edificio){
		losEdificios.remove(edificio);
    }
    
    public void remover(Unidad soldado){
    	if(soldado instanceof Aldeano) {
    		remover((Aldeano)soldado);
    	}
    	else {
    		losSoldados.remove(soldado);
    		actualizarPoblacion();
    	}
    }
    
	public boolean contieneA(Pieza unaPieza) {
		boolean laContiene, a, b, c, d;
		
		a = losSoldados.contains(unaPieza);
		b = losEdificios.contains(unaPieza);
		c = losAldeanos.contains(unaPieza);
		d = elCastillo.equals(unaPieza);
		
		laContiene = (a | b | c | d);
		return laContiene;
	}

	//---------------------      Fin Manejo de Piezas     ------------------------
	//----------------------------------------------------------------------------
      
    private void actualizarPoblacion(){
    	poblacion = losSoldados.size();
    	poblacion += losAldeanos.size();
    }

}
