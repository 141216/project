package Controller;

import Dao.jdbc;
import Entity.wrongAnswers;
import Utils.FourOperations;
import Utils.Fraction;
import Utils.addANDsub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class Servlet{

        @RequestMapping("/start")
        @ResponseBody
        private Map<String,Object> Start(HttpServletRequest request){
        int questionNum = Integer.parseInt(request.getParameter("questionNum"));//题目数量
        int randomNum1 = Integer.parseInt(request.getParameter("randomNum1"));//随机数范围randomNum1-randomNum2
        int randomNum2 = Integer.parseInt(request.getParameter("randomNum2"));
        int operationNum = Integer.parseInt(request.getParameter("operationNum"));//运算符数量
        int isFourOperation = Integer.parseInt(request.getParameter("isFourOperation"));//有无乘除，0为无，1为有
        int isBracket = Integer.parseInt(request.getParameter("isBracket"));//有无括号，0为无，1为有
        int isFraction = Integer.parseInt(request.getParameter("isFraction"));//有无分数，0为无，1为有
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Date date = new Date();
        request.getSession().setAttribute("startTime",date);
        if(isFourOperation == 0 && isFraction == 0){//最基础无分数的加减法
            addANDsub op1 = new addANDsub();
            List<String> list =  op1.MakeQuestion(questionNum,operationNum,randomNum1,randomNum2);
            //System.out.println(list.get(2));
            modelMap.put("questionList",list);
        }
        else if(isFourOperation == 0 && isFraction == 1){//基础的有分数加减法
            Fraction op2 = new Fraction();
            List<String> list = op2.MakeQuestion(questionNum,operationNum);
            modelMap.put("questionList",list);
        }
        else if(isFourOperation == 1 && isBracket == 0){//无括号的四则运算
            FourOperations op3 = new FourOperations();
            int is=0;
            List<String> list = op3.MakeQuestion(questionNum,operationNum,randomNum1,randomNum2,is);
            modelMap.put("questionList",list);
            modelMap.put("startTime",date);
        }
        else if(isFourOperation == 1 && isBracket == 1){//有括号的四则运算
            FourOperations op4 = new FourOperations();
            int is=1;
            List list = op4.MakeQuestion(questionNum,operationNum,randomNum1,randomNum2,is);
            modelMap.put("questionList",list);

        }
        else{
            List list = new ArrayList();
            modelMap.put("questionList",list);
        }
        return modelMap;
    }

    @RequestMapping("upload")
    @ResponseBody
    private Map<String,Object> upload(HttpServletRequest request, @RequestBody List<String> wrongAnswer){
        Map<String,Object> modelMap = new HashMap<>();
        Date endTime = new Date();
        try {
            SimpleDateFormat simpleDateFormatTest = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        }catch (Exception e){
            e.printStackTrace();
        }
        String path = request.getServletContext().getRealPath("/files/");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(new Date());
        File file = new File(path,filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedWriter bw = null;

        int isSucceed = jdbc.add(filename);
        System.out.println(filename);
        try {
            bw = new BufferedWriter( new FileWriter(file) );
            for(int i = 0; i < wrongAnswer.size(); i++ ) {
            bw.write( wrongAnswer.get(i) );
            bw.newLine();
        }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        modelMap.put("success",isSucceed);
        modelMap.put("startTime",request.getSession().getAttribute("startTime"));
        modelMap.put("endTime",endTime);

        return modelMap;
    }

    @RequestMapping(value = "/getFile")
    @ResponseBody
    public Map<String,Object> getFile(HttpServletRequest request)throws Exception {
        List<String> list = jdbc.getTitle();
        Map<String,Object> modelMap = new HashMap<>();
        if(list != null){
            modelMap.put("list",list);
            modelMap.put("success",true);
        }
        else{
            modelMap.put("success",false);
            modelMap.put("list",list);
        }
        return modelMap;
    }

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> fileDownLoad(HttpServletRequest request) throws Exception{
        String fileName = request.getParameter("fileName");
        System.out.println(fileName);
        String realPath = request.getServletContext().getRealPath("/files/")+"\\"+fileName;//得到文件所在位置
        InputStream in=new FileInputStream(new File(realPath));//将该文件加入到输入流之中
        byte[] body=null;
        body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
        in.read(body);//读入到输入流里面
        fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        HttpHeaders headers=new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename="+fileName);
        HttpStatus statusCode = HttpStatus.OK;//设置响应码
        ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }

}
