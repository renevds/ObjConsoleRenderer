
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.

public class Raster {
    Pixel[][] raster;
    int width;
    int height;
    PrintWriter printWriter;

    public Raster(int width, int height) throws UnsupportedEncodingException {
        this.width = width;
        this.height = height;
        raster = new Pixel[height][width];
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
            raster[height/2 + y][width/2 + x].setC(c, depth);
        }
        catch (ArrayIndexOutOfBoundsException e){

        }

    }

    public void print() throws IOException, InterruptedException {
        StringBuilder print = new StringBuilder();
        for(Pixel[] line: raster){
            for (Pixel c: line){
                print.append(c.toString());
            }
            print.append("\n");
        }
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        printWriter.print(colorize(print.toString(), Attribute.GREEN_TEXT()));
        printWriter.flush();
    }

    public void reset(){
        for (Pixel[] col: raster){
            for (Pixel pixel: col){
                pixel.reset();
            }
        }
    }
}
