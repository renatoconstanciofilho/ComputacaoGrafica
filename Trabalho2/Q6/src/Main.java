
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
	private Ponto4D pto1 = new Ponto4D(0.0, 0.0, 0.0, 1.0);
	private Ponto4D pto2 = new Ponto4D(200.0, 200.0, 0.0, 1.0);

	// Janela
	private int esquerda = -400;
	private int direita = 400;
	private int superior = 400;
	private int inferior = -400;

	// Retas guia
	private static final int PADRAO = 100;
	
	// Mouse
	private int antigoX, antigoY = 0;

	// Spline
	private int qtdPontos = 10;
	private float[][] armazenaPontos = new float[4][2];
	private int vertice = 0;

	private static final int MEDIDA_PADRAO = 5;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		armazenaPontos[0][0] = PADRAO;
		armazenaPontos[0][1] = PADRAO;
		armazenaPontos[1][0] = PADRAO;
		armazenaPontos[1][1] = -PADRAO;
		armazenaPontos[2][0] = -PADRAO;
		armazenaPontos[2][1] = -PADRAO;
		armazenaPontos[3][0] = -PADRAO;
		armazenaPontos[3][1] = PADRAO;
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(esquerda, direita, superior, inferior);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		SRU();
		poliedroControle(armazenaPontos[0][0], armazenaPontos[0][1], armazenaPontos[1][0], armazenaPontos[1][1]);
		poliedroControle(armazenaPontos[1][0], armazenaPontos[1][1], armazenaPontos[2][0], armazenaPontos[2][1]);
		poliedroControle(armazenaPontos[2][0], armazenaPontos[2][1], armazenaPontos[3][0], armazenaPontos[3][1]);
		desenhaSpline();
		desenhaPonto();
		gl.glFlush();
	}

	private void desenhaPonto() {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glPointSize(5.0f);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(armazenaPontos[vertice][0], armazenaPontos[vertice][1]);
		gl.glEnd();

	}

	private void poliedroControle(float x1, float y1, float x2, float y2) {
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glLineWidth(3.0f);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();

	}

	private float[] interpolacaoSpline(float[] p1, float[] p2, int t) {
		float[] posicao = new float[2];
		posicao[0] = p1[0] + (p2[0] - p1[0]) * t / qtdPontos;
		posicao[1] = p1[1] + (p2[1] - p1[1]) * t / qtdPontos;
		return posicao;
	}

	private void desenhaSpline() {
		float[] p1p2, p2p3, p3p4, p1p2p3, p2p3p4, p1p2p3p4;
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glLineWidth(3.0f);
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i <= qtdPontos; i++) {
			p1p2 = interpolacaoSpline(armazenaPontos[0], armazenaPontos[1], i);
			p2p3 = interpolacaoSpline(armazenaPontos[1], armazenaPontos[2], i);
			p3p4 = interpolacaoSpline(armazenaPontos[2], armazenaPontos[3], i);
			p1p2p3 = interpolacaoSpline(p1p2, p2p3, i);
			p2p3p4 = interpolacaoSpline(p2p3, p3p4, i);
			p1p2p3p4 = interpolacaoSpline(p1p2p3, p2p3p4, i);
			gl.glVertex2d(p1p2p3p4[0], p1p2p3p4[1]);
		}
		gl.glEnd();
	}

	public void mouseDragged(MouseEvent e) {
		int movtoX = e.getX() - antigoX;
		int movtoY = e.getY() - antigoY;
		armazenaPontos[vertice][0] += movtoX;
		armazenaPontos[vertice][1] += movtoY;

		System.out.println("Posição mouse: " + movtoX + " / " + movtoY);

		antigoX = e.getX();
		antigoY = e.getY();

		glDrawable.display();
	}
	
	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
        antigoY = e.getY();
	}
	
	public void keyPressed(KeyEvent e) {

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case '1':
			vertice = 0;
			System.out.println("Selecionado vértice 1");
			break;
		case '2':
			vertice = 1;
			System.out.println("Selecionado vértice 2");
			break;
		case '3':
			vertice = 2;
			System.out.println("Selecionado vértice 3");
			break;
		case '4':
			vertice = 3;
			System.out.println("Selecionado vértice 4");
			break;
		case '-':
			if (qtdPontos > 1) {
				qtdPontos--;
				System.out.println("Diminuido a quantidade de pontos para: " + qtdPontos);
			} else
				System.out.println("Quantidade mínima de pontos atingida: " + qtdPontos);
			break;
		case '+':
			qtdPontos++;
			System.out.println("Aumentada a quantidade de pontos para: " + qtdPontos);
			break;
		default:
			break;
		}
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

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
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