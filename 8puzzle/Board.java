/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private int[][] goalTiles;
    private int[][] twinTile;

    public Board(int[][] tiles) {
        goalTiles = new int[tiles.length][tiles.length];

        int count = 1;
        for (int i = 0; i < goalTiles.length; i++) {
            for (int j = 0; j < goalTiles[i].length; j++) {
                if (i == goalTiles.length - 1 && j == goalTiles[i].length - 1) {
                    count = 0;
                }
                goalTiles[i][j] = count++;
            }
        }

        this.tiles = copyTiles(tiles);
    }

    public String toString() {
        StringBuilder tilesToString2 = new StringBuilder();
        tilesToString2.append(tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            tilesToString2.append("\n");
            for (int j = 0; j < tiles[i].length; j++) {
                tilesToString2.append(String.format("%2d ", tiles[i][j]));
            }
        }
        return tilesToString2.toString();
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        int wrongPositionCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if ((goalTiles[i][j] != tiles[i][j]) && tiles[i][j] != 0) {
                    wrongPositionCount++;
                }
            }
        }
        return wrongPositionCount;
    }

    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < goalTiles.length; i++) {
            for (int j = 0; j < goalTiles[i].length; j++) {
                if (i == goalTiles.length - 1 && j == goalTiles[i].length - 1) continue;

                int[] numCoordinates = findCoordinates(goalTiles[i][j]);

                int calculatedDistance = Math.abs(i - numCoordinates[0]) + Math.abs(
                        j - numCoordinates[1]);

                manhattanDistance += calculatedDistance;
            }
        }

        return manhattanDistance;
    }

    private int[] findCoordinates(int num) {
        int[] coordinates = new int[2];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == num) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        return new int[0];
    }

    public boolean isGoal() {
        return isGoalTiles(this);
    }

    private boolean isGoalTiles(Board board) {
        for (int i = 0; i < board.tiles.length; i++) {
            for (int j = 0; j < board.tiles[i].length; j++) {
                if (goalTiles[i][j] != board.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (y == null) return false;

        if (this == y) return true;

        if (y.getClass() != this.getClass()) return false;

        Board yBoard = (Board) y;
        int[][] yTiles = yBoard.tiles;

        if (tiles.length != yTiles.length || tiles[0].length != yTiles[0].length) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != yTiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbourList = new ArrayList<>();
        int[] blankTileCoordinates = findCoordinates(0);

        if (blankTileCoordinates[0] + 1 < tiles.length) {
            Board neighbourBoard = copyBoard(this);
            int[] coordinates = new int[4];
            coordinates[0] = blankTileCoordinates[0];
            coordinates[1] = blankTileCoordinates[1];
            coordinates[2] = blankTileCoordinates[0] + 1;
            coordinates[3] = blankTileCoordinates[1];
            neighbourBoard.exch(coordinates);
            neighbourList.add(neighbourBoard);
        }

        if (blankTileCoordinates[0] - 1 >= 0) {
            Board neighbourBoard = copyBoard(this);
            int[] coordinates = new int[4];
            coordinates[0] = blankTileCoordinates[0];
            coordinates[1] = blankTileCoordinates[1];
            coordinates[2] = blankTileCoordinates[0] - 1;
            coordinates[3] = blankTileCoordinates[1];
            neighbourBoard.exch(coordinates);
            neighbourList.add(neighbourBoard);
        }

        if (blankTileCoordinates[1] + 1 < tiles.length) {
            Board neighbourBoard = copyBoard(this);
            int[] coordinates = new int[4];
            coordinates[0] = blankTileCoordinates[0];
            coordinates[1] = blankTileCoordinates[1];
            coordinates[2] = blankTileCoordinates[0];
            coordinates[3] = blankTileCoordinates[1] + 1;
            neighbourBoard.exch(coordinates);
            neighbourList.add(neighbourBoard);
        }

        if (blankTileCoordinates[1] - 1 >= 0) {
            Board neighbourBoard = copyBoard(this);
            int[] coordinates = new int[4];
            coordinates[0] = blankTileCoordinates[0];
            coordinates[1] = blankTileCoordinates[1];
            coordinates[2] = blankTileCoordinates[0];
            coordinates[3] = blankTileCoordinates[1] - 1;
            neighbourBoard.exch(coordinates);
            neighbourList.add(neighbourBoard);
        }
        return neighbourList;
    }

    private void exch(int[] coordinates) {
        int temp = tiles[coordinates[0]][coordinates[1]];
        tiles[coordinates[0]][coordinates[1]] = tiles[coordinates[2]][coordinates[3]];
        tiles[coordinates[2]][coordinates[3]] = temp;
    }

    private Board copyBoard(Board board) {
        return new Board(board.tiles);
    }

    private int[][] copyTiles(int[][] tiles1) {
        int[][] copyTiles = new int[tiles1.length][tiles1.length];
        for (int i = 0; i < tiles1.length; i++) {
            for (int j = 0; j < tiles1[i].length; j++) {
                copyTiles[i][j] = tiles1[i][j];
            }
        }
        return copyTiles;
    }

    public Board twin() {
        if (twinTile != null) return new Board(twinTile);

        twinTile = copyTiles(tiles);
        int[] coordinates = new int[4];

        while (true) {
            coordinates[0] = (int) (Math.random() * tiles.length);
            coordinates[1] = (int) (Math.random() * tiles.length);
            coordinates[2] = (int) (Math.random() * tiles.length);
            coordinates[3] = (int) (Math.random() * tiles.length);

            if (coordinates[0] == coordinates[2] && coordinates[1] == coordinates[3]) {
                continue;
            }

            if (tiles[coordinates[0]][coordinates[1]] != 0
                    && tiles[coordinates[2]][coordinates[3]] != 0) {
                break;
            }
        }
        exch2(twinTile, coordinates);

        return new Board(twinTile);
    }

    private void exch2(int[][] tempTiles, int[] coordinates) {
        int temp = tempTiles[coordinates[0]][coordinates[1]];
        tempTiles[coordinates[0]][coordinates[1]] = tempTiles[coordinates[2]][coordinates[3]];
        tempTiles[coordinates[2]][coordinates[3]] = temp;
    }
}
