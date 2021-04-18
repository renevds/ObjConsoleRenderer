import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleRenderer consoleRenderer = new ConsoleRenderer("./data/" + args[0] + ".obj", Integer.parseInt(args[1]));
        consoleRenderer.animate();
    }
}
