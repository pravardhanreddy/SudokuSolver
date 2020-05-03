package com.mycompany;

import java.util.ArrayList;
import java.util.Stack;

class Dim {
    public int i;
    public int j;

    public Dim(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

public class Main {

    private static int[][] board =
           {{0,6,4, 5,0,0, 0,8,0},
            {0,0,8, 0,0,0, 0,9,0},
            {0,7,0, 0,0,8, 3,4,0},

            {0,0,2, 0,0,3, 5,0,0},
            {7,0,0, 0,0,0, 0,0,3},
            {0,0,6, 2,0,0, 4,0,0},

            {0,9,5, 1,0,0, 0,7,0},
            {0,2,0, 0,0,0, 8,3,0},
            {0,4,0, 0,0,2, 1,5,0}};

    public static void main(String[] args) {

        printBoard();

        if (isValid()) System.out.println("The board is valid");
        else System.out.println("The board is invalid");

        Stack<Dim> stack = new Stack<>();
        Dim pos;
        int iter = 0;
        int i = 0;
        int j = 0;
        while (true) {
            pos = getNextEmpty(i, j);
            i = pos.i;
            j = pos.j;
            if (i == -1) break;
            while (true) {
                board[i][j] += 1;
                iter++;
                if (board[i][j] == 10){
                    board[i][j] = 0;
                    pos = stack.pop();
                    i = pos.i;
                    j = pos.j;
                } else if (isValid()) {
                    stack.push(new Dim(i,j));
                    break;
                }
            }
        }

        printBoard();
        System.out.println("This is the solved board");
        System.out.println("Solved in " + iter + " iterations");
    }


    public static Dim getNextEmpty(int i, int j) {
        if (i == 8 && j == 8 && board[i][j] != 0) return new Dim(-1, -1);
        int pos = i * 9 + j;
        while (board[pos / 9][pos % 9] != 0) {
            pos++;
            if (pos > 80) return new Dim(-1, -1);
        }
        return new Dim(pos / 9, pos % 9);
    }

    public static boolean isValid() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> storeRow = new ArrayList<>();
            ArrayList<Integer> storeCol = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    if (storeRow.contains(board[i][j])) return false;
                    else storeRow.add(board[i][j]);
                }
                if (board[j][i] != 0) {
                    if (storeCol.contains(board[j][i])) return false;
                    else storeCol.add(board[j][i]);
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ArrayList<Integer> storeBox = new ArrayList<>();
                for (int ii = 0; ii < 3; ii++) {
                    for (int jj = 0; jj < 3; jj++) {
                        if (board[i * 3 + ii][j * 3 + jj] != 0) {
                            if (storeBox.contains(board[i * 3 + ii][j * 3 + jj])) return false;
                            else storeBox.add(board[i * 3 + ii][j * 3 + jj]);
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.print("| ");
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                if (j % 3 == 2) System.out.print("| ");
            }
            System.out.print("\n");
            if (i % 3 == 2) System.out.print("  - - -   - - -   - - -\n");
        }
    }
}

