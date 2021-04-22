import de.javagl.obj.FloatTuple;

public class Face {
    Vertex vertex1;
    Vertex vertex2;
    Vertex vertex3;

    public Face(Vertex vertex1, Vertex vertex2, Vertex vertex3){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    public void addToRaster(Raster raster){
        double x1 = vertex1.getXWithAngle();
        double x2 = vertex2.getXWithAngle();
        double x3 = vertex3.getXWithAngle();

        double y1 = vertex1.getZWithAngle();
        double y2 = vertex2.getZWithAngle();
        double y3 = vertex3.getZWithAngle();

        double depth1 = vertex1.getYWithAngle();
        double depth2 = vertex2.getYWithAngle();
        double depth3 = vertex3.getYWithAngle();

        double xAMin = Math.min(x1, x2);
        double xAMax = Math.max(x1, x2);
        int xASteps = (int) Math.round(xAMax - xAMin);

        double yAMin = Math.min(y1, y2);
        double yAMax = Math.max(y1, y2);
        int yASteps = (int) Math.round(yAMax - yAMin);

        double xBMin = Math.min(x1, x3);
        double xBMax = Math.max(x1, x3);
        int xBSteps = (int) Math.round(xBMax - xBMin);

        double yBMin = Math.min(y1, y3);
        double yBMax = Math.max(y1, y3);
        int yBSteps = (int) Math.round(yBMax - yBMin);

        int steps = Math.max(Math.max(xASteps, yASteps), Math.max(xBSteps, yBSteps))*2;


        double NAx = x2 - x1;
        double NAy = y2 - y1;
        double NAz = depth2 - depth1;
        double NBx = x3 - x1;
        double NBy = y3 - y1;
        double NBz = depth3 - depth1;

        double Nx = NAy * NBz - NAz * NBy;
        double Ny = NAz * NBx - NAx * NBz;
        double Nz = NAx * NBy - NAy * NBx;

        String render = getRender(Nx, Ny, Nz);

        for (int step = 1; step < steps; step++) {
            double Ax = Math.round(x1 + step*((x2 - x1)/steps));
            double Ay = Math.round(y1 + step*((y2 - y1)/steps));
            Double Adepth = depth1 + step*((depth2 - depth1)/steps);

            double Bx = Math.round(x1 + step*((x3 - x1)/steps));
            double By = Math.round(y1 + step*((y3 - y1)/steps));
            Double Bdepth = depth1 + step*((depth3 - depth1)/steps);

            drawFillLine(raster, Ax, Bx, Ay, By, Adepth, Bdepth, render);
        }
    }

    public void drawFillLine(Raster raster, double Ax, double Bx, double Ay, double By, double Adepth, double Bdepth, String render){
        double xMin = Math.min(Ax, Bx);
        double xMax = Math.max(Ax, Bx);
        int xSteps = (int) Math.round(xMax - xMin);

        double yMin = Math.min(Ay, By);
        double yMax = Math.max(Ay, By);
        int ySteps = (int) Math.round(yMax - yMin);

        int steps = Math.max(xSteps, ySteps)*2;


        for (int step = 0; step <= steps; step++) {
            int x = (int) Math.round(Ax + step*((Bx - Ax)/steps));
            int y = (int) Math.round(Ay + step*((By - Ay)/steps));
            Double depth = Adepth + step*((Bdepth - Adepth)/steps);
            raster.setPos(x, y, depth,  render);

        }
    }

    private String getRender(double x, double y, double z){
        //String[] strings = new String[]{"..", "::", "()", "OO", "@@"};
        String grays = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'.";
        double calc = 1 - Math.abs(y/(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))));

        //return strings[(int) Math.round(calc*(strings.length-1))];
        String ret = String.valueOf(grays.charAt((int) Math.round((1-calc)*(grays.length()-1))));
        return ret + ret;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Vertex getVertex3() {
        return vertex3;
    }
}
