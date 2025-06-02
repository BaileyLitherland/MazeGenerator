package com.mazegen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeHandler{

    GraphicsContext gc; 

    int GRID_MARGIN = 10;

    int selectedBox;

    int mazeStart=-1;
    int mazeEnd=-1;

    int mazeWidth;
    int mazeHeight;
    
    double gridWidth;
    double gridHeight;

    MazeGenerator mazeGenerator;

    AdjMatrix adjM;

    public MazeHandler(GraphicsContext gc){
        this.gc = gc;
        mazeGenerator = new MazeGenerator();
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
    private void setGridWidth(double gridWidth){
        this.gridWidth = gridWidth;
    }

    private void setGridHeight(double gridHeight){
        this.gridHeight = gridHeight;
    }

    public void setMazeStart(double x,double y){

        if (x > (0 * gridWidth)+GRID_MARGIN + gridWidth && x < (mazeWidth * gridWidth)+GRID_MARGIN + gridWidth 
            && y > (0 * gridHeight)+GRID_MARGIN + gridHeight && y < (mazeHeight * gridHeight)+GRID_MARGIN + gridHeight){
                int previousSelectedBox = mazeStart;

                double boxXCart = Math.floor((x - (gridWidth*1.5) - GRID_MARGIN)/gridWidth + 0.5);
                double boxYCart = Math.floor((y - (gridHeight*1.5) - GRID_MARGIN)/gridHeight + 0.5);

                mazeStart = (int)boxXCart + (int)boxYCart * mazeWidth;
 
                clearBox(previousSelectedBox);
                highlightBox(mazeStart, Color.RED);

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
                
            clearBox(previousSelectedBox);
            highlightBox(mazeEnd, Color.PURPLE);

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


        gc.clearRect(boxX - 0.5 * (gridWidth*.95), boxY - 0.5 * (gridHeight*.95), gridWidth*.95, gridHeight*.95);

      
    }
    // Highlights the Selected Box
    private void highlightBox(int selectedBox, Color boxColour){
        
        int boxXCart = selectedBox%mazeWidth;
        int boxYCart = selectedBox/mazeWidth;

        double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5; 

        gc.setFill(boxColour);

        gc.fillRect(boxX - 0.5 * (gridWidth*.95), boxY - 0.5 * (gridHeight*.95), gridWidth*.95, gridHeight*.95);

        gc.setFill(Color.BLACK);        
    }

    // Draws the Maze Grid
    public void drawMaze(){
        
        int canvasHeight = 600;
        int canvasWidth = 600;

        gc.clearRect(0, 0, 600, 600);
        gridWidth = (canvasWidth-GRID_MARGIN*2)/(adjM.getWidth()+2);
        gridHeight = (canvasHeight-GRID_MARGIN*2)/(adjM.getHeight()+2);

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
}