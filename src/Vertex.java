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

    public float getXWithAngle(){
        double cos = Math.cos(consoleRenderer.gethAngle());
        double sin = Math.sin(consoleRenderer.gethAngle());
        return (float) (x*cos - getYWithVAngle()*sin);
    }

    public float getYWithVAngle(){
        double cos = Math.cos(consoleRenderer.getvAngle());
        double sin = Math.sin(consoleRenderer.getvAngle());
        return (float) (y*cos + z*sin);
    }

    public float getYWithAngle(){
        double cos = Math.cos(consoleRenderer.gethAngle());
        double sin = Math.sin(consoleRenderer.gethAngle());
        return (float) (x*sin + getYWithVAngle()*cos);
    }

    public void addToRaster(Raster raster){
        raster.setPos(Math.round(getXWithAngle()), Math.round(getZWithAngle()), getYWithAngle(),"@@");
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

