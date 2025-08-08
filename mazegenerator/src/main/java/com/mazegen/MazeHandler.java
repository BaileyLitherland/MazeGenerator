package com.mazegen;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeHandler{

    GraphicsContext gc; 

    // How many pixels between the edge of the canvas and the maze
    int GRID_MARGIN = 10;

    int selectedBox;

    int mazeStart=-1;
    int mazeEnd=-1;

    // How many boxes high and wide is each maze
    int mazeWidth;
    int mazeHeight;
    
    // How many pixles high and wide each maze box is
    double gridWidth;
    double gridHeight;

    MazeGenerator mazeGenerator;

    AdjMatrix adjM;

    MazeSolver solver;

    public MazeHandler(GraphicsContext gc){
        this.gc = gc;
        mazeGenerator = new MazeGenerator();
        solver = new DfsSolver();
    }

    // create nxm Adjacency Matrix
    public void createAdjacencyMatrix(int n, int m){
        adjM = new AdjMatrix(n, m);
        mazeWidth = n;
        mazeHeight = m;
    }

    // Generate Maze
    public void genMaze(){
        mazeGenerator.setAdjMatrix(adjM);
        mazeGenerator.createMaze();
    }

    //* Getters and Setters*/
    // private void setGridWidth(double gridWidth){
    //     this.gridWidth = gridWidth;
    // }

    // private void setGridHeight(double gridHeight){
    //     this.gridHeight = gridHeight;
    // }

    public void setMazeStart(double x,double y){
        System.out.println(x + " " + y);
        //System.out.println("gridHeight * mazeHeight: " + gridHeight * mazeWidth + " gridWidth * mazeWidth: " + gridWidth * mazeWidth + " x: " + x + " y: " + y);
        if (x > GRID_MARGIN && x < (mazeWidth * gridWidth)+GRID_MARGIN + gridWidth *.5 
            && y > GRID_MARGIN && y < (mazeHeight * gridHeight)+GRID_MARGIN + gridHeight *.5){
            
                int previousSelectedBox = mazeStart;

                double boxXCart = Math.floor((x - (gridWidth*.5) - GRID_MARGIN)/gridWidth + 0.5);
                double boxYCart = Math.floor((y - (gridHeight*.5) - GRID_MARGIN)/gridHeight + 0.5);
                // System.out.println(boxXCart + " " + boxYCart);
                mazeStart = (int)boxXCart + (int)boxYCart * mazeWidth;
 
                clearBox(previousSelectedBox);
                highlightBox(mazeStart, Color.RED,.80);

        }
        // double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        // double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
    }

    public void setMazeEnd(double x,double y){

        if (x > GRID_MARGIN && x < (mazeWidth * gridWidth)+GRID_MARGIN + gridWidth *.5 
            && y > GRID_MARGIN && y < (mazeHeight * gridHeight)+GRID_MARGIN + gridHeight *.5){
            int previousSelectedBox = mazeEnd;

            double boxXCart = Math.floor((x - (gridWidth*.5) - GRID_MARGIN)/gridWidth + 0.5);
            double boxYCart = Math.floor((y - (gridHeight*.5) - GRID_MARGIN)/gridHeight + 0.5);

            mazeEnd = (int)boxXCart + (int)boxYCart * mazeWidth;
            clearBox(previousSelectedBox);
            highlightBox(mazeEnd, Color.PURPLE, .80);

        }
        // double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        // double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
    }
    
    // Clear box of any highlight
    private void clearBox(int selectedBox){
        
        int boxXCart = getBoxXCartFromIndex(selectedBox);
        int boxYCart = getBoxYCartFromIndex(selectedBox);

        double boxX = getBoxX(boxXCart);
        double boxY = getBoxY(boxYCart);


        gc.clearRect(boxX - 0.5 * (gridWidth) +1 , boxY - 0.5 * (gridHeight) +1, gridWidth -2 , gridHeight -2);

      
    }
    // Highlights the Selected Box
    private void highlightBox(int highlightBox, Color boxColour, double sizePercentage){
        
        //System.out.println("Selected Box: "+ highlightBox);
        int boxXCart = getBoxXCartFromIndex(highlightBox);
        int boxYCart = getBoxYCartFromIndex(highlightBox);
       
        //System.out.println("Box XCart and BoxYCart: "+ boxYCart + " "+ boxXCart);
        double boxX = getBoxX(boxXCart);
        double boxY = getBoxY(boxYCart);
        //System.out.println("Box x and Box y: "+ boxX + " "+ boxY);

        gc.setFill(boxColour);

        gc.fillRect(boxX - 0.5 * (gridWidth*sizePercentage), boxY - 0.5 * (gridHeight*sizePercentage), gridWidth*sizePercentage, gridHeight*sizePercentage);

        gc.setFill(Color.BLACK);        
    }

    private int getBoxXCartFromIndex(int boxIndex){
        // Gets the x coordinate of the box index 
        return boxIndex%mazeWidth;
    }

    private int getBoxYCartFromIndex(int boxIndex){
        // Gets the x coordinate of the box index 
        return boxIndex/mazeWidth;
    }

    private double getBoxX(int boxXCart){
        return (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*.5;
    }

    private double getBoxY(int boxYCart){
        return (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*.5; 
    }
    // Draws the Maze Grid
    public void drawMaze(){
        
        // The size of the canvas
        int canvasHeight = 600;
        int canvasWidth = 600;

        // Clear the current canvas
        gc.clearRect(0, 0, 600, 600);

        //Define how wide and high each box is.
        gridWidth = (canvasWidth-GRID_MARGIN*2)/(mazeWidth);
        gridHeight = (canvasHeight-GRID_MARGIN*2)/(mazeHeight);

        // System.out.println(gridWidth + " is width" + gridHeight + " is height");
        // System.out.println("adjm Height " + adjM.getHeight() + " adjm width:" + adjM.getWidth());

        int numVert = adjM.getNumVert();

        boolean[][] am = adjM.getAdjMatrix(); 
        for (int i = 0; i < numVert; i++) {
            // Get a cartesian coordinate of each box
            
            int boxXCart = getBoxXCartFromIndex(i);
            int boxYCart = getBoxYCartFromIndex(i);
            // Turn the cartesian coordinate into an actual x and y
            double boxX = getBoxX(boxXCart);
            double boxY = getBoxY(boxYCart);
            // This next line draws the coordinates of each box on the canvas
            gc.strokeText(Integer.toString(boxXCart)+ " , " + Integer.toString(boxYCart), boxX, boxY);
            //gc.strokeText(Integer.toString(i), boxX, boxY);
            

            // Draw Border Lines

            if(boxXCart == mazeWidth-1){
                gc.setStroke(Color.RED);
                gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
                gc.setStroke(Color.BLACK);
            }
            if(boxXCart == 0){
                gc.setStroke(Color.RED);
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
                gc.setStroke(Color.BLACK);
            }

            if(boxYCart == 0){
                gc.setStroke(Color.RED);
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY-.5*gridHeight);
                gc.setStroke(Color.BLACK);
            }

            if(boxYCart == mazeHeight-1){
                gc.setStroke(Color.RED);
                gc.strokeLine(boxX-.5*gridWidth, boxY+.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
                gc.setStroke(Color.BLACK);
            } 
            
            // Draw maze walls

            // Draw Left hand wall
            if (boxXCart >= 1){    
                if (am[i][i-1] == false){
                    gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
                }
            }
            // Draw right hand wall
            // if (boxXCart < mazeWidth-1){
            //     if (am[i][i+1] == false){
            //         gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            //     }
            // }
            // Draw bottom wall
            if (boxYCart < mazeHeight-1){
                if (!adjM.isAdjacent(i, i+mazeWidth)){
                    gc.strokeLine(boxX+.5*gridWidth, boxY+.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
                }
            }
        }

        gc.strokeLine(canvasHeight, canvasWidth, canvasHeight, canvasWidth);

    }

    public void solve(){
        for (int i = 0; i < mazeHeight*mazeWidth; i++) {
            clearBox(i);
        }
        highlightBox(mazeStart, Color.RED, .80);
        highlightBox(mazeEnd, Color.PURPLE, .80);
        ArrayList<Integer> results = solver.solveMaze(adjM, mazeStart, mazeEnd);
        for (Integer integer : results) {
            highlightBox(integer, Color.GOLDENROD,.5);
        }
        
    }
}