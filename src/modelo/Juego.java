package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import modelo.estadoJuego.EstadoJuego;
import modelo.estadoJuego.JuegaJugador1;
import modelo.estadoJuego.JuegaJugador2;
import modelo.estadoJuego.NoComenzado;
import modelo.estadoJuego.Terminado;

public class Juego {

    private List<Jugador> jugadores;
    private Tablero tablero;
    private EstadoJuego estado;

    private void seleccionarJugadorInicial(){
        int numeroRandom = ThreadLocalRandom.current().nextInt(0, 2);

        if(numeroRandom == 0)
            this.estado = new JuegaJugador1();
        else
            this.estado = new JuegaJugador2();
    }

    //TODO: Agregar posibilidad de definir un tamaño inicial de tablero.
    public Juego(String nombreJugador1, String nombreJugador2){

        jugadores = new ArrayList<>();
        jugadores.add(new Jugador(nombreJugador1));
        jugadores.add(new Jugador(nombreJugador2));
        this.tablero = new Tablero();
        this.estado = new NoComenzado();
    }

    public void iniciarJuego() {

        seleccionarJugadorInicial();

        jugadores.get(0).asignarPiezas(tablero.generarPiezasInicialesEquipo1());
        jugadores.get(1).asignarPiezas(tablero.generarPiezasInicialesEquipo2());

    }

    public Tablero getTablero(){
        return this.tablero;
    }

    public Jugador getJugadorActual(){

        return (this.estado.getJugadorActual(this.jugadores));
    }

    public void finalizarTurno(){

        this.estado.getJugadorActual(jugadores).finalizarTurno();
        this.estado = estado.finalizarTurno();

        if(this.estado.getJugadorActual(jugadores).castilloFueDestruido())
            this.estado = new Terminado();
    }

    public Jugador seleccionarGanador(){

        return (estado.seleccionarGanador(this.jugadores));

    }

}
