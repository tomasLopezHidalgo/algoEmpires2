import org.junit.Test;

import junit.framework.Assert;
import modelo.Aldeano;
import modelo.Area;
import modelo.Plaza;
import modelo.Tablero;

@SuppressWarnings("deprecation")
public class PlazaCentralTest {

    @Test
    public void ColocarPlaza() throws Exception { 
    	Tablero unTablero = new Tablero(5,5);

        //voy a ocupar a partir de la (2,2)
        //se ocuparan las casillas en (2,2) (2,3) (3,2) (3,3)
        Area zonaDeConstruccion = unTablero.definirArea(2, 2, Plaza.TAMANIO_LADO+1, Plaza.TAMANIO_LADO+1);
        Assert.assertEquals(true, zonaDeConstruccion.estaLibre());

        Plaza unaPlaza = new Plaza(zonaDeConstruccion);
        Assert.assertEquals(false, zonaDeConstruccion.estaLibre());
        Assert.assertEquals(false, unaPlaza.obtenerAreaOcupada().estaLibre());

        //Las casillas adyacentes deberian estar libres
        Assert.assertEquals(false, unTablero.obtenerCasillaEn(1,1).estaOcupada());
        Assert.assertEquals(false, unTablero.obtenerCasillaEn(4,4).estaOcupada());
        Assert.assertEquals(false, unTablero.obtenerCasillaEn(2,1).estaOcupada());
        Assert.assertEquals(false, unTablero.obtenerCasillaEn(3,4).estaOcupada());
    }

    @Test
    public void PlazaCentralCreaAldeano() throws Exception {
        Tablero unTablero = new Tablero(5,5);

        Area zonaDeConstruccion = unTablero.definirArea(0, 0, Plaza.TAMANIO_LADO-1, Plaza.TAMANIO_LADO-1);

        Plaza unaPlaza = new Plaza(zonaDeConstruccion);
        Assert.assertEquals(true, unTablero.obtenerCasillaEn(1,1).estaOcupada());
        
        //La Plaza debe estar construida para poder crear unidades (3 turnos)
        unaPlaza.construir();
        unaPlaza.construir();
        unaPlaza.construir();
        
        Area espacioAldeano = unTablero.definirArea(0,2,0,2);
        Assert.assertEquals(true, espacioAldeano.estaLibre());
        
        Aldeano nuevoAldeano = unaPlaza.crearAldeano(espacioAldeano);
        Assert.assertEquals(false, espacioAldeano.estaLibre());
        Assert.assertNotNull(nuevoAldeano);
    }
}
