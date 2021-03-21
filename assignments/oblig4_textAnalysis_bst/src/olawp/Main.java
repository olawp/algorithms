package olawp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        System.out.println("Programmet tar en .txt fil som parameter. Inne i mappen ligger det to filer, 'theraven.txt' og 'pda.txt', som det kan testes med.");
        System.out.println("Skriv inn navnet på filen du vil sortere: ");

        try {
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.next();
            BufferedReader inputfile = new BufferedReader(new FileReader(filename));
            String line = inputfile.readLine();

            while (line != null) {
                String[] separatedLines = line.split(" ");
                for (String words : separatedLines) {
                    tree.insert(words);
                }
                line = inputfile.readLine();

            }

            inputfile.close();

            tree.print();


        } catch (FileNotFoundException e) {
            System.out.println("Filen finnes ikke, prøve en annen fil.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Problemer med å åpne filen");
            System.exit(1);
        }

    }

}
