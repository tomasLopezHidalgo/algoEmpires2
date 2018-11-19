package modelo;

public abstract class Unidad extends Pieza {

	protected boolean ocupado;
	
	public Unidad(Area unEspacio) throws Excepcion  {
		super(unEspacio);
		ocupado = false;
	}
	
	public boolean estaOcupado() {
		return ocupado;
	}

	public void mover(Area nuevoEspacio) throws Excepcion {
		siYaJugoElTurnoError();
		
        if (!ocupado & nuevoEspacio.estaLibre()) {
            espacioOcupado.liberar();
            espacioOcupado = nuevoEspacio;
            espacioOcupado.ocupar();
			turnoJugado = true;
        }
	}
	
	protected void siEstaOcupadoDaError() throws Excepcion {
		if(ocupado) {
			throw new Excepcion("ERROR: Pieza ocupada");
		}
	}

}