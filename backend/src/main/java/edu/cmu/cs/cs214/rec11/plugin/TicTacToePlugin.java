package edu.cmu.cs.cs214.rec11.plugin;

import edu.cmu.cs.cs214.rec11.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec11.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec11.games.TicTacToe;
import edu.cmu.cs.cs214.rec11.games.TicTacToe.Player;

public class TicTacToePlugin implements GamePlugin<Player> {
    private static final int WIDTH = TicTacToe.SIZE;
    private static final int HEIGHT = TicTacToe.SIZE;
    private static final String GAME_NAME = "Tic Tac Toe";
    private GameFramework framework;
    private TicTacToe game;
    private static final String X_WON_MSG = "Player X won!";
    private static final String O_WON_MSG = "Player O won!";
    private static final String TIED_MSG = "It's a tie!";
    private static final String X_TURN_MSG = "X's turn";
    private static final String O_TURN_MSG = "O's turn";

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public int getGridWidth() {
        return WIDTH;
    }

    @Override
    public int getGridHeight() {
        return HEIGHT;
    }

    @Override
    public void onRegister(GameFramework f) {
        framework = f;
    }

    @Override
    public void onNewGame() {
        game = new TicTacToe();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                framework.setSquare(i, j, null);
            }
        }
        framework.setFooterText(X_TURN_MSG);
    }

    @Override
    public void onNewMove() { } 

    @Override
    public boolean isMoveValid(int x, int y) {
        return game.isValidPlay(x, y);
    }

    @Override
    public boolean isMoveOver() {
        return game.isOver();
    }

    @Override
    public void onMovePlayed(int x, int y) {
        game.play(x, y);
        Player player = game.getSquare(x, y);
        String symbol = player == Player.X ? "X" : "O";
        framework.setSquare(x, y, symbol);
        if (game.isOver()) {
            String winner = game.winner() == Player.X ? X_WON_MSG : O_WON_MSG;
            if (game.winner() == null) winner = TIED_MSG;
            framework.setFooterText(winner);
        } else {
            String nextPlayer = game.currentPlayer() == Player.X ? X_TURN_MSG : O_TURN_MSG;
            framework.setFooterText(nextPlayer);
        }
    }

    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    @Override
    public String getGameOverMessage() {
        if (game.winner() == null) {
            return TIED_MSG;
        } else {
            return game.winner() == Player.X ? X_WON_MSG : O_WON_MSG;
        }
    }

    @Override
    public void onGameClosed() {
        game = null;
    }

    @Override
    public Player currentPlayer() {
        return game.currentPlayer();
    }
}
