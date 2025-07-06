package com.mazegen;

import java.util.ArrayList;
import java.util.Stack;

public abstract class MazeSolver{

    public ArrayList<Integer> solveMaze(AdjMatrix maze, int start, int finish){

        System.out.println(finish + " " + start);

        boolean[] visited = new boolean[maze.getNumVert()];
        Integer[] parents = new Integer[maze.getNumVert()];
        ArrayList<Integer> rtnArray = new ArrayList<>();
        Stack<Integer> s = new Stack<>();

        s.push(start);
        System.out.println(start);
        parents[start] = -1;
        int vertex;
        while (s.empty() == false){
            
            vertex = s.pop();
            System.out.println(visited[vertex]);
            if (visited[vertex] == false){
                visited[vertex] = true;
                ArrayList<Integer> neighbours = maze.getAdjacent(vertex); 
                for (Integer neighbour : neighbours) {
                    if (visited[neighbour] == false){
                        s.push(neighbour);
                        parents[neighbour] = vertex;
                        if (vertex == finish){
                            int current = finish;
                            
                            while(current != -1){
                                rtnArray.add(current);
                                current = parents[current];
                            }
                            System.out.println(rtnArray);
                            return rtnArray;
                        }
                    }                     
                }
            }
        }
        return null;
    }

    


}
