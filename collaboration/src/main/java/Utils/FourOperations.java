package Utils;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FourOperations {
    String[] op = { "+", "-", "*", "÷" };//指定生成表达式所需运算符
    String[] op1 = { "+", "*" };//指定连接表达式的运算符——避免了负数与无理数
    int flag = 0;//用于记录表达式优先级，+，-flag取1，区别是否需要加括号
    int ifSame=0;
    int answer=0;

    public String MakeQuestion1(int randomNum1,int randomNum2) {//生成一个表达式
        int a = (int) (Math.random() * (randomNum2-randomNum1) + randomNum1);//随机生成0-100的数
        int b = (int) (Math.random() * (randomNum2-randomNum1) + randomNum1);//随机生成0-100的数
        int c = (int) (Math.random() * 4);//随机生成0-4的数，用于指定运算符
        if (c == 1) {//减法注意是大减小
            if (a < b) {
                int temp = a;
                a = b;
                b = temp;
            }
        }
        if (c == 3) {//除法注意能整除
            b = (int) (Math.random() * (randomNum2 - randomNum1)) + 1;
            a = (int) (Math.random() * (randomNum2 - randomNum1)) * b;
        }
        String m = a + op[c] + b;//生成两个数的表达式

        if (op[c].equals("-") || op[c].equals("+")) {
            flag = 1;//记录下这个表达式的运算，如果是+，-就是1
        }
        return m;
    }

    public String MakeQuestion2(int p,int judge,int randomNum1,int randomNum2) {//生成p个表达式连接的一个题目
        String question = "";//初始化
        for (int i = 0; i < p; i++) {
            if (i < p - 1) {
                String con = op1[(int) (Math.random() * 2)];//生成连接符+或者*
                if (con.equals("*")) {
                    String que = MakeQuestion1(randomNum1, randomNum2);//生成第一个表达式
                    if (flag == 1) {//
                        question += "(" + que + ")" + con;
                    } else {
                        question += que + con;
                    }
                } else {
                    question += MakeQuestion1(randomNum1, randomNum2) + con;
                }
            } else {
                if(p == 3){
                    if(judge == 0)
                        question += MakeQuestion1(randomNum1,randomNum2);
                    else{//这是四个运算符
                        question += (int) (Math.random() * (randomNum2-randomNum1)) + randomNum1;
                    }
                }
                else{
                    question += MakeQuestion1(randomNum1,randomNum2);
                }
            }
            flag = 0;
        }
        return question;
    }

    public String MakeQuestion2Without(int p,int judge,int randomNum1,int randomNum2) {//生成p个表达式连接的一个题目
        String question = "";//初始化
        for (int i = 0; i < p; i++) {
            if (i < p - 1) {
                String con = op1[(int) (Math.random() * 2)];//生成连接符+或者*
                String que = MakeQuestion1(randomNum1, randomNum2);//生成第一个表达式
                question += MakeQuestion1(randomNum1, randomNum2) + con;
            } else {
                if(p == 3){
                    if(judge == 0)
                        question += MakeQuestion1(randomNum1,randomNum2);
                    else{//这是四个运算符
                        question += (int) (Math.random() * (randomNum2-randomNum1)) + randomNum1;
                    }
                }
                else{
                    question += MakeQuestion1(randomNum1,randomNum2);
                }
            }
            flag = 0;
        }
        return question;
    }

    public String[] MakeQuestion3(int p,int randomNum1,int randomNum2,int is) {
        int num;
        String question = "";
        int judge = 0;
        if(p == 5){
            num = 3;
        }
        else if(p == 4){
            num = 3;
            judge = 1;
        }
        else{
            num = 2;
        }
        if(is == 1){//有括号
            question = MakeQuestion2(num,judge,randomNum1,randomNum2);
        }
        else{
            question = MakeQuestion2Without(num,judge,randomNum1,randomNum2);
        }

        Caculate caculate = new Caculate();
        String result[] = caculate.Result(p,question);
        return result;
    }

    public List<String> MakeQuestion(int n,int p,int random1,int random2,int is) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String s[] = MakeQuestion3(p,random1,random2,is);
            if(ifSame==1||answer==1){
                i--;
                ifSame=0;
                answer=0;
            }
            else{
                list.add(s[0]);
                list.add(s[1]);
            }
        }
        return list;
    }

}