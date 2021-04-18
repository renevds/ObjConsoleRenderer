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

    public float getZWithAngle(){
        double cos = Math.cos(consoleRenderer.getvAngle());
        double sin = Math.sin(consoleRenderer.getvAngle());
        return (float) (z*cos - y*sin);
    }

    public float getYWithAngle(){
        double cos = Math.cos(consoleRenderer.getvAngle());
        double sin = Math.sin(consoleRenderer.getvAngle());
        return (float) (z*sin + y*cos);
    }

    public void addToRaster(Raster raster){
        raster.setPos(Math.round(x), Math.round(getZWithAngle()), "@@");
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

