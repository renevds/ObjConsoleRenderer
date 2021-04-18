
import com.diogonunes.jcolor.Attribute;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Raster {
    Pixel[][] raster;
    int size;
    PrintWriter printWriter;

    public Raster(int size) throws UnsupportedEncodingException {
        this.size = size;
        raster = new Pixel[size][size];
        for (Pixel[] line: raster) {
            for (int i = 0; i < line.length; i++) {
                line[i] = new Pixel();
            }
        }
        printWriter = new PrintWriter(new OutputStreamWriter(System.out, "Cp850"));
    }

    public void setPos(int x, int y, Float depth, String c){
        //System.out.println("draw " + c + "at x: " + x + " y: " + y);
        try {
            raster[size/2  + y][size/2 + x].setC(c, depth);
        }
        catch (ArrayIndexOutOfBoundsException e){

        }

    }

    public void print() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        String print = "";
        for(Pixel[] line: raster){
            for (Pixel c: line){
                print += c.toString();
            }
            print += "\n";
        }
        printWriter.print(colorize(print, Attribute.GREEN_TEXT()));
        printWriter.flush();
    }
}
