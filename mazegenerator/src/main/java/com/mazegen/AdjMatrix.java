package com.mazegen;

public class AdjMatrix {
    private boolean[][] adjMatrix;

    private int numVert;

    private int height;

    private int width;

    public AdjMatrix( int height, int width){

        this.numVert = height * width;

        adjMatrix = new boolean[numVert][numVert];

        this.width = width;
        this.height = height;

        this.basicGrid();
    }

    private void basicGrid(){
        // Makes the matrix corrispond to a nxm grid.
        for (int i = 0; i < numVert; i++) {
            for (int j = 0; j < numVert; j++) {
                adjMatrix[i][j] = false;
                if (i - 1 == j){
                    adjMatrix[i][j] = false;
                }
                if (i + 1 == j){
                    adjMatrix[i][j] = false;
                }
                if (i + width == j){
                    adjMatrix[i][j] = false;
                }
                if (i - width == j){
                    adjMatrix[i][j] = false;
                }
            }
        }
    }

    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumVert() {
        return numVert;
    }
}
