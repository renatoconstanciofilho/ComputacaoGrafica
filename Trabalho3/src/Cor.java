
public class Cor {
	
	private float r;
	private float g;
	private float b;
	
	public Cor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	public float getG() {
		return g;
	}
	public void setG(float g) {
		this.g = g;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	
	public void setRed() {
		this.r = 255;
		this.g = 0;
		this.b = 0;
	}
	
	public void setGreen() {
		this.r = 0;
		this.g = 255;
		this.b = 0;
	}
	
	public void setBlue() {
		this.r = 0;
		this.g = 0;
		this.b = 255;
	}
	
	public void setBlack() {
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	
	public void setWhite() {
		this.r = 255;
		this.g = 255;
		this.b = 255;
	}

}
