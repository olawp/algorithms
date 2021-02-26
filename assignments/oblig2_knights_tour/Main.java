import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        // Oppretter en bufferedreader. Bufferedreader skal tydeligvis fungere i
        // IntelliJ, men jeg har ikke testet. Hvis ikke fungerer Scanner fint.
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Henter ut størrelse på sjakkbrett og startposisjon.
        System.out.println("Dette programmet loeser Springerproblemet for ett N x N sjakkbrett \n");
        System.out.println("NB! Posisjon øverst til høyre er (0,0), ikke (1,1). Startposisjon markert med '0' på brettet");
        System.out.println("Velg en N som er 8 eller under(N > 8 vil ta for lang tid): ");
        int N = Integer.parseInt(reader.readLine());
        System.out.println("Velg x posisjon: ");
        int xPos = Integer.parseInt(reader.readLine());
        System.out.println("Velg y posisjon: ");
        int yPos = Integer.parseInt(reader.readLine());

        // Lukker igjen reader.
        reader.close();

        solveKnightsTour(N, xPos, yPos);
    }

    static boolean solveKnightsTour(int N, int xPos, int yPos) {

        // Definerer brett med størrelse NxN.
        int board[][] = new int[N][N];

        //Setter opp brettet og markerer alle celler med -1.
        setupBoard(board, N);

        // Definerer lovlige trekk en springer kan gjøre. 
        int xMoves[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int yMoves[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        //Markerer springerens startposisjon som 0 på brettet. 
        board[xPos][yPos] = 0;
        
        // Ser etter gyldige veier.
        if (!findTour(xPos, yPos, 1, board, xMoves, yMoves, N)) {
            System.out.println("Løsning finnes ikke");
            return false;
        } else {
            printSolution(board, N);
        }
        return true;
    }


    static boolean findTour(int x, int y, int move, int board[][], int xMoves[], int yMoves[], int N) {
        // Hvis move, som blir inkrementert ved hvert kall, er lik N*N, skal vi ha iterert over hver celle. Programmet skal da være ferdig. 
        if (move == N * N) {
            return true;
        }

        /*
        Tester trekkene. Hvis er trekk er gyldig, utfører vi trekket og markerer ruten. Fortsetter så å gjøre dette rekursivt frem til en vei er funnet. Hvis en vei ikke blir funnet returnerer vi false og avslutter. 
        */
        for (int i = 0; i < 8; i++) {
            int nextX = x + xMoves[i];
            int nextY = y + yMoves[i];
            if (legalMove(nextX, nextY, board, N)) {
                board[nextX][nextY] = move;
                if (findTour(nextX, nextY, move + 1, board, xMoves, yMoves, N)) {
                    return true;
                } else {
                    board[nextX][nextY] = -1;
                }
            }
        }

        return false;
    }

    static void printSolution(int board[][], int N) {
        System.out.println();
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                System.out.print(board[x][y] + " ");
            }
            System.out.println();
        }
    }
    
    // Sjekker om et trekk er gyldig, altså om et trekk er større eller lik null og mindre enn N. For at trekket skal være gyldig må også cellen være markert med -1. Altså at den ikke er "brukt".
    static boolean legalMove(int x, int y, int board[][], int N) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);
    }

    // Setter opp brett ved å markere hver celle med -1 siden det ikke er en boolean, altså hverken 0 eller 1.
    static int[][] setupBoard(int[][] board, int N) {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                board[x][y] = -1;
            }
        }
        return board;
    }
}