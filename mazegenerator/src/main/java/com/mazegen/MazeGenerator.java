package com.mazegen;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
public class MazeGenerator {
    
    int numVert;
    int width;
    int height;
    int currentCell;
    AdjMatrix aM;

    Stack<Integer> stack = new Stack<>();
    //ArrayList<Integer> visited = new ArrayList<>();
    // Pick inital cell, put on stack
    Random r = new Random();
    
    // Get a AdjMatrix and preform a Maze making algorith
    public MazeGenerator(AdjMatrix adjMatrix){
        this.numVert = adjMatrix.getNumVert();
        this.width = adjMatrix.getWidth();
        this.height = adjMatrix.getHeight();
        this.aM = adjMatrix;
    }

    public MazeGenerator(){

    }

    public void setAdjMatrix(AdjMatrix am){
        this.numVert = am.getNumVert();
        this.width = am.getWidth();
        this.height = am.getHeight();
        this.aM = am;
    }
    // DFS    
    // Pick inital cell, put on stack
    // while stack not empty
    
    // pop cell from stack make current cell
    // if current cell has unvisited neighbours push current cell to stack
    // pick unvited cell
    // choose one unvisited neighbour
    // remove the wall between the current cell and chosen cell(Adj Maxtrix)
    // mark the chosen cell as visited and push it to the stack

    public AdjMatrix createMaze() {

        System.out.println("start");
        // Pick inital cell, put on stack
        ArrayList<Integer> visited = new ArrayList<>();
        this.currentCell = r.nextInt(numVert);
        stack.push(currentCell);
        visited.add(currentCell);
        while (!stack.empty() && visited.size() <= numVert){
            
            currentCell = stack.pop();
            // Check i + 1, i - 1, i + width, i - width if adjacent, then check if visited.
            ArrayList<Integer> validNeighbours = new ArrayList<>();
            //System.out.println("validNeighbours empty: "+ validNeighbours);
            int currentCellX = currentCell%width;
            int currentCellY = currentCell/width;
            if (currentCellX<width-1){
                if (!visited.contains(currentCell+1)){
                    validNeighbours.add(currentCell+1);
                }
            }

            if (currentCellX > 0){
                // Not checking if the cell is on the other side of the wall. I dont actually think this is the main problem(might be checking stuff twice)
                if (!visited.contains(currentCell-1)){
                    validNeighbours.add(currentCell-1);
                }
            }

            if (currentCellY > 0){
                
                if (!visited.contains((currentCell-width))){
                    validNeighbours.add(currentCell-width);
                }
            }

            if (currentCellY < height-1){
                
                if (!visited.contains(currentCell+width)){
                    validNeighbours.add(currentCell+width);
                }
            }

            //System.out.println("visited: "+ visited);
            // Get random of the unvisited cells
            //System.out.println("validNeighbours: " + validNeighbours);
            if (validNeighbours.size() > 0){
                //push current cell
                stack.push(currentCell);
                // find next current cell randomly
                int validNeighboursRnd = r.nextInt(validNeighbours.size());
                aM.makeAdjcent(currentCell, validNeighbours.get(validNeighboursRnd));
                currentCell = validNeighbours.get(validNeighboursRnd);

                // push new current cell
                stack.push(currentCell);
                //visit new current cell
                visited.add(currentCell);
            }

        }
        return aM;
    }
    
}
