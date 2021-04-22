import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsoleRenderer {
    Obj obj;
    int width;
    int height;
    float scale;
    List<Vertex> vertices;
    List<Edge> edges;
    List<Face> faces;
    Raster raster;

    int mode;

    double vAngle = Math.toRadians(0);
    double hAngle = Math.toRadians(0);

    public ConsoleRenderer(String objPath, int mode, int width, int height) throws IOException {
        InputStream objInputStream = new FileInputStream(objPath);
        obj = ObjReader.read(objInputStream);
        this.width = width;
        this.height = height;
        this.mode = mode;
        setScale();
    }

    public double getvAngle() {
        return vAngle;
    }

    public double gethAngle() {
        return hAngle;
    }

    public Vertex getVertexAtPos(FloatTuple floatTuple) {
        for (Vertex vertex : vertices) {
            if (floatTuple.getX() * scale == vertex.getX() && floatTuple.getY() * scale == vertex.getY() && floatTuple.getZ() * scale == vertex.getZ()) {
                return vertex;
            }
        }
        return null;
    }


    private Vertex floatTupleToVertex(FloatTuple floatTuple) {
        return new Vertex(floatTuple.getX(), floatTuple.getY(), floatTuple.getZ(), this);
    }

    public void animate() throws IOException, InterruptedException {
        show();
        for (int i = 0; i < 60 || true; i++) {
            frame();
            vAngle += Math.toRadians(2);
            hAngle += Math.toRadians(2);
            //Thread.sleep(400);
        }
    }

    public void setScale() {
        double maxLargest = 0;

        for (int i = 0; i < obj.getNumVertices(); i++) {
            FloatTuple floatTuple = obj.getVertex(i);
            double largest = Math.sqrt(Math.pow(floatTuple.getX(), 2) + Math.pow(floatTuple.getY(), 2) + Math.pow(floatTuple.getZ(), 2));
            if (largest > maxLargest) {
                maxLargest = largest;
            }
        }

        scale = (float) ((Math.min(width, height) / maxLargest)/2);
    }

    public void show() throws IOException {
        raster = new Raster(width, height);
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        faces = new ArrayList<>();

        for (int i = 0; i < obj.getNumVertices(); i++) {
            FloatTuple floatTuple = obj.getVertex(i);
            Vertex vertex = floatTupleToVertex(floatTuple);
            vertices.add(vertex);
            vertex.scale(scale);
        }

        for (int i = 0; i < obj.getNumFaces(); i++) {
            ObjFace objFace = obj.getFace(i);
            for (int j = 0; j < objFace.getNumVertices(); j++) {
                Vertex vertex1 = getVertexAtPos(obj.getVertex(objFace.getVertexIndex(j)));
                for (int k = 0; k < objFace.getNumVertices(); k++) {
                    Vertex vertex2 = getVertexAtPos(obj.getVertex(objFace.getVertexIndex(k)));
                    if (vertex1 != vertex2) {
                        Edge edge = new Edge(vertex1, vertex2);
                        edges.add(edge);
                    }
                }
            }
        }

        for (int i = 0; i < obj.getNumFaces(); i++) {
            ObjFace objFace = obj.getFace(i);
            for (int j = 0; j < objFace.getNumVertices(); j++) {
                for (int k = 0; k < objFace.getNumVertices(); k++) {
                    for (int l = 0; l < objFace.getNumVertices(); l++) {
                        Vertex vertex1 = getVertexAtPos(obj.getVertex(objFace.getVertexIndex(j)));
                        Vertex vertex2 = getVertexAtPos(obj.getVertex(objFace.getVertexIndex(k)));
                        Vertex vertex3 = getVertexAtPos(obj.getVertex(objFace.getVertexIndex(l)));
                        if(vertex1 != vertex2 && vertex2 != vertex3 && vertex1 != vertex3) {
                            if(findFace(vertex1, vertex2, vertex3) == null) {
                                faces.add(new Face(vertex1, vertex2, vertex3));
                            }
                        }
                    }
                }
            }
        }
    }

    private Face findFace(Vertex vertex1, Vertex vertex2, Vertex vertex3){
        for (Face face: faces){
            if(face.getVertex1() == vertex1 || face.getVertex2() == vertex1 || face.getVertex3() == vertex1){
                if(face.getVertex1() == vertex2 || face.getVertex2() == vertex2 || face.getVertex3() == vertex2){
                    if(face.getVertex1() == vertex3 || face.getVertex2() == vertex3 || face.getVertex3() == vertex3){
                        return face;
                    }
                }
            }
        }
        return null;
    }

    public void frame() throws IOException, InterruptedException {
        raster.reset();
        if(mode == 2) {
            for (Edge edge : edges) {
                edge.addToRaster(raster);
            }
            for (Vertex vertex : vertices) {
                vertex.addToRaster(raster);
            }
        }else {
            for (Face face : faces) {
                face.addToRaster(raster);
            }
        }
        raster.print();
        System.out.println(scale);
        System.out.println(vAngle);
        System.out.println(hAngle);
        System.out.println("faces: " + faces.size());
    }
}
