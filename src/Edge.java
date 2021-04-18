public class Edge {
    private Vertex vertex1;
    private Vertex vertex2;
    private String[] charArr;

    public Edge(Vertex vertex1, Vertex vertex2){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        charArr = new String[]{"||", "//", "--", "\\\\", "||", "//", "--", "\\\\"};
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    private float getAngle(){
        return (float) (Math.atan((vertex1.getZ() - vertex2.getZ())/(vertex1.getX() - vertex2.getX())) + Math.PI/2);
    }

    public String toString() {
        int angle = (int) Math.round((getAngle()/ Math.PI)*4);
        return charArr[(angle+8)%8];
    }

    public void addToRaster(Raster raster){

        float xMin = Math.round(Math.min(vertex1.getX(), vertex2.getX()));
        float xMax = Math.round(Math.max(vertex1.getX(), vertex2.getX()));
        int xSteps = Math.round(xMax - xMin);

        float yMin = Math.round(Math.min(vertex1.getZWithAngle(), vertex2.getZWithAngle()));
        float yMax = Math.round(Math.max(vertex1.getZWithAngle(), vertex2.getZWithAngle()));
        int ySteps = Math.round(yMax - yMin);

        int steps = Math.max(xSteps, ySteps);

        for (int step = 0; step < steps; step++) {
                int x = Math.round(xMin + step*((xMax - xMin)/steps));
                int y = Math.round(yMin + step*((yMax - yMin)/steps));
                raster.setPos(x, y, toString());

        }
    }
}
