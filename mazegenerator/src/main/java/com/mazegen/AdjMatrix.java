package com.mazegen;

import java.util.ArrayList;

public class AdjMatrix {
    private boolean[][] adjMatrix;

    private int numVert;

    private int height;

    private int width;

    public AdjMatrix( int width, int height){

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

    public boolean isAdjacent(int i,int j){
        return adjMatrix[i][j];
    }

    public void makeAdjcent(int i,int j){
        adjMatrix[i][j] = true;
        adjMatrix[j][i] = true;
    }

    public ArrayList<Integer> getAdjacent(int i){

        ArrayList<Integer> adjVertex = new ArrayList<Integer>();
        for (int j = 0; j < numVert; j++) {
            if(isAdjacent(i, j)){
                if(i!=j){
                    adjVertex.add(j);
                }
            }
        } 

        return adjVertex;
        
    }
}
