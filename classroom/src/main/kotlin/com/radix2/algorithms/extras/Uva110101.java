package com.radix2.algorithms.extras;

import java.io.IOException;
import java.util.StringTokenizer;

public class Uva110101 {

    static String readLn(int maxLg) {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;
        String line = "";

        try {
            while (lg < maxLg) {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin[lg++] += car;
            }
        } catch (IOException e) {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String(lin, 0, lg));
    }

    public static int algorithm(int n) {
        int cycleLength = 1;

        while (n != 1) {
            if (n % 2 == 0) {
                n >>= 1;
                cycleLength++;
            } else {
                n = 3 * n + 1;
                n >>= 1;
                cycleLength += 2;
            }
        }

        return cycleLength;
    }

    public static void main(String[] args) {
        String input;

        StringTokenizer iData;

        int a, b, i, j;

        while ((input = readLn (255)) != null) {
            iData = new StringTokenizer(input);

            a = Integer.parseInt (iData.nextToken());
            b = Integer.parseInt (iData.nextToken());

            i = a;
            j = b;

            if (i > j) {
                int temp = i;
                i = j;
                j = temp;
            }

            int maxCycleLength = 0;

            while (i <= j) {
                int cycleLength = algorithm(i);

                if (cycleLength > maxCycleLength)
                    maxCycleLength = cycleLength;

                i++;
            }

            System.out.println (a + " " + b + " " + maxCycleLength);
        }
    }
}
