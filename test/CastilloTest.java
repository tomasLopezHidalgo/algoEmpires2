import modelo.*;
import modelo.excepciones.CastilloDeJugadorFueDestruido;
import modelo.excepciones.NoSePuedeConstruirTanLejosError;
import modelo.excepciones.PiezaFueraDeAlcanceError;
import modelo.excepciones.PiezaYaJugoEnTurnoActualError;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class CastilloTest {

    @Test
    public void castilloCreaCatapulta() {
    	Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);
        Assert.assertTrue(unTablero.obtenerCasillaEn(3, 3).estaOcupada());

        Area espacioParaCatapulta = unTablero.definirArea(4,4,4,4);
        Assert.assertTrue(espacioParaCatapulta.estaLibre());

        ArmaDeAsedio nuevaArmaDeAsedio = (ArmaDeAsedio) unCastillo.crearCatapulta(4,4);
        Assert.assertFalse(espacioParaCatapulta.estaLibre());
        Assert.assertNotNull(nuevaArmaDeAsedio);
    }

    @Test
    public void ColocarCastillo() {
    	Tablero unTablero = new Tablero();

        Area zonaDeConstruccion = unTablero.definirArea(1, 1, Castillo.TAMANIO_LADO, Castillo.TAMANIO_LADO);
        Assert.assertTrue(zonaDeConstruccion.estaLibre());

        Castillo unCastillo = new Castillo(1,1);
        Assert.assertFalse(zonaDeConstruccion.estaLibre());
        Assert.assertFalse(unCastillo.obtenerAreaOcupada().estaLibre());

        Assert.assertTrue(unTablero.obtenerCasillaEn(3, 4).estaOcupada());
        Assert.assertTrue(unTablero.obtenerCasillaEn(4, 4).estaOcupada());
        //Las casillas adyacentes deberian estar libres
        Assert.assertFalse(unTablero.obtenerCasillaEn(0, 0).estaOcupada());
        Assert.assertFalse(unTablero.obtenerCasillaEn(0, 2).estaOcupada());
        Assert.assertFalse(unTablero.obtenerCasillaEn(0, 3).estaOcupada());
        Assert.assertFalse(unTablero.obtenerCasillaEn(0, 4).estaOcupada());
    }

    @Test
    public void recibirDanio() {
        Tablero unTablero = new Tablero();
        Castillo unCastillo = new Castillo(0,0);

        Assert.assertFalse(unCastillo.necesitaReparacion());

        unCastillo.recibirDanio(50);
        Assert.assertTrue(unCastillo.necesitaReparacion());
    }

    @Test
    public void atacarPiezaEnemiga() {
        Juego unJuego = new Juego();
        Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);
        Castillo otroCastillo = new Castillo(12,12);

        Aldeano otroAldeano = new Aldeano(4,4);
        Aldeano unAldeano = new Aldeano(5,5);

        Jugador unJugador = new Jugador("Ailen");
        Jugador otroJugador = new Jugador("Laura");

        unJugador.agregar(unCastillo);
        unJugador.agregar(unAldeano);

        otroJugador.agregar(otroCastillo);
        otroJugador.agregar(otroAldeano);

        unJuego.agregarJugador(unJugador);
        unJuego.agregarJugador(otroJugador);

        unJuego.iniciarJuegoNoRandom();

        //vidaAldeano=50
        Assert.assertFalse(otroAldeano.estaDestruida());
        unJugador.finalizarTurno();
        //vidaAldeano=30
        Assert.assertFalse(otroAldeano.estaDestruida());
        unJugador.finalizarTurno();
        //vidaAldeano=10
        Assert.assertFalse(otroAldeano.estaDestruida());
        unJugador.finalizarTurno();
        //vidaAldeano=0
        Assert.assertTrue(otroAldeano.estaDestruida());
    }

    @Test
    public void castilloDestruido() throws Exception{
        Juego unJuego = new Juego();
        Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);
        Castillo otroCastillo = new Castillo(12,12);

        Aldeano otroAldeano = new Aldeano(4,4);
        Aldeano unAldeano = new Aldeano(5,5);

        Jugador unJugador = new Jugador("Ailen");
        Jugador otroJugador = new Jugador("Laura");

        unJugador.agregar(unCastillo);
        unJugador.agregar(unAldeano);

        otroJugador.agregar(otroCastillo);
        otroJugador.agregar(otroAldeano);

        unJuego.agregarJugador(unJugador);
        unJuego.agregarJugador(otroJugador);

        unJuego.iniciarJuegoNoRandom();

        unCastillo.recibirDanio(1000); //El castillo se destruye cuando llega a 0 de vida

        boolean lanzoUnError=false;
        try {
            unCastillo.recibirDanio(10);
        } catch (Exception e)
        {
            lanzoUnError=true;
        }

        Assert.assertTrue(lanzoUnError);
    }

    @Test
    public void noSePuedeConstruirArmaDeAsedioTanLejos() {
        Juego unJuego = new Juego();
        Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);
        Castillo otroCastillo = new Castillo(12,12);

        Aldeano otroAldeano = new Aldeano(4,4);
        Aldeano unAldeano = new Aldeano(5,5);

        Jugador unJugador = new Jugador("Ailen");
        Jugador otroJugador = new Jugador("Laura");

        unJugador.agregar(unCastillo);
        unJugador.agregar(unAldeano);

        otroJugador.agregar(otroCastillo);
        otroJugador.agregar(otroAldeano);

        unJuego.agregarJugador(unJugador);
        unJuego.agregarJugador(otroJugador);

        unJuego.iniciarJuego();

        ArmaDeAsedio unaCatapulta = (ArmaDeAsedio)unCastillo.crearCatapulta(4,0);
        unCastillo.nuevoTurno();

        boolean lanzaUnError=false;
        try {
            ArmaDeAsedio otraCatapulta = (ArmaDeAsedio)unCastillo.crearCatapulta(7,0);
        } catch(NoSePuedeConstruirTanLejosError e) {
            lanzaUnError=true;
        }

        Assert.assertTrue(lanzaUnError);
    }

    @Test
    public void piezaFueraDeAlcanceParaAtacar() {
        Juego unJuego = new Juego();
        Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);
        Castillo otroCastillo = new Castillo(12,12);

        Aldeano otroAldeano = new Aldeano(4,4);
        Aldeano unAldeano = new Aldeano(5,5);

        Arquero unArquero = new Arquero(7,7);
        /* El castillo que está en el (0,0) ocupa hasta el (3,3), y su alcance
        es hasta 3 casillas, por lo que no llega a destruir al arquero */

        Jugador unJugador = new Jugador("Ailen");
        Jugador otroJugador = new Jugador("Laura");

        unJugador.agregar(unCastillo);
        unJugador.agregar(unAldeano);

        otroJugador.agregar(otroCastillo);
        otroJugador.agregar(otroAldeano);

        unJuego.agregarJugador(unJugador);
        unJuego.agregarJugador(otroJugador);

        unJuego.iniciarJuego();

        unCastillo.nuevoTurno();
        Assert.assertFalse(unArquero.estaDestruida());

    }

    @Test
    public void castilloYaJugoEnEseTurno() {
        Tablero unTablero = new Tablero();
        Castillo unCastillo = new Castillo(0,0);

        ArmaDeAsedio catapulta = (ArmaDeAsedio)unCastillo.crearCatapulta(4,0);

        boolean lanzaUnError=false;
        try {
            ArmaDeAsedio unaCatapulta = (ArmaDeAsedio)unCastillo.crearCatapulta(4,2);
        } catch (PiezaYaJugoEnTurnoActualError e){
            lanzaUnError=true;
        }

        Assert.assertTrue(lanzaUnError);


    }

    @Test
    public void edificioNecesitaReparacionCastillo() throws Exception {
        Tablero unTablero = new Tablero();

        Edificio unEdificio = new Castillo(0,0);
        Assert.assertTrue(unTablero.obtenerCasillaEn(3,3).estaOcupada());

        Assert.assertFalse(unEdificio.necesitaReparacion());

        unEdificio.recibirDanio(15);

        Assert.assertTrue(unEdificio.necesitaReparacion());

    }

    @Test
    public void edificioRepararCastillo() throws Exception {
        Tablero unTablero = new Tablero();

        Edificio unEdificio = new Castillo(0,0);
        Assert.assertTrue(unTablero.obtenerCasillaEn(3, 3).estaOcupada());

        Assert.assertFalse(unEdificio.necesitaReparacion());

        unEdificio.recibirDanio(15);

        Assert.assertTrue(unEdificio.necesitaReparacion());

        unEdificio.reparar();

        Assert.assertFalse(unEdificio.necesitaReparacion());

    }

    @Test
    public void edificioConstruirCastillo() throws Exception {
        Tablero unTablero = new Tablero();

        Edificio unEdificio = new Castillo(0,0);
        Assert.assertTrue(unTablero.obtenerCasillaEn(3, 3).estaOcupada());

        Assert.assertFalse(unEdificio.enConstruccion());


    }

    @Test
    public void edificioRecibirDanioDeArqueroCastillo() throws Exception {
        Tablero unTablero = new Tablero();

        Castillo unCastillo = new Castillo(0,0);

        Arquero unArquero = new Arquero(4,4);

        Assert.assertFalse(unCastillo.necesitaReparacion());

        unCastillo.recibirDanioDe(unArquero);

        Assert.assertTrue(unCastillo.necesitaReparacion());
    }

    @Test
    public void edificioRecibirDanioDeEspadachinCastillo() throws Exception {
        Tablero unTablero = new Tablero();

        Edificio unEdificio = new Castillo(0,0);
        Assert.assertTrue(unTablero.obtenerCasillaEn(3, 3).estaOcupada());

        Espadachin unEspadachin = new Espadachin(4,4);

        Assert.assertFalse(unEdificio.necesitaReparacion());

        unEdificio.recibirDanioDe(unEspadachin);

        Assert.assertTrue(unEdificio.necesitaReparacion());

    }

    @Test
    public void liberarUbicacionCastillo() {
        Juego unJuego = new Juego();
        Tablero unTablero = new Tablero();

        Area zonaDeConstruccion = unTablero.definirArea(0,0,3,3);
        Castillo unCastillo = new Castillo(0,0);

        Assert.assertTrue(unTablero.obtenerCasillaEn(3,3).estaOcupada());
        Assert.assertFalse(zonaDeConstruccion.estaLibre());
        Assert.assertEquals(16,zonaDeConstruccion.obtenerCantidadDeCasillas());

        unCastillo.setCastilloListener(unJuego);
        unCastillo.recibirDanio(1000);//Castillo.VIDA_MAX = 1000

        Assert.assertTrue(zonaDeConstruccion.estaLibre());
    }

}
