public class Point {
    private final int x, y;
    private final double module;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.module = Math.sqrt(x*x + y*y);
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public double getModule() {
        return module;
    }
}