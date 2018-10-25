
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

	private int[] tipo = { GL.GL_POINTS, GL.GL_LINES, GL.GL_LINE_LOOP, GL.GL_LINE_STRIP, GL.GL_TRIANGLES,
			GL.GL_TRIANGLE_FAN, GL.GL_TRIANGLE_STRIP, GL.GL_QUADS, GL.GL_QUAD_STRIP, GL.GL_POLYGON };
	private String[] tipoS = { "GL_POINTS", "GL_LINES", "GL_LINE_LOOP", "GL_LINE_STRIP", "GL_TRIANGLES",
			"GL_TRIANGLE_FAN", "GL_TRIANGLE_STRIP", "GL_QUADS", "GL_QUAD_STRIP", "GL_POLYGON" };

	private int posicao = tipo.length - 1;

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
		gl.glLineWidth(5.0f);
		gl.glPointSize(5.0f);
		gl.glBegin(tipo[retornaPosicao()]);
		// Inferior esquerdo
		gl.glColor3f(255, 0, 255);
		gl.glVertex2d(-200, 200);
		// Superior esquerdo
		gl.glColor3f(0, 0, 255);
		gl.glVertex2d(-200, -200);
		// Superior direito
		gl.glColor3f(0, 255, 0);
		gl.glVertex2d(200, -200);
		// Inferior direito
		gl.glColor3f(255, 0, 0);
		gl.glVertex2d(200, 200);
		gl.glEnd();
		gl.glFlush();
	}

	private int retornaPosicao() {
		if (posicao >= (tipo.length - 1)) {
			posicao = 0;
			System.out.println("Primitiva tipo: " + tipoS[posicao]);
			return posicao;
		}
		posicao++;
		System.out.println("Primitiva tipo: " + tipoS[posicao]);
		return posicao;
	}

	// private String retornaPrimitiva(int numero) {
	// switch (numero) {
	// case 0:
	// return "GL_POINTS";
	// case 1:
	// return "GL.GL_LINES";
	// case 2:
	// return "GL_LINE_LOOP";
	// case 3:
	// return "GL_LINE_STRIP";
	// case 4:
	// return "GL_TRIANGLES";
	// case 5:
	// return "GL_TRIANGLE_FAN";
	// case 6:
	// return "GL_TRIANGLE_STRIP";
	// case 7:
	// return "GL_QUADS";
	// case 8:
	// return "GL_QUADS_STRIP";
	// case 9:
	// return "GL_POLYGON";
	// default:
	// return "Desconhecida";
	// }
	// }

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
		if (arg0.getKeyChar() == ' ') {
			glDrawable.display();
			// System.out.println("Primitiva tipo: " + retornaPrimitiva(posicao));
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
