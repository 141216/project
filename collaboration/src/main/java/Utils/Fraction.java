package Utils;

import java.util.ArrayList;
import java.util.List;

public class Fraction {
    public String[] MakeFraction(int operationNum) {
        String[] s = new String[100];
        String[] op2 = { "+", "-" };
        int denominator = 1;
        int numerator = 1;
        int denominator1 = (int) (Math.random() * 19) + 1;
        int numerator1 = (int) (Math.random() * 20);
        if (numerator1 != 0) {
            if (numerator1 > denominator1) {
                int temp = numerator1;
                numerator1 = denominator1;
                denominator1 = temp;
            }
            if (numerator1 == denominator1) {
                numerator1 = (int) (Math.random() * 20);
            }
            int gcd1 = gcd(numerator1, denominator1);
            denominator1 = denominator1 / gcd1;
            numerator1 = numerator1 / gcd1;
        }
        String question1 = numerator1 + "/" + denominator1;
        int count = operationNum;
        for (int u = 0; u < count; u++) {
            int denominator2 = (int) (Math.random() * 19) + 1;
            int numerator2 = (int) (Math.random() * 20);
            if (numerator2 != 0) {
                if (numerator2 > denominator2) {
                    int temp = numerator2;
                    numerator2 = denominator2;
                    denominator2 = temp;
                }
                if (numerator2 == denominator2) {
                    numerator2 = (int) (Math.random() * 20);
                }
                int gcd2 = gcd(numerator2, denominator2);
                denominator2 = denominator2 / gcd2;
                numerator2 = numerator2 / gcd2;
            }
            int symbol = (int) (Math.random() * 2);
            if (op2[symbol].equals("+")) {
                if (denominator1 == denominator2) {
                    numerator = numerator1 + numerator2;
                } else {
                    denominator = denominator1 * denominator2;
                    numerator = numerator1 * denominator2 + numerator2 * denominator1;
                }
                if (denominator < numerator) {
                    u--;
                } else {
                    int gcd = gcd(numerator, denominator);
                    denominator = denominator / gcd;
                    numerator = numerator / gcd;
                    question1 += op2[symbol] + numerator2 + "/" + denominator2;
                    denominator1 = denominator;
                    numerator1 = numerator;
                }
            } else {
                if (denominator1 == denominator2) {
                    numerator = numerator1 - numerator2;
                } else {
                    denominator = denominator1 * denominator2;
                    numerator = numerator1 * denominator2 - numerator2 * denominator1;
                }
                if (numerator < 0) {
                    u--;
                } else {
                    int gcd = gcd(numerator, denominator);
                    denominator = denominator / gcd;
                    numerator = numerator / gcd;
                    question1 += op2[symbol] + numerator2 + "/" + denominator2;
                    denominator1 = denominator;
                    numerator1 = numerator;
                }
            }
        }
        s[0] = question1;
        s[1] = numerator + "/" + denominator;
        System.out.println(s[0] + " = " + s[1]);
        return s;
    }

    public int gcd(int x, int y) {
        if (x == 0) {
            return y;
        } else {
            return gcd(y % x, x);
        }
    }

    public List<String> MakeQuestion(int n, int p) {
        String result[] = new String[100];
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result = MakeFraction(p);
            resultList.add(result[0]);
            resultList.add(result[1]);
        }
        return resultList;
    }
}
