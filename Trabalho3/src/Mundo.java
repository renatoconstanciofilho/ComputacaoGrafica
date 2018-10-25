import java.util.ArrayList;

public class Mundo {

	private ArrayList<Poligono> listaPoligonos;
	private Camera camera;
	private Poligono poligonoSelecionado;
	private Point4D poligonoSelecionadoPonto;

	// https://www.devmedia.com.br/padrao-de-projeto-singleton-em-java/26392

	private static Mundo instance = new Mundo();
	
	// Janela
	private int esquerda = -400;
	private int direita = 400;
	private int superior = 400;
	private int inferior = -400;

	private Mundo() {
		listaPoligonos = new ArrayList<>();
		camera = new Camera();
		poligonoSelecionado = new Poligono();
		listaPoligonos.add(poligonoSelecionado);
		poligonoSelecionadoPonto = poligonoSelecionado.getUltimoPonto();
	}

	public static Mundo getInstance() {
		if (instance == null)
			instance = new Mundo();
		return instance;
	}

	public void adicionarPoligono() {
		poligonoSelecionado = new Poligono();
		listaPoligonos.add(poligonoSelecionado);
	}

	public Poligono getUltimoPoligono() {
		return listaPoligonos != null && listaPoligonos.isEmpty() ? listaPoligonos.get(listaPoligonos.size() - 1)
				: null;
	}

	// Parte 1 selecionar poligono
	public void selecionarPoligono(float x, float y) {
		Poligono temp;
		for (Poligono poligono : listaPoligonos) {
			if (x > poligono.getBbox().obterMenorX() && x < poligono.getBbox().obterMaiorX()
					&& y > poligono.getBbox().obterMenorY() && y < poligono.getBbox().obterMaiorY()) {
				temp = poligono;
				break;
			}
		}

		// TODO Scanline
	}

	public ArrayList<Poligono> getListaPoligonos() {
		return listaPoligonos;
	}

	public void setListaPoligonos(ArrayList<Poligono> listaPoligonos) {
		this.listaPoligonos = listaPoligonos;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Poligono getPoligonoSelecionado() {
		return poligonoSelecionado;
	}

	public void setPoligonoSelecionado(Poligono poligonoSelecionado) {
		this.poligonoSelecionado = poligonoSelecionado;
	}

	public Point4D getPoligonoSelecionadoPonto() {
		return poligonoSelecionadoPonto;
	}

	public void setPoligonoSelecionadoPonto(Point4D poligonoSelecionadoPonto) {
		this.poligonoSelecionadoPonto = poligonoSelecionadoPonto;
	}

}
