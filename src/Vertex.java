public class Vertex {
    private float x;
    private float y;
    private float z;
    ConsoleRenderer consoleRenderer;

    public Vertex(float x, float y, float z, ConsoleRenderer consoleRenderer){
        this.x = x;
        this.y = y;
        this.z = z;
        this.consoleRenderer = consoleRenderer;
    }

    public void scale(float scale){
        x = scale*x;
        y = scale*y;
        z = scale*z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public double getZWithAngle(){
        double cos = Math.cos(consoleRenderer.getvAngle());
        double sin = Math.sin(consoleRenderer.getvAngle());
        return (y*sin + z*cos);
    }

    public double getXWithAngle(){
        double cos = Math.cos(consoleRenderer.gethAngle());
        double sin = Math.sin(consoleRenderer.gethAngle());
        return (x*cos - getYWithVAngle()*sin);
    }

    private double getYWithVAngle(){
        double cos = Math.cos(consoleRenderer.getvAngle());
        double sin = Math.sin(consoleRenderer.getvAngle());
        return (y*cos - z*sin);
    }

    public double getYWithAngle(){
        double cos = Math.cos(consoleRenderer.gethAngle());
        double sin = Math.sin(consoleRenderer.gethAngle());
        return (getYWithVAngle()*cos + x*sin);
    }

    public void addToRaster(Raster raster){
        raster.setPos((int)Math.round(getXWithAngle()), (int) Math.round(getZWithAngle()), getYWithAngle(),"()");
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

