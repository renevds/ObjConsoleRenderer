import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjFace;
import de.javagl.obj.ObjReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsoleRenderer {
    Obj obj;
    int size;
    float scale;
    List<Vertex> vertices;
    List<Edge> edges;

    double vAngle = Math.toRadians(90);
    double hAngle = Math.toRadians(0);

    public ConsoleRenderer(String objPath, int size ) throws IOException {
        InputStream objInputStream = new FileInputStream(objPath);
        obj = ObjReader.read(objInputStream);
        this.size = size;
        setScale();
    }

    public double getvAngle() {
        return vAngle;
    }

    public double gethAngle() {
        return hAngle;
    }

    public Vertex getVertexAtPos(FloatTuple floatTuple){
        for (Vertex vertex: vertices){
            if (floatTuple.getX()*scale == vertex.getX() && floatTuple.getY()*scale == vertex.getY() && floatTuple.getZ()*scale == vertex.getZ()){
                return vertex;
            }
        }
        return null;
    }


    private Vertex floatTupleToVertex(FloatTuple floatTuple){
        return new Vertex(floatTuple.getX(), floatTuple.getY(), floatTuple.getZ(), this);
    }

    public void animate() throws IOException, InterruptedException {
        while (true) {
            show();
            Thread.sleep(10);
            vAngle += Math.toRadians(0);
            hAngle += Math.toRadians(5);
        }
    }

    public void setScale(){
        float maxLargest = 0;

        for (int i = 0; i < obj.getNumVertices(); i++) {
            FloatTuple floatTuple = obj.getVertex(i);
            float largest = Math.max(Math.max(Math.abs(floatTuple.getX()), Math.abs(floatTuple.getY())), Math.abs(floatTuple.getZ()));
            if(largest > maxLargest){
                maxLargest = largest;
            }
        }

        scale = ((size/maxLargest)/2);
    }

    public void show() throws IOException, InterruptedException {
        Raster raster = new Raster(size);
        vertices = new ArrayList<>();
        edges = new ArrayList<>();


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
                    if (vertex1 != vertex2){
                        Edge edge = new Edge(vertex1, vertex2);
                        edges.add(edge);
                        edge.addToRaster(raster);
                    }
                }
            }
        }

        for(Vertex vertex: vertices){
            vertex.addToRaster(raster);
        }

        raster.print();
        System.out.println(scale);
    }
}
