package Game;

import com.sun.deploy.panel.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;//размерность
    static int cellSize = 150;//рамер одной клетки
    private char [][] gameField;//матрица игры
    private GameButton[] gameButtons; // массив кнопок

    private Game game; //ссылка на игру

    static char nullSymbol = '\u0000';//null символ

    public GameBoard(Game currentGame){
        this.game = currentGame;
        initField();
    }

    private void initField(){
        //задаем основные настройки игры
        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(); // панель управления игрой
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();// панельу самой игры
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];
        for (int i = 0; i < (dimension * dimension); i++){
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    //Метод очистки поля и матрицы игры
    void emptyField(){
        for (int i = 0; i < (dimension * dimension); i++){
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbol;
        }
    }

    Game getGame(){
        return game;
    }

    //Метод проверки доступности клетки для хода
    boolean isTurnable(int x, int y){
        boolean result = false;

        if(gameField[y][x] == nullSymbol)
            result = true;
        return result;
    }

    //Обновление матрицы игры после хода
    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin(){
        boolean result = false;

        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();

        if(checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)){
            result = true;
        }
        return  result;
    }

    boolean isFull(){
        boolean result = true;
            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    if(gameField[i][j] == nullSymbol)
                        result = false;
                }
            }
        return result;
    }

    private boolean checkWinLines(char playerSymbol){
        boolean cols, rows, result;

        result = false;
        for (int col = 0; col < dimension; col++){
            cols = true;
            rows = true;

            for(int row = 0; row < dimension; row++){
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
                if (cols || rows){
                    result = true;
                    break;
                }
                if(result){
                    break;
                }
            }
        }return result;


    }
    private boolean checkWinDiagonals(char playersSymbol){
        boolean leftRight, rightLeft, result;

        leftRight = true;
        rightLeft = true;
        result = false;

        for(int i = 0; i < dimension; i++){
            leftRight &= (gameField[i][i] == playersSymbol); //оптимизация кода, чтобы не было через if/else
            rightLeft &= (gameField[dimension - i - 1][i] == playersSymbol);
        }

        if(leftRight || rightLeft){
            result = true;
        }

        return result;
    }

    public GameButton getButton(int buttonIndex){
        return gameButtons[buttonIndex];
    }


}
