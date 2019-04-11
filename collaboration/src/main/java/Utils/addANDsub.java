package Utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class addANDsub {
    String[] op = { "+", "-" };//指定生成表达式所需运算符
    String[] op1 = { "+" };//指定连接表达式的运算符——避免了负数
    int ifSame=0;
    int answer=0;

    private String MakeQuestion1(int randomNum1,int randomNum2) {//生成一个表达式
        int a = (int) (Math.random() * (randomNum2-randomNum1)) + randomNum1;//随机生成randomNum1---randomNum2的数
        int b = (int) (Math.random() * (randomNum2-randomNum1)) + randomNum1;
        int c = (int) (Math.random() * 2);//随机生成0-2的数，用于指定运算符
        if (c == 1) {//减法注意是大减小
            if (a < b) {
                int temp = a;
                a = b;
                b = temp;
            }
        }
        String m = a + op[c] + b;//生成两个数的表达式
        return m;
    }

    private String MakeQuestion2(int p,int flag,int randomNum1,int randomNum2) {
        String question = "";//初始化
        //3个运算符生成2个式子相连，5个运算符生成3个式子相连，4个运算符生成2个式子相连再加一个数
        for (int i = 0; i < p; i++) {
            if (i < p - 1) {
                String con = op1[0];
                question += MakeQuestion1(randomNum1,randomNum2) + con;
            }
            //生成式子时，3个运算符就循环一次再加一个式子即可；4或5个运算符需要判断循环两次后加数还是加式子
            else {
                if(p == 3){//循环两次，生成两个式子
                    if(flag == 0)//表示这是五个运算符
                        question += MakeQuestion1(randomNum1,randomNum2);
                    else{//这是四个运算符
                        question += (int) (Math.random() * (randomNum2-randomNum1)) + randomNum1;
                    }
                }
                else{
                    question += MakeQuestion1(randomNum1,randomNum2);
                }
            }
        }
        return question;
    }

    private String[] MakeQuestion3(int p,int randomNum1,int randomNum2) {

        int num;
        int flag = 0;
        if(p == 5){
            num = 3;
        }
        else if(p == 4){
            num = 3;
            flag = 1;
        }
        else{
            num = 2;
        }
        String question = MakeQuestion2(num,flag,randomNum1,randomNum2);
        Caculate caculate = new Caculate();
        return caculate.Result(p,question);

    }

    public List<String> MakeQuestion(int n, int p, int randomNum1, int randomNum2) {
        String result[] = new String[1000];
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result = MakeQuestion3(p,randomNum1,randomNum2);
            if(ifSame==1||answer==1){
                i--;
                ifSame=0;
                answer=0;
            }
            else{
                resultList.add(result[0]);
                resultList.add(result[1]);
            }
        }
        return resultList;
    }

}
