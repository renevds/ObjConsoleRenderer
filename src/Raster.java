import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Raster {
    String[][] raster;
    int size;
    PrintWriter printWriter;

    public Raster(int size) throws UnsupportedEncodingException {
        this.size = size;
        raster = new String[size][size];
        for (String[] line: raster) {
            Arrays.fill(line, "  ");
        }
        printWriter = new PrintWriter(new OutputStreamWriter(System.out, "Cp850"), false);
    }

    public void setPos(int x, int y, String c){
        //System.out.println("draw " + c + "at x: " + x + " y: " + y);
        try {
            raster[size/2  + y][size/2 + x] = c;
        }
        catch (ArrayIndexOutOfBoundsException e){

        }

    }

    public void print() throws IOException {
        String print = "";
        for(String[] line: raster){
            for (String c: line){
                print += c;
            }
            print += "\n";
        }
        printWriter.print(print);
    }
}
