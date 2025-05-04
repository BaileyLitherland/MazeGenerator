package com.mazegen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeGraphics {
    int GRID_MARGIN = 10;
    public MazeGraphics(){

    }

    public void drawMaze(GraphicsContext gc, AdjMatrix adjMatrix){
        
        int canvasHeight = 600;
        int canvasWidth = 600;

        gc.clearRect(0, 0, 600, 600);
        double gridWidth = (canvasHeight-GRID_MARGIN*2)/(adjMatrix.getHeight()+2);
        double gridHeight = (canvasWidth-GRID_MARGIN*2)/(adjMatrix.getWidth()+2);

        int numVert = adjMatrix.getNumVert();

        boolean[][] adjM = adjMatrix.getAdjMatrix(); 
        for (int i =0; i < numVert; i++) {
            //y coord =  i//adjmatrix.width * height of square
            //x coord = i%%adjmatrix.width * width of square
            int boxXCart = i%adjMatrix.getWidth();
            int boxYCart = i/adjMatrix.getWidth();
            double boxX = (boxXCart * gridWidth)+GRID_MARGIN + gridWidth*1.5;
            double boxY = (boxYCart * gridHeight)+GRID_MARGIN + gridHeight*1.5;
            //gc.strokeText(Integer.toString(boxXCart)+ " , " + Integer.toString(boxYCart), boxX, boxY);

            // Draw Border Lines

            if(boxXCart == adjMatrix.getWidth()-1){
                gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            }
            if(boxXCart == 0){
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
            }

            if(boxYCart == 0){
                gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY-.5*gridHeight);
            }

            if(boxYCart == adjMatrix.getHeight()-1){
                gc.strokeLine(boxX-.5*gridWidth, boxY+.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            } 
            
            // Draw maze walls

            // Draw Left hand wall
            if (boxXCart >= 1){    
                if (adjM[i][i-1] == false){
                    gc.strokeLine(boxX-.5*gridWidth, boxY-.5*gridHeight,boxX-.5*gridWidth,boxY+.5*gridHeight);
                }
            }
            // // Draw right hand wall
            // if (boxXCart < adjMatrix.getWidth()-1){
            //     if (adjM[i][i+1] == false){
            //         gc.strokeLine(boxX+.5*gridWidth, boxY-.5*gridHeight,boxX+.5*gridWidth,boxY+.5*gridHeight);
            //     }
            // }
            // Draw bottom wall
            if (boxYCart < adjMatrix.getHeight()-1){
                if (adjM[i][i+adjMatrix.getWidth()] == false){
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
