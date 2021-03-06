import java.io.File;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("T:\\WorkSpace\\Level_3_Term_2\\AI\\Offline4\\src\\d-10-01.txt.txt");
        Grid tablero = new Grid(file);
        tablero.loadBoard();

        Player player = new Player();
        tablero.printBoard();
//        player.runAC(tablero);

//        player.runBC(tablero);
        player.ejecutarFC(tablero);
        tablero.printBoard();
    }

}
