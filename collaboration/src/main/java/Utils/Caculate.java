package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Caculate {
    int ifSame=0;
    int answer=0;

    public String[] Result(int p,String question) {
        Stack<String> number = new Stack<String>();
        Stack<Character> operate = new Stack<Character>();
        String[] s = new String[10];
        int len = question.length();
        int k = 0;
        int same=0;
        for (int j = -1; j < len - 1; j++) {
            if (question.charAt(j + 1) == '+' || question.charAt(j + 1) == '-' || question.charAt(j + 1) == '*'
                    || question.charAt(j + 1) == '÷' || question.charAt(j + 1) == '(' || question.charAt(j + 1) == ')'
                    || j == len - 2) {
                if (j == -1) {
                    operate.push(question.charAt(0));
                }
                else if (j == len - 2) {
                    if (question.charAt(len - 1) == ')') {
                        number.push(question.substring(k, len - 1));
                        number.push(String.valueOf(operate.pop()));
                        if (!operate.empty()) {
                            operate.pop();
                        }
                    } else {
                        number.push(question.substring(k));
                    }
                    break;
                } else {
                    if (k <= j) {
                        number.push(question.substring(k, j + 1));
                    }
                    if (operate.empty() || question.charAt(j + 1) == '(') {
                        operate.push(question.charAt(j + 1));
                    } else if ((operate.peek() == '+' || operate.peek() == '-')
                            && (question.charAt(j + 1) == '*' || question.charAt(j + 1) == '÷')) {
                        operate.push(question.charAt(j + 1));
                    } else if (operate.peek() == '(') {
                        operate.push(question.charAt(j + 1));
                    } else if (question.charAt(j + 1) == ')') {
                        number.push(String.valueOf(operate.pop()));
                        if (!operate.empty()) {
                            operate.pop();
                        }
                    } else {
                        if(operate.peek()==question.charAt(j + 1)){
                            same++;
                        }
                        number.push(String.valueOf(operate.pop()));
                        operate.push(question.charAt(j + 1));
                    }
                }
                k = j + 2;
            }
        }
        if(same==p+2){
            ifSame=1;
        }
        while (!operate.empty()) {
            number.push(String.valueOf(operate.pop()));
        }
        String[] result = new String[20];
        int k1 = 0;
        while (!number.empty()) {
            result[k1] = number.pop();
            k1++;
        }
        for (k1 = k1 - 1; k1 >= 0; k1--) {
            if (!result[k1].equals("+") && !result[k1].equals("-") && !result[k1].equals("*")
                    && !result[k1].equals("÷")) {
                number.push(result[k1]);
            } else {
                int a1 = 0;
                int b1 = 0;
                if (!number.empty()) {
                    b1 = Integer.parseInt(number.pop());
                }
                if (!number.empty()) {
                    a1 = Integer.parseInt(number.pop());
                }
                if (result[k1].equals("+")) {
                    int c1 = a1 + b1;
                    number.push(String.valueOf(c1));
                } else if (result[k1].equals("-")) {
                    int c1 = a1 - b1;
                    number.push(String.valueOf(c1));
                } else if (result[k1].equals("*")) {
                    int c1 = a1 * b1;
                    number.push(String.valueOf(c1));
                } else {
                    int c1 = a1 / b1;
                    number.push(String.valueOf(c1));
                }
            }
        }
        if(Integer.parseInt(number.peek())<0){
            answer=1;
        }
        if(ifSame==1||answer==1){
            ;
        }else{

            s[0] = question;
            s[1] = number.pop();
            System.out.println(s[0]+"="+s[1]);
        }
        return s;
    }
}
