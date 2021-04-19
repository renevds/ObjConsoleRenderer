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
        return (float) (Math.atan2(Math.abs(vertex1.getZWithAngle() - vertex2.getZWithAngle()), Math.abs(vertex1.getXWithAngle() - vertex2.getXWithAngle())) + Math.PI/2);
    }

    public String toString() {
        int angle = (int) Math.round((getAngle()/ Math.PI)*4);
        return charArr[(angle+8)%8];
    }

    public void addToRaster(Raster raster){
        double x1 = vertex1.getXWithAngle();
        double x2 = vertex2.getXWithAngle();

        double y1 = vertex1.getZWithAngle();
        double y2 = vertex2.getZWithAngle();

        double depth1 = vertex1.getYWithAngle();
        double depth2 = vertex2.getYWithAngle();

        double xMin = Math.min(x1, x2);
        double xMax = Math.max(x1, x2);
        int xSteps = (int) Math.round(xMax - xMin);

        double yMin = Math.min(y1, y2);
        double yMax = Math.max(y1, y2);
        int ySteps = (int) Math.round(yMax - yMin);

        int steps = Math.max(xSteps, ySteps);

        for (int step = 1; step < steps; step++) {
                int x = (int) Math.round(x1 + step*((x2 - x1)/steps));
                int y = (int) Math.round(y1 + step*((y2 - y1)/steps));
                Double depth = depth1 + step*((depth2 - depth1)/steps);
                raster.setPos(x, y, depth,  toString());

        }
    }
}
