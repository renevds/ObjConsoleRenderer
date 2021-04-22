import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleRenderer consoleRenderer = new ConsoleRenderer("./data/" + args[0] + ".obj", Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        if(args[1].equals("1")) {
            consoleRenderer.animate();
        }
        else {
            consoleRenderer.show();
            consoleRenderer.frame();
        }
    }
}
