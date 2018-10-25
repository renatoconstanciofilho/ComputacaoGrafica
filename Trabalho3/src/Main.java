
/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

// http://gcg.inf.furb.br/?page_id=470

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

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

	private Point4D posicaoMouse = new Point4D(0, 0, 0, 1);
	private boolean desenhar = true;

	public void init(GLAutoDrawable drawable) {
		System.out.println("--- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println("--- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(esquerda, direita, superior, inferior);

		ArrayList<Poligono> poligonos = Mundo.getInstance().getListaPoligonos();
		if (!poligonos.isEmpty()) {
			for (Poligono poligono : poligonos) {
				poligono.desenha(gl);
			}
			Poligono poligonoSelecionado = Mundo.getInstance().getPoligonoSelecionado();
			if (poligonoSelecionado != null)
				poligonoSelecionado.desenhaBBox(gl);
		}
		gl.glFlush();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Duvida se seleciono o poligono todo ou só o ponto

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		posicaoMouse.SetX(e.getX());
		posicaoMouse.SetY(e.getY());

		if (Mundo.getInstance().getUltimoPoligono() != null)
			Mundo.getInstance().getPoligonoSelecionado().alteraUltimoPonto(e.getX(), e.getY());

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			// Verificar o nó mais próximo e selecionar
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			if (desenhar) {
				Mundo.getInstance().getPoligonoSelecionado().adicionaPonto(e.getX(), e.getY());
				glDrawable.display();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			if (desenhar) {
				desenhar = false;
				System.out.println("Modo inserção de movimentação");
			} else {
				desenhar = true;
				System.out.println("Modo inserção de ponto");
			}
			break;
		case KeyEvent.VK_DELETE:
			Mundo.getInstance().getListaPoligonos().remove(Mundo.getInstance().getPoligonoSelecionado());
			Mundo.getInstance().setPoligonoSelecionado(Mundo.getInstance().getUltimoPoligono());
			break;
		case KeyEvent.VK_A:
			Mundo.getInstance().getPoligonoSelecionado().mudaPrimitiva();
			break;
		case KeyEvent.VK_R:
			Mundo.getInstance().getPoligonoSelecionado().getCor().setRed();
			break;
		case KeyEvent.VK_G:
			Mundo.getInstance().getPoligonoSelecionado().getCor().setGreen();
			break;
		case KeyEvent.VK_B:
			Mundo.getInstance().getPoligonoSelecionado().getCor().setBlue();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

}