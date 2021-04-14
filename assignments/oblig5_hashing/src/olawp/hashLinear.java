package olawp;

import java.io.IOException;
import java.util.Arrays;
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

    public String[] getHashTabell() {
        return hashTabell;
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

    public void insertLCFS(String S) {
        int h = hash(S);
        int neste = h;

        // debug og testing
        System.out.println(neste);

        if (hashTabell[neste] != null)
            antProbes++;

        String currentItem = S;

        while (true) {
            if (hashTabell[neste] == null) {
                hashTabell[neste] = currentItem;
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

                n++;
            }
        }

    }

    public void insertRobinHood(String S) {
        int h = hash(S);
        int neste = h;

        System.out.println(neste);

        if (hashTabell[neste] != null) {
            antProbes++;
        }

        String currentItem = S;

        while (true) {
            if (hashTabell[neste] == null) {
                hashTabell[neste] = currentItem;
                return;

            } else {
                String temporary = hashTabell[neste];

                // Fant noe random greier, dunno om dette stemmer atm
                if ((hash(currentItem) - neste) > hash(temporary) - neste) {
                    hashTabell[neste] = currentItem;
                    currentItem = temporary;
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

                n++;
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
    public static void main(String[] argv) {
        System.out.println("Skriv inn tekststrenger som skal hashes. Avbryt input med ctrl+d");
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 0;
        Scanner input = new Scanner(System.in);
        try {
            if (argv.length != 1)
                throw new IOException("Feil: Hashlengde mÃ¥ angis");
            hashLengde = Integer.parseInt(argv[0]);
            if (hashLengde < 1)
                throw new IOException("Feil: Hashlengde mÃ¥ vÃ¦re stÃ¸rre enn 0");
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }

        // Lager ny hashTabell
        hashLinear hL = new hashLinear(hashLengde);

        // Leser input og hasher alle linjer
        while (input.hasNext()) {
            hL.insertRobinHood(input.nextLine());
            // debug og testing
            System.out.println(Arrays.toString(hL.getHashTabell()));
        }

        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hL.antData());
        System.out.printf("Load factor : %5.3f\n", hL.loadFactor());
        System.out.println("Probes      : " + hL.antProbes());

        // Et par enkle sÃ¸k
        String S = "Volkswagen Karmann Ghia";
        if (hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hL.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}
