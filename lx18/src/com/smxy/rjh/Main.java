
package com.smxy.rjh;



import java.util.Arrays;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int[][] a = new int[7][8];
            int[][] v = new int[5][];
            for (int j = 1; j < 6; j++) {
                for (int k = 1; k < 7; k++) {
                    a[j][k] = -sc.nextInt();
                }
            }
            //int b = 0;
            emt(a, v, new int[]{0, -1, 0, 0, -1, -1, -1, 0});
            //while (!wtm(a, v, b)) {
            //++b;
            //}
        }

    }

    public static  boolean wtm(int[][] e, int[][] v, int bb) {
        int[] b = new int[8];
        int[][] a = new int[7][];
        for (int i = 0; i < e.length; i++) {
            a[i] = Arrays.copyOf(e[i], 8);
        }
        String sb = Integer.toBinaryString(bb);
        sb = String.format("%06d", Integer.parseInt(sb));
        System.out.println("[" + sb + "]");
        for (int i = 1; i < 7; i++) {
            b[i] = -(sb.charAt(i - 1) - '0');
        }
        return emt(a, v, b);
    }

    public static boolean emt(int[][] a, int[][] v, int[] b) {
        v[0] = b;
        eu(a, 0, b);
        for (int j = 1; j < 5; j++) {
            v[j] = Arrays.copyOf(a[j], 8);
            eu(a, j, a[j]);
        }
        for (int j = 1; j < a[5].length - 1; j++) {
            if (a[5][j] == -1){
                return false;
            }
        }
        for (int[] ints : v) {
            for (int k = 1; k < ints.length - 1; k++) {
                System.out.print(-ints[k] + " ");
            }
            System.out.println();
        }
        return true;
    }

    public static void eu(int[][] a, int n, int[] b) {
        ++n;
        for (int i = 1; i < b.length - 1; i++) {
            if (b[i] == -1) {
                chick(a, n, i);
            }
        }
    }


    public static void chick(int[][] a, int i, int j) {
        a[i][j] = ~a[i][j];
        a[i - 1][j] = ~a[i - 1][j];
        a[i][j - 1] = ~a[i][j - 1];
        a[i + 1][j] = ~a[i + 1][j];
        a[i][j + 1] = ~a[i][j + 1];

    }
}

