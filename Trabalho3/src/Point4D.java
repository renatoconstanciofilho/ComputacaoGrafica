public final class Point4D {
	private double x;
	private double y;
	private double z;
	private double w;

	public Point4D() {
		this(0, 0, 0, 1);
	}
	
	public Point4D(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public double GetX() {
		return x;
	}
	
	public double GetY() {
		return y;
	}
	
	public double GetZ() {
		return z;
	}
	
	public double GetW() {
		return w;
	}

	public void SetX(double x) {
		this.x = x;
	}
	
	public void SetY(double y) {
		this.y = y;
	}
	
	public void SetZ(double z) {
		this.z = z;
	}

	public void SetW(double w) {
		this.w = w;
	}
	
}
