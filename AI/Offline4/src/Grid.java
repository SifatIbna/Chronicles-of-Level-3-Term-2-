import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Grid {

    private File file;
    private int[][] m_board;

    
    public Grid(File file) {
        this.file = file;
        m_board = new int[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                m_board[i][j] = 0;
            }
        }

    }

    public int getCell(int i, int j) {
        return m_board[i][j];
    }

    public void setCell(int value, int row, int col) {
        m_board[row][col] = value;
    }

    public void loadBoard() {
        FileReader fr = null;
        String line;
        int i;

        try {
            fr = new FileReader(file.getPath());
            BufferedReader bf = new BufferedReader(fr);
            try {
                i = 0;
                while ((line = bf.readLine()) != null) {
                    String[] numeros = line.split(" "); // get the numbers from the line

                    for (int j = 0; j < numeros.length; j++) {
//                        System.out.println(Integer.parseInt(numeros[j]));
                        m_board[i][j] = Integer.parseInt(numeros[j]); // inserts the numbers into the board
                    }
                    i++;
                }
            } catch (IOException e1) {
                System.out.println("Error reading file:" + file.getName());
            }
        } catch (FileNotFoundException e2) {
            System.out.println("Error opening file: " + file.getName());
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e3) {
                System.out.println("Error closing file: " + file.getName());
            }
        }
    }

    public void printBoard(){
        for(int i=0;i<10;i++){
            for (int j = 0;j<10;j++){
                System.out.print(this.m_board[i][j]+" ");

            }
            System.out.println();
        }
    }
}
