import java.util.ArrayList;

import javax.media.opengl.GL;

public class Poligono {

	private ArrayList<Point4D> listaPontos;
	private ArrayList<Poligono> listaPoligonosFilhos;
	private BoundingBox bbox;
	private Cor cor;
	private Transformacao4D transformacao4D;
	private int primitiva;
	private float tamanho;

	public Poligono() {
		listaPontos = new ArrayList<>();
		listaPoligonosFilhos = new ArrayList<>();
		bbox = new BoundingBox();
		cor = new Cor(0, 0, 0);
		transformacao4D = new Transformacao4D();
		primitiva = 2;
		tamanho = 2;
	}

	public void desenha(GL gl) {
		gl.glPushMatrix();
			gl.glMultMatrixd(transformacao4D.GetDate(), 0);
			gl.glLineWidth(tamanho);
			gl.glBegin(getPrimitiva());
			gl.glColor3f(cor.getR(), cor.getG(), cor.getB());
			for (Point4D ponto : listaPontos) {
				gl.glVertex2d(ponto.GetX(), ponto.GetY());
			}
			gl.glEnd();
			
//			INTERAR OS FILHOS
			
		gl.glPopMatrix();
	}

	public void desenhaBBox(GL gl) {
		if (!listaPontos.isEmpty())
			bbox.desenharOpenGLBBox(gl);
	}

	private void recalcularBBox() {
		bbox.atribuirBoundingBox(0, 0, 0, 0, 0, 0);
		for (Point4D ponto : listaPontos) {
			bbox.atualizarBBox(ponto);
		}
	}

	public void adicionaPonto(double x, double y) {
		Point4D temp1 = new Point4D(x, y, 0, 1);
		Point4D temp2 = new Point4D(x, y, 0, 1);
		if (listaPontos.isEmpty()) {
			listaPontos.add(temp1);
			listaPontos.add(temp2);
			bbox.atualizarBBox(temp1);
		} else {
			alteraUltimoPonto(x, y);
			listaPontos.add(temp1);
			listaPontos.add(temp2);
			bbox.atualizarBBox(temp1);
		}
	}

	public void alteraUltimoPonto(double x, double y) {
		getUltimoPonto().SetX(x);
		getUltimoPonto().SetY(y);
	}

	public void moverPonto(Point4D p, float x, float y) {
		p.SetX(x);
		p.SetY(y);
		recalcularBBox();
	}

	public Point4D retornaPontoMaisProximo(float x, float y) {
		// Cria um ponto com os dados do click
		Point4D temp = new Point4D(x, y, 0, 1);
		// Seta o primeiro ponto como ponto mais perto
		Point4D pontoMaisProximo = listaPontos.get(0);
		// Calcula a distancia entre o dado do clicke e o primeiro ponto
		float menorDistancia = distancia(temp, pontoMaisProximo);
		// Percorre todos os pontos verificando
		for (int i = 1; i < listaPontos.size(); i++) {
			// Se a menor distancia for maior do q a distancia do ponto atual é setado nova
			// menor distancia e novo ponto mais próximo
			if (menorDistancia > distancia(temp, listaPontos.get(i))) {
				menorDistancia = distancia(temp, listaPontos.get(i));
				pontoMaisProximo = listaPontos.get(i);
			}
		}
		// Retorna o ponto mais próximo
		return pontoMaisProximo;
	}

	private float distancia(Point4D p1, Point4D p2) {
		return (float) (Math.pow((p2.GetX() - p1.GetX()), 2) + Math.pow((p2.GetY() - p1.GetY()), 2));
	}

	public Point4D getUltimoPonto() {
	    return listaPontos.isEmpty()? null : listaPontos.get(listaPontos.size() - 1);
	  }

	public ArrayList<Point4D> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(ArrayList<Point4D> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public BoundingBox getBbox() {
		return bbox;
	}

	public void setBbox(BoundingBox bbox) {
		this.bbox = bbox;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Transformacao4D getTransformacao4D() {
		return transformacao4D;
	}

	public void setTransformacao4D(Transformacao4D transformacao4d) {
		transformacao4D = transformacao4d;
	}

	public int getPrimitiva() {
		return primitiva;
	}

	public void setPrimitiva(int primitiva) {
		if (primitiva <= 2)
			this.primitiva = 2;
		else
			this.primitiva = 3;
	}
	
	public void mudaPrimitiva() {
		if(primitiva == 2)
			this.primitiva = 3;
		else
			this.primitiva = 2;
	}

}
