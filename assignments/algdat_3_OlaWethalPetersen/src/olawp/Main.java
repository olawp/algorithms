package olawp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hvor mange tall skal sorteres: ");
        int n = scanner.nextInt();
        System.out.println("\nVelg sortering: \n");
        System.out.println("1) Insertionsort");
        System.out.println("2) Quicksort");
        System.out.println("3) Mergesort");
        System.out.println("4) Radixsort\n");

        int sortingMethod = scanner.nextInt();

        System.out.println("\nHva vil du gjøre? \n");
        System.out.println("1) Utføre sortering og vise tidsbruk");
        System.out.println("2) Estimere verdi for konstanten foran høyeste ordens ledd");
        int test = scanner.nextInt();

        int[] A = new int[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            A[i] = r.nextInt(n);
        }

        switch (test) {
            case 1 -> sort(sortingMethod, n, A);
            case 2 -> estimate(sortingMethod, n, A);
            default -> System.out.println("\nFeil i input");
        }
    }

    //Dette kan og burde refaktoreres, men eh
    public static void sort(int sortMethod, int n, int[] array) {
        long currentTime, elapsedTime;
        switch (sortMethod) {
            case 1 -> {
                currentTime = System.currentTimeMillis();
                insertionSort(array);
                elapsedTime = System.currentTimeMillis() - currentTime;
                System.out.println("\nSorteringsmetode: Insertion sort");
                System.out.println("Sorteringstid: " + elapsedTime / 1000.0 + " sekunder");
                System.out.println("Verdier sortert: " + n);
            }
            case 2 -> {
                currentTime = System.currentTimeMillis();
                quickSort(array, 0, n - 1);
                elapsedTime = System.currentTimeMillis() - currentTime;
                System.out.println("\nSorteringsmetode: Quicksort");
                System.out.println("Sorteringstid: " + elapsedTime / 1000.0 + " sekunder");
                System.out.println("Verdier sortert: " + n);
            }
            case 3 -> {
                currentTime = System.currentTimeMillis();
                mergeSort(array, 0, n - 1);
                elapsedTime = System.currentTimeMillis() - currentTime;
                System.out.println("\nSorteringsmetode: Merge sort");
                System.out.println("Sorteringstid: " + elapsedTime / 1000.0 + " sekunder");
                System.out.println("Verdier sortert: " + n);
            }
            case 4 -> {
                currentTime = System.currentTimeMillis();
                radixSort(array);
                elapsedTime = System.currentTimeMillis() - currentTime;
                System.out.println("\nSorteringsmetode: Radix sort");
                System.out.println("Sorteringstid: " + elapsedTime / 1000.0 + " sekunder");
                System.out.println("Verdier sortert: " + n);


            }
        }
    }

    //TODO: Refaktorer denne. Funker fint at this point tho.
    public static void estimate(int sortMethod, int n, int[] array) {
        Random r = new Random();
        double C;
        double total = 0;
        long time;
        long totalTime = 0;
        int iterations = 5;

        switch (sortMethod) {
            case 1 -> {
                System.out.println("\nKjører 5 testsorteringer. \n");
                for (int i = 0; i < iterations; i++) {
                    for (int j = 0; j < n; j++) {
                        array[j] = r.nextInt(n);
                    }

                    time = System.currentTimeMillis();
                    insertionSort(array);
                    time = System.currentTimeMillis() - time;
                    total += time / Math.pow(n, 2);
                    totalTime += time;

                    System.out.println("Sortering " + (i + 1) + "/" + iterations + " fullført. Sortering tok " + time / 1000.0 + " sekunder");
                }
                C = (total / 5);
                System.out.println("\nTilnærmet verdi for C: " + C);
                System.out.println("Tid brukt: " + totalTime / 1000.0 + " sekunder");
            }
            case 2 -> {
                System.out.println("\nKjører 5 testsorteringer. \n");
                for (int i = 0; i < iterations; i++) {
                    for (int j = 0; j < n; j++) {
                        array[j] = r.nextInt(n);
                    }
                    time = System.currentTimeMillis();
                    quickSort(array, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    total += time / (n * Math.log(n));
                    totalTime += time;

                    System.out.println("Sortering " + (i + 1) + "/" + iterations + " fullført. Sortering tok " + time / 1000.0 + " sekunder");
                }

                C = (total / 5);
                System.out.println("\nTilnærmet verdi for C: " + C);
                System.out.println("Tid brukt: " + totalTime / 1000.0 + " sekunder");
            }
            case 3 -> {
                System.out.println("\nKjører 5 testsorteringer. \n");
                for (int i = 0; i < iterations; i++) {
                    for (int j = 0; j < n; j++) {
                        array[j] = r.nextInt(n);
                    }
                    time = System.currentTimeMillis();
                    mergeSort(array, 0, n - 1);
                    time = System.currentTimeMillis() - time;
                    total += time / (n * Math.log(n));
                    totalTime += time;

                    System.out.println("Sortering " + (i + 1) + "/" + iterations + " fullført. Sortering tok " + time / 1000.0 + " sekunder");

                }
                C = (total / 5);
                System.out.println("\nTilnærmet verdi for C: " + C);
                System.out.println("Tid brukt: " + totalTime / 1000.0 + " sekunder");
            }
            case 4 -> {
                System.out.println("\nKjører 5 testsorteringer. \n");
                for (int i = 0; i < iterations; i++) {
                    for (int j = 0; j < n; j++) {
                        array[j] = r.nextInt(n);
                    }
                    time = System.currentTimeMillis();
                    radixSort(array);
                    time = System.currentTimeMillis() - time;
                    // Etter hva jeg leste skal Radix være O(m*n), der m er antall iterasjoner. Vanligvis er n mye større enn m, som vil føre til at arbeidsmengde er O(n)
                    total += time / (float) n;
                    totalTime += time;

                    System.out.println("Sortering " + (i + 1) + "/" + iterations + " fullført. Sortering tok " + time / 1000.0 + " sekunder");

                }
                C = (total / 5);
                System.out.println("\nTilnærmet verdi for C: " + C);
                System.out.println("Tid brukt: " + totalTime / 1000.0 + " sekunder");
            }
        }
    }


    public static void insertionSort(int[] A) {
        int n = A.length;
        int key;

        for (int i = 1; i < n; i++) {
            key = A[i];
            int j = i;
            while (j > 0 && A[j - 1] > key) {
                A[j] = A[j - 1];
                j--;
            }
            A[j] = key;
        }

    }

    public static void quickSort(int[] A, int min, int max) {
        int indexofpartition;

        if (max - min > 0) {
            indexofpartition = findPartition(A, min, max);

            quickSort(A, min, indexofpartition - 1);

            quickSort(A, indexofpartition + 1, max);
        }
    }

    private static int findPartition(int[] A, int min, int max) {
        int left, right;
        int temp, partitionelement;

        partitionelement = A[min];

        left = min;
        right = max;

        while (left < right) {
            while (A[left] <= partitionelement && left < right)
                left++;

            while (A[right] > partitionelement)
                right--;

            if (left < right) {
                temp = A[left];
                A[left] = A[right];
                A[right] = temp;
            }
        }

        temp = A[min];
        A[min] = A[right];
        A[right] = temp;

        return right;
    }


    public static void mergeSort(int[] A, int min, int max) {


        if (min == max)
            return;

        int[] temp;
        int index1, left, right;
        int size = max - min + 1;
        int mid = (min + max) / 2;

        temp = new int[size];

        mergeSort(A, min, mid);
        mergeSort(A, mid + 1, max);

        for (index1 = 0; index1 < size; index1++)
            temp[index1] = A[min + index1];

        left = 0;
        right = mid - min + 1;
        for (index1 = 0; index1 < size; index1++) {
            if (right <= max - min)
                if (left <= mid - min)
                    if (temp[left] > temp[right])
                        A[index1 + min] = temp[right++];
                    else
                        A[index1 + min] = temp[left++];
                else
                    A[index1 + min] = temp[right++];
            else
                A[index1 + min] = temp[left++];
        }
    }

    // Denne metoden kan trigge en "OutOfMemoryError" ved sortering av store mengder tall. F.eks 10 millioner verdier går fint, 100 millioner har ikke jeg fått til å fungere.
    public static void radixSort(int[] a) {


        int ti_i_m = 1;
        int n = a.length;
        int maksAntSiffer = String.valueOf(n).length();

        Queue<Integer>[] Q = (Queue<Integer>[]) (new Queue[10]);

        for (int i = 0; i < 10; i++)
            Q[i] = new LinkedList();


        for (int m = 0; m < maksAntSiffer; m++) {
            // Fordeler tallene i 10 kÃ¸er
            for (int k : a) {
                int siffer = (k / ti_i_m) % 10;
                Q[siffer].add(k);
            }

            int j = 0;
            for (int i = 0; i < 10; i++)
                while (!Q[i].isEmpty())
                    a[j++] = Q[i].remove();


            ti_i_m *= 10;
        }
    }


}
