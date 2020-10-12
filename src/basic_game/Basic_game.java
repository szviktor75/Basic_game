package basic_game;

import java.util.Random;

public class Basic_game {

    static final int GAMING_ROUND = 100;
    static final int WIDTH = 30;
    static final int HEIGHT = 30;
    static final Random RANDOM = new Random();

    public static void main(String[] args) throws InterruptedException {

//játékos adatai 
        String player_mark = "O";
        int player_row = 2;
        int player_column = 2;
        Direction player_direction = Direction.RIGHT;

//ellenfél adatai  
        String enemy_mark = "X";
        int enemy_row = 7;
        int enemy_column = 4;
        Direction enemy_direction = Direction.LEFT;

// pálya inicializálása
        String[][] level = new String[HEIGHT][WIDTH];
        init_level(level);
        add_walls(level, 1, 0);

        for (int iteration_number = 1; iteration_number <= GAMING_ROUND; iteration_number++) {
            // játékos léptetése
            if (iteration_number % 15 == 0) {
                player_direction = switch_direction(player_direction);
            }
            int[] player_coordinates = make_move(player_direction, level, player_row, player_column);
            player_row = player_coordinates[0];
            player_column = player_coordinates[1];

            // ellenfél léptetése
            if (iteration_number % 10 == 0) {
                enemy_direction = switch_direction(enemy_direction);
            }
            int[] enemy_coordinates = make_move(enemy_direction, level, enemy_row, enemy_column);
            enemy_row = enemy_coordinates[0];
            enemy_column = enemy_coordinates[1];

            draw(level, player_mark, player_row, player_column, enemy_mark, enemy_row, enemy_column);
            add_some_delay(200, iteration_number);
            if (player_row == enemy_row && player_column == enemy_column) {
                System.out.println("Vesztettél!");
                break;
            }
        }
        System.out.println("Játék vége!");

    }
    static void add_walls(String[][] level, int number_of_horizontal_wall, int number_of_vertical_wall ){
       for(int i = 0; i < number_of_horizontal_wall; i++){
           add_horizontal_wall(level);
       } 
       
       for(int i = 0; i < number_of_vertical_wall; i++){
           add_vertical_wall(level);
       } 
    }
    
    static void add_horizontal_wall(String[][] level){
        int wall_width = RANDOM.nextInt(WIDTH - 3);
        int wall_row = RANDOM.nextInt(HEIGHT - 2) + 1;
        int wall_column = RANDOM.nextInt(WIDTH - 2 - wall_width);
        for(int i =0; i < wall_width; i++){
            level[wall_row][wall_column + i] = "N"; 
    }
    }

    static void add_vertical_wall(String[][] level){
        int wall_height = RANDOM.nextInt(HEIGHT - 3);
        int wall_column = RANDOM.nextInt(HEIGHT - 2) + 1;
        int wall_row = RANDOM.nextInt(HEIGHT - 2 - wall_height);
        for(int i =0; i < wall_height; i++){
            level[wall_row + i][wall_column] = "N"; 
    }
    }
    
    static void add_some_delay(long time, int iteration_number) throws InterruptedException {
        System.out.println("--------" + iteration_number + "---------");

        Thread.sleep(time);
    }

    static void init_level(String[][] level) {
        for (int row = 0; row < level.length; row++) {
            for (int column = 0; column < level[row].length; column++) {
                if (row == 0 || row == HEIGHT - 1 || column == 0 || column == WIDTH - 1) {
                    level[row][column] = "N";
                } else {
                    level[row][column] = " ";
                }
            }
        }
    }

    static int[] make_move(Direction direction, String[][] board, int row, int column) {

        switch (direction) {
            case RIGHT:
                if (board[row][column + 1].equals(" ")) {
                    column++;
                }
                break;
            case DOWN:
                if (board[row + 1][column].equals(" ")) {
                    row++;
                }
                break;
            case LEFT:
                if (board[row][column - 1].equals(" ")) {
                    column--;
                }
                break;
            case UP:
                if (board[row - 1][column].equals(" ")) {
                    row--;
                }
                break;
        }
        return new int[]{row, column};
    }

    static Direction switch_direction(Direction direction) {
        switch (direction) {
            case RIGHT:
                return direction = Direction.DOWN;
            case DOWN:
                return direction = Direction.LEFT;
            case LEFT:
                return direction = Direction.UP;
            case UP:
                return direction = Direction.RIGHT;
        }
        return direction;
    }

    static void draw(String[][] board, String player_mark, int player_row, int player_column, String enemy_mark, int enemy_row, int enemy_column) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (row == player_row && column == player_column) {
                    System.out.print(player_mark);
                } else if (row == enemy_row && column == enemy_column) {
                    System.out.print(enemy_mark);
                } else {
                    System.out.print(board[row][column]);
                }

            }
            System.out.println();

        }
    }
}
