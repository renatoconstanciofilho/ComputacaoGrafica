
/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
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
		gl.glPointSize(2.0f);
		gl.glBegin(GL.GL_POINTS);
		for (double ang = 0; ang < 360; ang = ang + 5) {
			double x = pto1.RetornaX(ang, 100);
			double y = pto2.RetornaY(ang, 100);
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");
		switch (e.getKeyCode()) {
		}
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
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case 'o':
			esquerda -= MEDIDA_PADRAO;
			direita += MEDIDA_PADRAO;
			superior += MEDIDA_PADRAO;
			inferior -= MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Zoom In");
			break;
		case 'i':
			esquerda += MEDIDA_PADRAO;
			direita -= MEDIDA_PADRAO;
			superior -= MEDIDA_PADRAO;
			inferior += MEDIDA_PADRAO;
			glDrawable.display();
			System.out.println("Zoom Out");
			break;
		case 'e':
			if (esquerda < -200) {
				esquerda += MEDIDA_PADRAO;
				direita += MEDIDA_PADRAO;
			} else {
				System.out.println("Limite do lado esquerdo atingido. (" + esquerda + ")");
			}

			glDrawable.display();
			break;
		case 'd':
			if (direita > 200) {
				esquerda -= MEDIDA_PADRAO;
				direita -= MEDIDA_PADRAO;
			} else {
				System.out.println("Limite do lado direito atingido. (" + direita + ")");
			}
			glDrawable.display();
			break;
		case 'c':
			if (superior < 600) {
				superior += MEDIDA_PADRAO;
				inferior += MEDIDA_PADRAO;
			} else {
				System.out.println("Limite superior aringido. (" + superior + ")");
			}

			glDrawable.display();
			break;
		case 'b':
			if (inferior > -600) {
				superior -= MEDIDA_PADRAO;
				inferior -= MEDIDA_PADRAO;
			} else {
				System.out.println("Limite inferior atingido. (" + inferior + ")");
			}

			glDrawable.display();
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

}
