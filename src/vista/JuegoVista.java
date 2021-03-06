package vista;


import java.io.File;

import controlador.FinalizarTurnoHandler;
import controlador.HerramientasMapa;
import controlador.TextoHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.JuegoTerminadoListener;
import modelo.Jugador;
import modelo.Pieza;
import modelo.Tablero;

public class JuegoVista extends BorderPane implements JuegoTerminadoListener{

    final double SCALE_DELTA = 1.1;
	double ultimoX;
	double ultimoY;

	public static final int TAMANIO_CASILLA = CasillaVista.TAMANIO_CASILLA;
	
	//--------------------------
	private StackPane panelSuperior;
	private StackPane panelIzquierdo;
	private StackPane panelCentro;
	private StackPane panelDerecho;	
	private StackPane botonesPanelIzquierdo;
	
	private Juego modelo;
	private Stage stagePrincipal;
	private Tablero elTablero;
	private Pane mapa;
	
	private Jugador jugador1;
	private Jugador jugador2;
	
	private PiezaVista piezaSeleccionada;
	private CasillaVista casillaSeleccionada;
	private Group grupoCasillas = new Group(); 
	private Group grupoPiezas = new Group();
	
	//---------- Panel Inferior Central ----------------------
	Label nombreJugador1;
	Label nombreJugador2;
	
	Label oro1;
	Label oro2;
	
	Label edificios1;
	Label edificios2;
	
	Label hpCastillo1;
	Label hpCastillo2;
	
	Label aldeanos1;
	Label aldeanos2;
	
	Label soldados1;
	Label soldados2;
	
	Label poblacion1;
	Label poblacion2;
	//---------------------------------------------------------
	//--------------------- Sonidos ---------------------------
	private MediaPlayer ambienteInicial;
	private MediaPlayer ambienteCombate;
	private MediaPlayer error;
	private MediaPlayer finJuego;
	//---------------------------------------------------------

    public JuegoVista(String nombreJugador1, String nombreJugador2, Stage stagePrincipal){
    	piezaSeleccionada = null;
    	casillaSeleccionada = null;
    	this.stagePrincipal = stagePrincipal;
    	
    	jugador1 = new Jugador(nombreJugador1);
    	jugador2 = new Jugador(nombreJugador2);
    	
    	modelo = HerramientasMapa.crearJuego(this,grupoCasillas, grupoPiezas, jugador1, jugador2);
    	    	
    	modelo.setListenerJuegoTerminado(this);
    	
    	crearMapa();
    	crearPanelSuperior();
    	crearPanelInferior();
    	configurarSonido();
    	
    	iniciarJuego();
    }
    
    private void iniciarJuego() {

    	modelo.iniciarJuego(); 	
    	
    	BorderPane panelTransparente = new BorderPane();
    	panelTransparente.setManaged(false);
    	Text jugadorInicial = new Text("Comienza: " + modelo.getJugadorActual().obtenerNombre());
    	jugadorInicial.setFont(Font.loadFont("file:src/resources/fonts/Mairon.ttf", 40));
    	jugadorInicial.setFill(Color.GOLD);
    	jugadorInicial.setOnMouseMoved(new TextoHandler(jugadorInicial));
        //Centrar esto, no se cmo
    	panelTransparente.setCenter(jugadorInicial);
        mapa.getChildren().add(panelTransparente);
    }
    
    public Juego modelo() {
    	return modelo;
    }

    public void cobrarAJugadorActual(int monto){
    	modelo.getJugadorActual().cobrar(monto);
    }
    
    public void agregarTablero(Tablero unTablero) {
    	elTablero = unTablero;
    }
    
    public void decirGanador(){
        Text unTexto = new Text("NUEVO GANADOR " + modelo.seleccionarGanador().obtenerNombre() + " !");
       unTexto.setFont(Font.loadFont("file:src/resources/fonts/Mairon.ttf", 50));
       unTexto.setTextAlignment(TextAlignment.CENTER);
        //Esto es para calcular las dimensiones de pantalla pero no funciona me parece
       Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        unTexto.relocate(screenBounds.getWidth() / 2 , screenBounds.getHeight() / 2);
       unTexto.setOnMouseMoved(new TextoHandler(unTexto));
        getChildren().add(unTexto);
	}
    
    private void crearMapa(){
    	mapa = new Pane();
    	mapa.getChildren().addAll(grupoCasillas,grupoPiezas);
    	
    	ScrollPane mapaSC = new ScrollPane();
    	mapaSC.setContent(mapa);
    	setCenter(mapaSC);
    }
    
    private void crearPanelSuperior() {   	
    	this.panelSuperior = new StackPane();
    	Image background = new Image("resources/images/elementosJuego/panelSuperior/panelSuperior.png");
    	ImageView backgroundView = new ImageView(background);
    	backgroundView.fitWidthProperty().bind(stagePrincipal.widthProperty());
    	backgroundView.setFitHeight(30);
    	panelSuperior.getChildren().add(backgroundView);
    	
    	setTop(panelSuperior);
    }
    
    private void crearPanelInferior() {
    	crearPanelInferiorIzquierdo();
    	crearPanelInferiorCentro();
    	crearPanelInferiorDerecho();
    	
    	HBox contenedor = new HBox();
    	contenedor.getChildren().addAll(panelIzquierdo, panelCentro, panelDerecho);
    	setBottom(contenedor);	
    }

    private void crearPanelInferiorIzquierdo() {
    	this.panelIzquierdo = new StackPane();
    	
    	Image izquierdo = new Image("resources/images/elementosJuego/panelInferior/izquierdo/background.png");
    	ImageView panelIzquierdoView = new ImageView(izquierdo);
    	panelIzquierdoView.fitWidthProperty().bind(stagePrincipal.widthProperty().multiply(0.27));
    	panelIzquierdoView.fitHeightProperty().bind(stagePrincipal.heightProperty().multiply(0.2));
    	panelIzquierdo.getChildren().add(panelIzquierdoView);
    	
    	HBox menuPanelIzquierdo = new HBox();
    	botonesPanelIzquierdo = new StackPane(menuPanelIzquierdo);
    	panelIzquierdo.getChildren().add(botonesPanelIzquierdo);
    	botonesPanelIzquierdo.setAlignment(Pos.CENTER);
    	botonesPanelIzquierdo.setTranslateX(85);
    }
    
    private void crearPanelInferiorCentro() {
    	panelCentro = new StackPane();
    	
    	Image central = new Image("resources/images/elementosJuego/panelInferior/centro/background2.png");
    	ImageView panelCentralView = new ImageView(central);
    	panelCentralView.fitWidthProperty().bind(stagePrincipal.widthProperty().multiply(0.39));
    	panelCentralView.fitHeightProperty().bind(stagePrincipal.heightProperty().multiply(0.2));
    	panelCentro.getChildren().add(panelCentralView);
    	
    	configurarCuadrosDeTexto(panelCentralView);
    }
    
    private void crearPanelInferiorDerecho() {
    	this.panelDerecho = new StackPane();
    	
    	Image derecho = new Image("resources/images/elementosJuego/panelInferior/derecho/background.png");
    	ImageView panelDerechoView = new ImageView(derecho);
    	panelDerechoView.fitWidthProperty().bind(stagePrincipal.widthProperty().multiply(0.34));
    	panelDerechoView.fitHeightProperty().bind(stagePrincipal.heightProperty().multiply(0.2));
    	panelDerecho.getChildren().add(panelDerechoView);
    	
    	Button botonFinTurno = new Button("Finalizar Turno");
    	botonFinTurno.setOnAction( new FinalizarTurnoHandler(this, grupoPiezas));
    	Button botonSalir = new Button("Salir");
    	botonSalir.setOnAction(event ->  stagePrincipal.close());
    	Button botonMute = new Button("Mute Ambiente");
    	botonMute.setOnAction(event ->  {
    		ambienteInicial.setMute(!ambienteInicial.isMute());
    		ambienteCombate.setMute(!ambienteCombate.isMute());
    	});
    	
    	VBox menuPanelDerecho = new VBox(10);
    	menuPanelDerecho.getChildren().addAll(botonFinTurno, botonSalir, botonMute);
    	panelDerecho.getChildren().add(menuPanelDerecho);
    	menuPanelDerecho.setAlignment(Pos.CENTER);
    }
    
    private void configurarCuadrosDeTexto(ImageView imagenReferencia) {
    	nombreJugador1 = new Label(jugador1.obtenerNombre());    	
    	nombreJugador2 = new Label(jugador2.obtenerNombre());
    	
    	oro1 = new Label(Integer.toString(jugador1.obtenerOro()));
    	oro2 = new Label(Integer.toString(jugador2.obtenerOro()));
    	
    	edificios1 = new Label(Integer.toString(jugador1.cantidadEdificios()));
    	edificios2 = new Label(Integer.toString(jugador2.cantidadEdificios()));
    	
    	hpCastillo1 = new Label(Integer.toString(jugador1.hpCastillo()));
    	hpCastillo2 = new Label(Integer.toString(jugador2.hpCastillo()));
    	
    	aldeanos1 = new Label(Integer.toString(jugador1.cantidadAldeanos()));
    	aldeanos2 = new Label(Integer.toString(jugador2.cantidadAldeanos()));
    	
    	soldados1 = new Label(Integer.toString(jugador1.cantidadSoldados()));
    	soldados2 = new Label(Integer.toString(jugador2.cantidadSoldados()));
    	
    	poblacion1 = new Label(Integer.toString(jugador1.getPoblacion()));
    	poblacion2 = new Label(Integer.toString(jugador2.getPoblacion()));
    	
    	panelCentro.getChildren().addAll(nombreJugador1, oro1, edificios1, hpCastillo1, aldeanos1, soldados1, poblacion1);
    	panelCentro.getChildren().addAll(nombreJugador2, oro2, edificios2, hpCastillo2, aldeanos2, soldados2, poblacion2);
  

    	colocarTextoEn(90,60,imagenReferencia,oro1);
    	colocarTextoEn(90,115,imagenReferencia,edificios1);
    	colocarTextoEn(90,170, imagenReferencia,hpCastillo1);
    	colocarTextoEn(270,60,imagenReferencia,aldeanos1);
    	colocarTextoEn(270,115,imagenReferencia,soldados1);
    	colocarTextoEn(270,170,imagenReferencia,poblacion1);
    	
    	colocarTextoEn(545,60,imagenReferencia,oro2);
    	oro2.setAlignment(Pos.CENTER);
    	colocarTextoEn(545,115,imagenReferencia,edificios2);
    	edificios2.setAlignment(Pos.CENTER);
    	colocarTextoEn(545,170, imagenReferencia,hpCastillo2);
    	hpCastillo2.setAlignment(Pos.CENTER);
    	colocarTextoEn(355,60,imagenReferencia,aldeanos2);
    	aldeanos2.setAlignment(Pos.CENTER);
    	colocarTextoEn(355,115,imagenReferencia,soldados2);
    	soldados2.setAlignment(Pos.CENTER);
    	colocarTextoEn(355,170,imagenReferencia,poblacion2);
    	poblacion2.setAlignment(Pos.CENTER);
    	
    	colocarTextoEn(110,30,imagenReferencia,nombreJugador1);
    	colocarTextoEn(550,30,imagenReferencia,nombreJugador2);
    	nombreJugador2.setAlignment(Pos.CENTER);
    }
    
    private void colocarTextoEn(int x, int y, ImageView referencia, Label texto) {
    	texto.setManaged(false);
    	
    	x = (int)(referencia.getFitWidth()*((double)x/750));
    	y = (int)(referencia.getFitHeight()*((double)y/219));
    	
    	texto.resizeRelocate(x, y, 120, 30);
    	texto.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
    }
    
    private void configurarSonido(){
		String ambiente = "src/resources/sound/ambiente/ambiente.mp3"; 
		Media ambienteSound = new Media(new File(ambiente).toURI().toString());
		ambienteInicial = new MediaPlayer(ambienteSound);
		ambienteInicial.setVolume(0.7);
		
		ambiente = "src/resources/sound/ambiente/ambienteCombate.mp3"; 
		Media ambienteCombateSound = new Media(new File(ambiente).toURI().toString());
		ambienteCombate = new MediaPlayer(ambienteCombateSound);
		ambienteCombate.setVolume(0.7);
		
		String errorUrl = "src/resources/sound/ambiente/error.wav"; 
		Media errorSound = new Media(new File(errorUrl).toURI().toString());
		error = new MediaPlayer(errorSound);
		
		String victoria = "src/resources/sound/ambiente/victory.wav"; 
		Media victoriaSound = new Media(new File(victoria).toURI().toString());
		finJuego = new MediaPlayer(victoriaSound);
		
		ambienteInicial.play();
		ambienteInicial.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		    	ambienteInicial.stop();
		    	ambienteCombate.play();
		    }
		});
		ambienteCombate.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		    	ambienteCombate.stop();
		    	ambienteInicial.play();
		    }
		});
	}
    
    public void playError() {
    	error.stop();
    	error.play();
    }
    
    public void playFinJuego() {
    	finJuego.stop();
    	finJuego.play();
    }
    
    public void actualizarContadores() {
    	oro1.setText(Integer.toString(jugador1.obtenerOro()));
    	oro2.setText(Integer.toString(jugador2.obtenerOro()));
    	
    	edificios1.setText(Integer.toString(jugador1.cantidadEdificios()));
    	edificios2.setText(Integer.toString(jugador2.cantidadEdificios()));
    	
    	hpCastillo1.setText(Integer.toString(jugador1.hpCastillo()));
    	hpCastillo2.setText(Integer.toString(jugador2.hpCastillo()));
    	
    	aldeanos1.setText(Integer.toString(jugador1.cantidadAldeanos()));
    	aldeanos2.setText(Integer.toString(jugador2.cantidadAldeanos()));
    	
    	soldados1.setText(Integer.toString(jugador1.cantidadSoldados()));
    	soldados2.setText(Integer.toString(jugador2.cantidadSoldados()));
    	
    	poblacion1.setText(Integer.toString(jugador1.getPoblacion()));
    	poblacion2.setText(Integer.toString(jugador2.getPoblacion()));
    }

	public Tablero obtenerTablero() {
		return elTablero;
	}
    
    public void asignarMenuAcciones(HBox acciones) {
    	botonesPanelIzquierdo.getChildren().remove(0);
    	botonesPanelIzquierdo.getChildren().add(acciones);
    }

	public PiezaVista piezaSeleccionada() {
		return piezaSeleccionada;
	}
	
	public CasillaVista casillaSeleccionada() {
		return casillaSeleccionada;
	}

	public void seleccionarPieza(PiezaVista piezaVista) {
		piezaSeleccionada = piezaVista;
	}

	public void seleccionarCasilla(CasillaVista casillaVista) {
		casillaSeleccionada = casillaVista;
	}
	
	//----------------------------------------------------------------------------
	//---------------------      Manejo de Piezas     ----------------------------
	public void agregar(AldeanoVista unAldeano) {
		modelo.getJugadorActual().agregar(unAldeano.modelo());
		unAldeano.colocarColor();
		grupoPiezas.getChildren().add(unAldeano);
		actualizarContadores();
	}
	
	public void agregar(CastilloVista unCastillo) {
		modelo.getJugadorActual().agregar(unCastillo.modelo());
		unCastillo.colocarColor();
		grupoPiezas.getChildren().add(unCastillo);
		actualizarContadores();
	}
	
	public void agregar(EdificioVista unEdificio) {
		modelo.getJugadorActual().agregar(unEdificio.modelo());
		unEdificio.colocarColor();
		grupoPiezas.getChildren().add(unEdificio);
		actualizarContadores();
	}
	
	public void agregar(UnidadVista unaUnidad) {
		if(unaUnidad instanceof AldeanoVista) {
			agregar((AldeanoVista) unaUnidad);
		}
		else {
			modelo.getJugadorActual().agregar(unaUnidad.modelo());
			unaUnidad.colocarColor();
			grupoPiezas.getChildren().add(unaUnidad);
			actualizarContadores();
		}
	}
	
	//TODO necesito un remover para castillo o el programa corta muy rapido que no ?
	
	public void remover(UnidadVista unidadVista) {
		if(unidadVista instanceof AldeanoVista) {
			remover((AldeanoVista)unidadVista);
		}
		else {
			modelo.getJugadorEnemigo().remover(unidadVista.modelo());
			grupoPiezas.getChildren().remove(unidadVista);
			actualizarContadores();
		}
	}
	
	public void remover(EdificioVista edificioVista) {
		modelo.getJugadorEnemigo().remover(edificioVista.modelo());
		grupoPiezas.getChildren().remove(edificioVista);
	}
	
	public void remover(AldeanoVista aldeanoVista) {
		modelo.getJugadorEnemigo().remover(aldeanoVista.modelo());
		grupoPiezas.getChildren().remove(aldeanoVista);
		actualizarContadores();
	}
	
	public boolean enemigoContieneA(Pieza unaPieza) {
		return modelo.getJugadorEnemigo().contieneA(unaPieza);
	}
	
	public boolean aliadoContieneA(Pieza unaPieza) {
		return modelo.getJugadorActual().contieneA(unaPieza);
	}
	
    public boolean perteneceAJugador1(Pieza unaPieza) {
    	return jugador1.contieneA(unaPieza);
    }

	//---------------------      Fin Manejo de Piezas     ------------------------
	//----------------------------------------------------------------------------


}