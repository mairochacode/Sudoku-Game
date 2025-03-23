import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku {
    private final List<List<Integer>> board; // Tabuleiro do Sudoku
    protected boolean[][] fixedPositions; // Para saber quais posições são fixas

    public Sudoku() {
        board = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<>(9));
            for (int j = 0; j < 9; j++) {
                board.get(i).add(0); // Inicializa com zeros
            }
        }
        fixedPositions = new boolean[9][9]; // Inicializa as posições fixas
    }

    public void startGame(int[] initialNumbers) {
        for (int i = 0; i < initialNumbers.length; i += 3) {
            int row = initialNumbers[i];
            int col = initialNumbers[i + 1];
            int num = initialNumbers[i + 2];
            board.get(row).set(col, num); // Coloca o número no tabuleiro
            fixedPositions[row][col] = true; // Marca a posição como fixa
        }
        System.out.println("Jogo iniciado!");
    }

    public void placeNumber(int num, int row, int col) {
        if (fixedPositions[row][col]) {
            System.out.println("Essa posição já está preenchida!");
        } else {
            board.get(row).set(col, num); // Coloca o número na posição
            System.out.println("Número colocado com sucesso!");
        }
    }

    public void removeNumber(int row, int col) {
        if (fixedPositions[row][col]) {
            System.out.println("Não é possível remover um número fixo!");
        } else {
            board.get(row).set(col, 0); // Remove o número
            System.out.println("Número removido com sucesso!");
        }
    }

    public void displayBoard() {
        System.out.println("Tabuleiro Atual:");
        for (List<Integer> row : board) {
            for (Integer num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public GameStatus checkGameStatus() {
        boolean complete = true;
        boolean hasErrors = false;

        for (int i = 0; i < 9; i++) {
            Set<Integer> rowSet = new HashSet<>();
            Set<Integer> colSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                // Verifica se a linha contém erros
                if (board.get(i).get(j) != 0 && !rowSet.add(board.get(i).get(j))) {
                    hasErrors = true; // Erro na linha
                }
                // Verifica se a coluna contém erros
                if (board.get(j).get(i) != 0 && !colSet.add(board.get(j).get(i))) {
                    hasErrors = true; // Erro na coluna
                }
                if (board.get(i).get(j) == 0) {
                    complete = false; // Se houver um zero, o jogo não está completo
                }
            }
        }

        if (complete) {
            return GameStatus.COMPLETE;
        } else if (hasErrors) {
            return GameStatus.HAS_ERRORS;
        } else {
            return GameStatus.INCOMPLETE;
        }
    }

    // Getter para o tabuleiro
    public List<List<Integer>> getBoard() {
        return board;
    }
}