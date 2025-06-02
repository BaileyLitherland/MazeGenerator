package com.mazegen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeHandler{

    GraphicsContext gc; 

    int GRID_MARGIN = 10;

    int selectedBox;
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

    // Highlights the Selected Box
    private void highlightBox(int selectedBox, Color boxColour){
        
        int boxXCart = selectedBox%mazeWidth;
        int boxYCart = selectedBox/mazeWidth;

        double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
        double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5; 

        gc.fillRect(boxXCart, boxYCart, gridWidth, gridHeight);
    }

    // Draws the Maze Grid
    public void drawMaze(){
        
        int canvasHeight = 600;
        int canvasWidth = 600;

        gc.clearRect(0, 0, 600, 600);
        double gridWidth = (canvasWidth-GRID_MARGIN*2)/(adjM.getWidth()+2);
        double gridHeight = (canvasHeight-GRID_MARGIN*2)/(adjM.getHeight()+2);

        System.out.println(gridWidth + " is width" + gridHeight + " is height");
        System.out.println("adjm Height " + adjM.getHeight() + " adjm width:" + adjM.getWidth());

        int numVert = adjM.getNumVert();

        boolean[][] am = adjM.getAdjMatrix(); 
        for (int i = 0; i < numVert; i++) {
            //y coord =  i//adjmatrix.width * height of square
            //x coord = i%%adjmatrix.width * width of square
            int boxXCart = i%adjM.getWidth();
            int boxYCart = i/adjM.getWidth();
            double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
            double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
            //gc.strokeText(Integer.toString(boxXCart)+ " , " + Integer.toString(boxYCart), boxX, boxY);
            

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
            
            // if (i > adjMatrix.getHeight()){
            //     if (adjM[i][i-adjMatrix.getHeight()-1] == false){
            //         gc.strokeLine(boxX-.5*gridWidth, boxY+.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            //     }
            // }
            // }
            // if (adjM[i][i-1] == false){
            //     gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            // }7
            //adjMatrix.getAdjMatrix()
            // gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY-.5*gridHeight);
            // gc.strokeLine(boxX-.5*gridWidth, boxY+.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            // gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
            // gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
        
        }

        gc.strokeLine(canvasHeight, canvasWidth, canvasHeight, canvasWidth);

    }
}