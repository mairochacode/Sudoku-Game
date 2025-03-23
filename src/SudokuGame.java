import java.util.InputMismatchException;
import java.util.Scanner;

public class SudokuGame {
    private final Sudoku sudoku;
    private final Scanner scanner;

    public SudokuGame() {
        sudoku = new Sudoku();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMenu();
            int option = getUserInput("Escolha uma opção: ");

            switch (option) {
                case 1 -> startNewGame();
                case 2 -> placeNewNumber();
                case 3 -> removeNumber();
                case 4 -> sudoku.displayBoard();
                case 5 -> checkGameStatus();
                case 6 -> clearUserNumbers();
                case 0 -> {
                    System.out.println("Saindo do jogo.");
                    return; // Sai do loop e encerra o jogo
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Iniciar um novo jogo");
        System.out.println("2. Colocar um novo número");
        System.out.println("3. Remover um número");
        System.out.println("4. Verificar jogo");
        System.out.println("5. Verificar status do jogo");
        System.out.println("6. Limpar");
        System.out.println("0. Sair");
    }

    private int getUserInput(String prompt) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer
                break; // Sai do loop se a entrada for válida
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine(); // Limpa o buffer
            }
        }
        return input;
    }

    private void startNewGame() {
        System.out.println("Informe os números iniciais (formato: linha coluna número):");
        int[] initialNumbers = new int[27]; // 9 números iniciais
        for (int i = 0; i < 27; i += 3) {
            if (i / 3 < 9) { // Verifica se o índice está dentro dos limites
                initialNumbers[i] = getUserInput("Linha (0-8): ");
                initialNumbers[i + 1] = getUserInput("Coluna (0-8): ");
                initialNumbers[i + 2] = getUserInput("Número (1-9): ");
            } else {
                break; // Sai do loop se o índice estiver fora dos limites
            }
        }
        sudoku.startGame(initialNumbers);
    }

    private void placeNewNumber() {
        int numToPlace = getUserInput("Informe o número a ser colocado (1-9): ");
        int rowToPlace = getUserInput("Informe a linha (0-8): ");
        int colToPlace = getUserInput("Informe a coluna (0-8): ");
        sudoku.placeNumber(numToPlace, rowToPlace, colToPlace);
    }

    private void removeNumber() {
        int rowToRemove = getUserInput("Informe a linha (0-8) do número a remover: ");
        int colToRemove = getUserInput("Informe a coluna (0-8) do número a remover: ");
        sudoku.removeNumber(rowToRemove, colToRemove);
    }

    private void checkGameStatus() {
        GameStatus status = sudoku.checkGameStatus();
        switch (status) {
            case COMPLETE -> System.out.println("O jogo está completo!");
            case INCOMPLETE -> System.out.println("O jogo está incompleto.");
            case HAS_ERRORS -> System.out.println("O jogo contém erros.");
            default -> System.out.println("O jogo não foi iniciado.");
        }
    }

    private void clearUserNumbers() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!sudoku.fixedPositions[i][j]) {
                    sudoku.getBoard().get(i).set(j, 0); // Limpa a posição
                }
            }
        }
        System.out.println("Números informados pelo usuário foram removidos.");
    }
}