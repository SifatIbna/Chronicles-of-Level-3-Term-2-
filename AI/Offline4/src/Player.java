import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Player {

    HashMap<Cell, ArrayList<Integer>> map = new HashMap<>();    // map linking variables(cells) to domains
    ArrayList<Pair> qArray = new ArrayList<>(); // Q contais a set of arcs (pairs of (cell, domain)
    boolean ac3 = false; // Boolean indicating if AC3 has been executed
    int recursiveCalls = 0; // variable to measure the ammount of recursive calls 

    private void fillMap(Grid tablero) { // reads the board and fill auxiliary map
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCell(i, j) == 0) {
                    Cell c = new Cell(i, j);
                    ArrayList<Integer> elements = fillDomain();
                    map.put(c, elements);
                } else {
                    Cell c = new Cell(i, j);
                    ArrayList<Integer> elements = new ArrayList<>();
                    elements.add(tablero.getCell(i, j));
                    map.put(c, elements);
                }
            }
        }
    }

    private ArrayList<Integer> fillDomain() {  // fills the domain with the values 1-9
        ArrayList<Integer> elements = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            elements.add(i);
        }

        return elements;
    }

    private void printDomains() {    //prints the domains
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("Domain Cell (" + i + "," + j + "): ");
                Cell celda = new Cell(i, j);
                ArrayList<Integer> domain = map.get(celda);
                for (int z = 0; z < domain.size(); z++) {
                    System.out.print(domain.get(z) + " ");
                }
                System.out.print("\n");
            }
        }
    }

    private void fillQ() { // fills Q in the first iteration of the algorithm
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = new Cell(i, j);
                addToQ(cell, true);
            }
        }
    }

    private boolean existsInQ(Pair pair) {  // check if the arc already exists in Q
        for (Pair p : qArray) {
            if (p.equals(pair)) {
                return true;
            }
        }
        return false;
    }

    private void addToQ(Cell c, boolean fill) { // adds arcs to Q, boolean fill controls if the fillup is in the first execution
        addRow(c, fill);                        // in that case, it will add all the possible arcs
        addColumn(c, fill);                     // columna
//        addMatrix(c, fill);
    }

    private void addRow(Cell c, boolean fill) {
        for (int i = 0; i < 10; i++) {
            if (i != c.col) {
                Pair pair = null;
                Cell newCell = new Cell(c.row, i);
                if (fill) {
                    pair = new Pair(c, newCell);
                } else {
                    pair = new Pair(newCell, c);
                }
                if (!existsInQ(pair)) {
                    qArray.add(pair);
                }
            }
        }
    }

    private void addColumn(Cell c, boolean fill) {
        for (int i = 0; i < 10; i++) {
            if (i != c.row) {
                Pair pair = null;
                Cell newCell = new Cell(i, c.col);
                if (fill) {
                    pair = new Pair(c, newCell);
                } else {
                    pair = new Pair(newCell, c);
                }
                if (!existsInQ(pair)) {
                    qArray.add(pair);
                }
            }
        }
    }



    public boolean AC3(Grid board) {
        ac3 = true; // set to true to indicate AC3 has been executed
        boolean changed;
        boolean solutionExists = true;
        fillMap(board); // genereate auxiliary map
        fillQ();
        while (qArray.size() > 0 && solutionExists) { // while Q != empty and solution exists
            Pair pair = qArray.get(0); // read from Q
            qArray.remove(pair); // delete the pair from Q
            changed = false;
            //System.out.println("Debug checking " + pair.left().hashCode() + "with " + pair.right().hashCode());
            ArrayList<Integer> valuesCell1 = map.get(pair.left());
            ArrayList<Integer> valuesCell2 = map.get(pair.right());

            for (int i = valuesCell1.size() - 1; i >= 0; i--) { // for each vk from Dk
                if (deleteValue(valuesCell2, valuesCell1.get(i))) { // if (vk, Dm) doesnt satisfy the constraint
                    valuesCell1.remove(valuesCell1.get(i)); // delete (vk, Dk)
                    changed = true;
                }
            }

            if (valuesCell1.isEmpty()) {    // if the domain is empty, there is no solution
                solutionExists = false;
            }

            if (changed) {
                map.put(pair.left(), valuesCell1); // if we deleted values, we update the domain of the cell  
                addToQ(pair.left(), false); // add new restrictions to Q
            }
        }
        printDomains(); // print the domains of the cells

        if (!solutionExists) { // exit if there is no solution
            System.out.println("There is no solution for this problem");
            return false;
        }

        return true;
    }



    private boolean deleteValue(ArrayList<Integer> values, int number) {
        for (int value : values) {
            if (value != number) {
                return false;
            }
        }

        return true;
    }


    private boolean backtrack(Grid board, Cell cell) {
        recursiveCalls++;
        if (endOfGrid(board)) {
            return true;
        }

        ArrayList<Integer> valuesCell = map.get(cell); // get the domain of the cell on which im operating 
        int value = 0;

        for (int i = 0; i < valuesCell.size(); i++) { // Iterate through the cells domain
            value = valuesCell.get(i);                  // get a value from the domain
            board.setCell(value, cell.row, cell.col);  // set the value onto the board
            if (isValid(board, cell, value)) {            // check for errors
                if (backtrack(board, cell.nextCell(board))) {   // recursive call
                    return true;
                }
            }
        }
        board.setCell(0, cell.row, cell.col);  // restores the cell value

        return false;
    }

    private boolean endOfGrid(Grid tablero) { // returns true if the whole grid is full
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCell(i, j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(Grid tablero, Cell cell, int value) { // Checks if the inserted value is correct  
        for (int i = 0; i < 10; i++) { // check for repeated values in the row
            if (i != cell.col) {
                if (tablero.getCell(cell.row, i) == value) {
                    return false;
                }
            }
        }

        for (int j = 0; j < 10; j++) { // check for repeated values in the column
            if (j != cell.row) {
                if (tablero.getCell(j, cell.col) == value) {
                    return false;
                }
            }
        }


        return true;
    }


//////////////////////////////////////////////////////////////////////////////////
    // Forward Checking
////////////////////////////////////////////////////////////////////////////////// 
    public boolean forwardChecking(Grid board, Cell cell) { 
        recursiveCalls++;
        if (endOfGrid(board)) { // if all the numbers of the board != 0
            return true;
        }

        DeleteQueue delQueue = new DeleteQueue();   // create delete queue
        delQueue.fcAddToDelete(cell);   // fill the queue with posible variables (row, col & matrix) that must update their domain
        ArrayList<Integer> valuesCell = map.get(cell); // save the domain of the variable on which im operating
        int value = 0;

        for (int i = 0; i < valuesCell.size(); i++) {  // for each value of the domain, I will delete the corresponding value from the domain of the variables with restrictions towards V
            value = valuesCell.get(i);
            delQueue.executeDeletion(value, map); // delete from the domains
            if (delQueue.checkForEmptyDomains(map)) { // check for empty domains
                delQueue.restoreDomains(value, map); // If any domain is empty, restore all domains
            } else {
                ArrayList<Integer> newDomain = new ArrayList<>();
                newDomain.add(value);
                map.put(cell, newDomain);                           // update the domain of the varible on which im operating
                board.setCell(value, cell.row, cell.col);             // set the new value on the board
                if (forwardChecking(board, cell.nextCell(board))) { // recursive call
                    return true;
                } else {
                    delQueue.restoreDomains(value, map);          // restore all domains
                    map.put(cell, valuesCell);              // restore the domain of the cell on which I operated
                }
            }
        }
        board.setCell(0, cell.row, cell.col);   // restore original value on the board

        return false;
    }

//////////////////////////////////////////////////////////////////////////////////
    // Interface Calls
//////////////////////////////////////////////////////////////////////////////////    
    public boolean runBC(Grid board) {
        if (!ac3) // if AC3 did not run, I load the map
        {
            fillMap(board);
        }
        Cell cell = new Cell();

        long time1 = System.nanoTime();
        backtrack(board, cell.nextCell(board)); // call it with the first empty cell
        long time2 = System.nanoTime();
        long timeSpent = time2 - time1;
        System.out.println("Time elapsed(Backtracking): " + TimeUnit.NANOSECONDS.toMillis(timeSpent) + "ms");
        System.out.println("Recursive calls: " + recursiveCalls);

        return true;
    }

    public boolean runAC(Grid board) {
        long time1 = System.nanoTime();
        boolean solutionExists = AC3(board);
        long time2 = System.nanoTime();
        long timeSpent = time2 - time1;
        System.out.println("Time elapsed(AC3): " + TimeUnit.NANOSECONDS.toMillis(timeSpent) + "ms");

        return solutionExists;
    }

    public boolean ejecutarFC(Grid tablero) {

        if (!ac3) // if AC3 did not run, I load the map
        {
            fillMap(tablero);
        }

        Cell cell = new Cell();

        long time1 = System.nanoTime();
        forwardChecking(tablero, cell.nextCell(tablero)); // call it with the first empty cell
        long time2 = System.nanoTime();
        long timeSpent = time2 - time1;
        System.out.println("Time elapsed(Forward Checking): " + TimeUnit.NANOSECONDS.toMillis(timeSpent) + "ms");
        System.out.println("Recursive calls: " + recursiveCalls);

        return true;
    }
}
