package Game;

import javax.swing.*;

public class Game {

    private GameBoard board; //ссылка на игровое поле
    private GamePlayer[] gamePlayers = new GamePlayer[2]; // массив игроков
    private int playersTurn = 0; //индекс текущего игрока

    public Game(){
        this.board = new GameBoard(this);
    }
    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    //Метод передачи хода
    void passTurn(){
        if(playersTurn == 0)
            playersTurn = 1;
        else
            playersTurn = 0;
    }

    GamePlayer getCurrentPlayer() {return gamePlayers[playersTurn];}

    void showMessage(String messageText){
        JOptionPane.showMessageDialog(board, messageText);
    }
}
