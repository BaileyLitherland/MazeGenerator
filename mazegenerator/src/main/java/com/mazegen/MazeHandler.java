package com.mazegen;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeHandler{

    GraphicsContext gc; 

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
        System.out.println("click");
        if (x > (0 * gridWidth)+GRID_MARGIN && x < (mazeWidth * gridWidth)+GRID_MARGIN + gridWidth 
            && y > (0 * gridHeight)+GRID_MARGIN && y < (mazeHeight * gridHeight)+GRID_MARGIN + gridHeight){
                System.out.println("Click!");
                int previousSelectedBox = mazeStart;

                double boxXCart = Math.floor((x - (gridWidth*1.5) - GRID_MARGIN)/gridWidth + 0.5);
                double boxYCart = Math.floor((y - (gridHeight*1.5) - GRID_MARGIN)/gridHeight + 0.5);
                System.out.println(boxXCart +" " + boxYCart);
                mazeStart = (int)boxXCart + (int)boxYCart * mazeWidth;
 
                clearBox(previousSelectedBox);
                highlightBox(mazeStart, Color.RED,.80);

        }
        // double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        // double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
    }

    public void setMazeEnd(double x,double y){

        if (x > (0 * gridWidth)+GRID_MARGIN + gridWidth && x < (mazeWidth * gridWidth)+GRID_MARGIN + gridWidth 
        && y > (0 * gridHeight)+GRID_MARGIN + gridHeight && y < (mazeHeight * gridHeight)+GRID_MARGIN + gridHeight){
            int previousSelectedBox = mazeEnd;

            double boxXCart = Math.floor((x - (gridWidth*1.5) - GRID_MARGIN)/gridWidth + 0.5);
            double boxYCart = Math.floor((y - (gridHeight*1.5) - GRID_MARGIN)/gridHeight + 0.5);

            mazeEnd = (int)boxXCart + (int)boxYCart * mazeWidth;
            System.out.println(adjM.getAdjacent(mazeEnd));
            clearBox(previousSelectedBox);
            highlightBox(mazeEnd, Color.PURPLE, .80);

        }
        // double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        // double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
    }
    
    // Clear box of any highlight
    private void clearBox(int selectedBox){
        
        int boxXCart = selectedBox%mazeWidth;
        int boxYCart = selectedBox/mazeWidth;

        double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5; 


        gc.clearRect(boxX - 0.5 * (gridWidth) +1 , boxY - 0.5 * (gridHeight) +1, gridWidth -2 , gridHeight -2);

      
    }
    // Highlights the Selected Box
    private void highlightBox(int selectedBox, Color boxColour, double sizePercentage){
        
        int boxXCart = selectedBox%mazeWidth;
        int boxYCart = selectedBox/mazeWidth;

        double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5; 

        gc.setFill(boxColour);

        gc.fillRect(boxX - 0.5 * (gridWidth*sizePercentage), boxY - 0.5 * (gridHeight*sizePercentage), gridWidth*sizePercentage, gridHeight*sizePercentage);

        gc.setFill(Color.BLACK);        
    }

    // Draws the Maze Grid
    public void drawMaze(){
        
        int canvasHeight = 600;
        int canvasWidth = 600;

        gc.clearRect(0, 0, 600, 600);

        //Define how wide and high each box is 
        gridWidth = (canvasWidth-GRID_MARGIN*2)/(mazeWidth+2);
        gridHeight = (canvasHeight-GRID_MARGIN*2)/(mazeHeight+2);

        System.out.println(gridWidth + " is width" + gridHeight + " is height");
        System.out.println("adjm Height " + adjM.getHeight() + " adjm width:" + adjM.getWidth());

        int numVert = adjM.getNumVert();

        boolean[][] am = adjM.getAdjMatrix(); 
        for (int i = 0; i < numVert; i++) {

            int boxXCart = i%adjM.getWidth();
            int boxYCart = i/adjM.getWidth();
            double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
            double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
            // gc.strokeText(Integer.toString(boxXCart)+ " , " + Integer.toString(boxYCart), boxX, boxY);
            

            // Draw Border Lines

            if(boxXCart == adjM.getWidth()-1){
                gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            }
            if(boxXCart == 0){
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
            }

            if(boxYCart == 0){
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY-.5*gridHeight);
            }

            if(boxYCart == adjM.getHeight()-1){
                gc.strokeLine(boxX-.5*gridWidth, boxY+.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            } 
            
            // Draw maze walls

            // Draw Left hand wall
            if (boxXCart >= 1){    
                if (am[i][i-1] == false){
                    gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
                }
            }
            // // Draw right hand wall
            if (boxXCart < adjM.getWidth()-1){
                if (am[i][i+1] == false){
                    gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
                }
            }
            // Draw bottom wall
            if (boxYCart < adjM.getHeight()-1){
                if (am[i][i+adjM.getWidth()] == false){
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