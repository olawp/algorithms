package olawp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Hashing av tekststrenger med lineÃ¦r probing
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell
// - Tilbyr bare innsetting og sÃ¸king
//
public class hashLinear {
    // Hashlengde
    private final int hashLengde;

    // Hashtabell
    private final String[] hashTabell;

    // Antall elementer lagret i tabellen
    private int n;


    // Antall probes ved innsetting
    private int antProbes;

    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashLinear(int lengde) {
        hashLengde = lengde;
        hashTabell = new String[lengde];
        n = 0;
        antProbes = 0;
    }

    // Returnerer load factor
    public float loadFactor() {
        return ((float) n) / hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData() {
        return n;
    }

    // Returnerer antall probes ved innsetting
    public int antProbes() {
        return antProbes;
    }

    // Hashfunksjon
    int hash(String S) {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med lineÃ¦r probing
    // Avbryter med feilmelding hvis ledig plass ikke finnes
    //
    void insert(String S) {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTabell[neste] != null) {
            // Ny probe
            antProbes++;

            // Denne indeksen er opptatt, prÃ¸ver neste
            neste++;

            // Wrap-around
            if (neste >= hashLengde)
                neste = 0;

            // Hvis vi er kommet tilbake til opprinnelig hashverdi, er
            // tabellen full og vi gir opp (her ville man normalt
            // doblet lengden pÃ¥ hashtabellen og gjort en rehashing)
            if (neste == h) {
                System.err.println("\nHashtabell full, avbryter");
                System.exit(0);
            }
        }

        // Lagrer tekststrengen pÃ¥ funnet indeks
        hashTabell[neste] = S;

        // Ã˜ker antall elementer som er lagret
        n++;
    }


    // ------- IMPLEMENTERT AV MEG -------
    // Usikker på om probing er gjort riktig her og i robinhood da jeg får samme antall probes på begge ??
    // Jeg tenker sånn at det burde stemme med tanke på at probingen kun skjer ved innsetting
    void insertLCFS(String S) {
        int h = hash(S);
        int neste = h;

        if (hashTabell[neste] != null) {
            antProbes++;
        }

        String currentItem = S;

        while (true) {
            if (hashTabell[neste] == null) {
                hashTabell[neste] = currentItem;
                n++;
                return;

            } else {
                String temporary = hashTabell[neste];
                hashTabell[neste] = currentItem;
                currentItem = temporary;

                neste++;

                //Wrap-around
                if (neste >= hashLengde) {
                    neste = 0;
                }

                if (neste == h) {
                    System.err.println("\nHashtabell full, avbryter");
                    System.exit(0);
                }
            }

        }


    }

    // ------- IMPLEMENTERT AV MEG -------
    // Usikker på om probing er gjort riktig her og i LCFS da jeg får samme antall probes på begge ??
    // Jeg tenker sånn at det burde stemme med tanke på at probingen kun skjer ved innsetting
    // Er generelt sett usikker om på denne implementasjonen stemmer i det hele tatt, men det var det beste jeg kom frem til.
    // Som sagt er nok probeantallet feil, så må nesten se på selve implementasjonen for å se om det er riktig eller ei .
    public void insertRobinHood(String S) {
        int h = hash(S);
        int neste = h;

        if (hashTabell[neste] != null) {
            // Tror probeverdien her blir feil
            antProbes++;
        }

        String current = S;

        while (true) {
            // Hvis det er ledig, setter vi elementet i indeksen
            if (hashTabell[neste] == null) {
                hashTabell[neste] = current;
                n++;
                return;
            } else {
                // Sammenligner hashene for å bestemme hvor mye de har flyttet på seg
                String temp = hashTabell[neste];
                if (hashTabell[neste] != null && ((hash(current) - neste) > (hash(temp) - neste))) {
                    hashTabell[neste] = current;
                    current = temp;
                }

            }

            neste++;

            //Wrap-around
            if (neste >= hashLengde) {
                neste = 0;
            }

            if (neste == h) {
                System.err.println("\nHashtabell full, avbryter");
                System.exit(0);
            }

        }


    }


    // SÃ¸king etter tekststreng med lineÃ¦r probing
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S) {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTabell[neste] != null) {
            // Har vi funnet tekststrengen?
            if (hashTabell[neste].compareTo(S) == 0)
                return true;

            // PrÃ¸ver neste mulige
            neste++;

            // Wrap-around
            if (neste >= hashLengde)
                neste = 0;

            // Hvis vi er kommet tilbake til opprinnelig hashverdi,
            // finnes ikke strengen i tabellen
            if (neste == h)
                return false;
        }

        // Finner ikke strengen, har kommet til en probe som er null
        return false;
    }

    // Enkelt testprogram:
    //
    // * Hashlengde gis som input pÃ¥ kommandolinjen
    //
    // * Leser tekststrenger linje for linje fra standard input
    //   og lagrer dem i hashtabellen
    //
    // * Skriver ut litt statistikk etter innsetting
    //
    // * Tester om sÃ¸k fungerer for et par konstante verdier
    //

    // ------- IMPLEMENTERT AV MEG -------
    // Gjort om Jan sin kode så den kan lese inn fra fil istedenfor kommandolinje siden det var teit i intellij
    // Fjernet søk nederst i main også siden det strangt talt ikke trengs
    public static void main(String[] argv) {
        // Hashlengde leses fra kommandolinjen
        System.out.println("Lengde på hashtabell?");
        Scanner length = new Scanner(System.in);
        int hashLengde = length.nextInt();

        System.out.println("\nVelg metode: ");
        System.out.println("(1). Last come, first served");
        System.out.println("(2). Robin Hood\n");
        int option = length.nextInt();
        System.out.println("-----------");
        // Lager ny hashTabell
        hashLinear hL = new hashLinear(hashLengde);


        File file = new File("cars.txt");

        try {
            Scanner scanner = new Scanner(file);

            if (option == 1) {
                System.out.println("Last Come, First Served \n");
                while (scanner.hasNext()) {
                    hL.insertLCFS(scanner.nextLine());
                }
            } else if (option == 2) {
                System.out.println("Robin Hood");
                while (scanner.hasNext()) {
                    hL.insertRobinHood(scanner.nextLine());
                }
            } else {
                System.out.println("Noe er scuffed");
                return;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hL.antData());
        System.out.printf("Load factor : %5.3f\n", hL.loadFactor());
        System.out.println("Probes      : " + hL.antProbes());

    }
}
