public class Point {
	private int x;
	private int y;	

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Point other) {
		final int dX = x - other.x;
		final int dY = y - other.y;
		return Math.sqrt(dX * dX + dY * dY);
	}

	public double distance() {
		return distance(new Point(0, 0));
	}

    @Override
    public String toString() {
        return "Point(x=" + this.x + ", y=" + this.y + ")";
    }    
}