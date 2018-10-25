
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

	private int esquerda = -400;
	private int direita = 400;
	private int superior = 400;
	private int inferior = -400;

	private static final int MEDIDA_PADRAO = 5;

	private int tempX, tempY = 0;

	private int pX, pY = 0;
	private int raio = 100;
	private int angulo = -45;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(esquerda, direita, superior, inferior);

		SRU();

		// seu desenho ...
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(3.0f);
		gl.glPointSize(3.0f);

		desenhaReta(pX, pY, raio, angulo);

		gl.glFlush();
	}

	// Mouse opcional / Ao mexer as referencias para o teclado ficam alteradas
	public void mousePressed(MouseEvent e) {
		// tempX = e.getX();
		// tempY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		// int movtoX = e.getX() - tempX;
		// int movtoY = e.getY() - tempY;
		// pX += movtoX;
		// pY += movtoY;
		//
		// // Dump ...
		// System.out.println("Posição do mouse: " + movtoX + " / " + movtoY);
		//
		// tempX = e.getX();
		// tempY = e.getY();
		//
		// glDrawable.display();
	}

	public void desenhaReta(double posicaoX, double posicaoY, double raio, double angulo) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(0.0 + posicaoX, 0.0);
		gl.glVertex2d(retornaX(angulo, raio) + posicaoX, retornaY(angulo, raio) + posicaoY);
		gl.glEnd();
	}

	public double retornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public double retornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
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
		case 'q':
			pX -= MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Mover direita. (" + pX + ")");
			break;
		case 'w':
			pX += MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Mover esquerda. (" + pX + ")");
			break;
		case 'a':
			raio -= MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Aumentar raio. (" + raio + ")");
			break;
		case 's':
			raio += MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Diminuir raio. (" + raio + ")");
			break;
		case 'z':
			angulo -= MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Aumentar angulo. (" + angulo + ")");
			break;
		case 'x':
			angulo += MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Diminuir angulo. (" + angulo + ")");
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

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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

}
