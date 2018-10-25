
/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	// Janela
	private int esquerda = -400;
	private int direita = 400;
	private int superior = 400;
	private int inferior = -400;

	// Mouse
	private int antigoX, antigoY = 0;

	// Pontos BBox
	private int circuloInternoX = 200;
	private int circuloInternoY = -200;
	private int[] rgbBbox = { 255, 0, 255 };
	private static final int CIRCULO_EXTERNO = 200;
	private static final int RAIO_PADRAO = 150;
	private double diametroCirculoMaior = Math.pow(RAIO_PADRAO, 2);
	// Angulos a em contato com o circulo: 45, 135, 225, 315
	private Point4D p1 = new Point4D(retornaX(45, RAIO_PADRAO) + CIRCULO_EXTERNO,
			retornaY(45, RAIO_PADRAO) - CIRCULO_EXTERNO, 0.0, 1.0);
	private Point4D p2 = new Point4D(retornaX(135, RAIO_PADRAO) + CIRCULO_EXTERNO,
			retornaY(135, RAIO_PADRAO) - CIRCULO_EXTERNO, 0.0, 1.0);
	private Point4D p3 = new Point4D(retornaX(225, RAIO_PADRAO) + CIRCULO_EXTERNO,
			retornaY(225, RAIO_PADRAO) - CIRCULO_EXTERNO, 0.0, 1.0);
	private Point4D p4 = new Point4D(retornaX(315, RAIO_PADRAO) + CIRCULO_EXTERNO,
			retornaY(315, RAIO_PADRAO) - CIRCULO_EXTERNO, 0.0, 1.0);
	private BoundingBox bbox = new BoundingBox(p1.GetX(), p1.GetY(), p1.GetZ(), p1.GetX(), p1.GetY(), p1.GetZ());

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		bbox.atualizarBBox(p2);
		bbox.atualizarBBox(p3);
		bbox.atualizarBBox(p4);
		bbox.processarCentroBBox();
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(esquerda, direita, superior, inferior);
		
		SRU();

		// Circulo Externo
		desenhaCirculo(CIRCULO_EXTERNO, -CIRCULO_EXTERNO, 360, RAIO_PADRAO);
		// Circulo interno
		desenhaCirculo(circuloInternoX, circuloInternoY, 360, (RAIO_PADRAO / 3));
		// Cor do quadrado
		gl.glColor3f(rgbBbox[0], rgbBbox[1], rgbBbox[2]);
		bbox.desenharOpenGLBBox(gl);

		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glPointSize(5.0f);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(circuloInternoX, circuloInternoY);
		gl.glEnd();

		gl.glFlush();

	}

	public void desenhaCirculo(double posicaoX, double posicaoY, int qtdPontos, double raio) {
		int angulo = 360 / qtdPontos;
		gl.glColor3f(0, 0, 0);
		gl.glLineWidth(1.0f);
		gl.glPointSize(3.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int i = 0; i < 360;) {
			gl.glVertex2d(retornaX(i, raio) + posicaoX, retornaY(i, raio) + posicaoY);
			i = i + angulo;
		}
		gl.glEnd();
	}

	public double retornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public double retornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}

	public void mouseDragged(MouseEvent e) {
		int movtoX = e.getX() - antigoX;
		int movtoY = e.getY() - antigoY;
		// Desenha o circulo menor conforme as coordenadas do Mouse
		int circuloInternoXTemp = circuloInternoX + movtoX;
		int circuloInternoYTemp = circuloInternoY + movtoY;
		if (contidoBbox(circuloInternoXTemp, circuloInternoYTemp)) {
			// Se est� dentro do quadrado mantem a cor ROXA e atualiza o desenho do circulo
			circuloInternoX = circuloInternoXTemp;
			circuloInternoY = circuloInternoYTemp;
			// ROXO
			rgbBbox[0] = 255;
			rgbBbox[1] = 0;
			rgbBbox[2] = 255;
		} else {
			// Se est� fora do quadrado mas dentro do circulo, muda a cor para amarelo e
			// atualiza o desenho do circulo
			if (contidoCirculoMaior(circuloInternoXTemp, circuloInternoYTemp)) {
				circuloInternoX = circuloInternoXTemp;
				circuloInternoY = circuloInternoYTemp;
				// AMARELO
				rgbBbox[0] = 255;
				rgbBbox[1] = 255;
				rgbBbox[2] = 0;
			} else {
				// Se fora do quadrado e fora do circulo maior, muda a cor para ciano e n�o
				// atualiza o circulo;
				// CIANO
				rgbBbox[0] = 0;
				rgbBbox[1] = 255;
				rgbBbox[2] = 255;
			}
		}
		antigoX = e.getX();
		antigoY = e.getY();
		glDrawable.display();
	}

	private boolean contidoCirculoMaior(int x, int y) {
		// Distancia euclidiana
		return diametroCirculoMaior >= Math.pow((x - bbox.obterCentro().GetX()), 2)
				+ Math.pow((y - bbox.obterCentro().GetY()), 2);
	}

	public boolean contidoBbox(double x, double y) {
		return x > bbox.obterMenorX() && x < bbox.obterMaiorX() && y > bbox.obterMenorY() && y < bbox.obterMaiorY();
	}

	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		// Quando o bot�o do mouse � solto, retorna ao centro e atualiza a cor para ROXO
		circuloInternoX = 200;
		circuloInternoY = -200;
		rgbBbox[0] = 255;
		rgbBbox[1] = 0;
		rgbBbox[2] = 255;
		glDrawable.display();

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void SRU() {
		// gl.glDisable(gl.GL_TEXTURE_2D);
		// gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
		// gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and
		// texture is enabled

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-200.0f, 0.0f);
		gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -200.0f);
		gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}