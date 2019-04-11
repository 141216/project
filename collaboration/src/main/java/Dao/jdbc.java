package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class jdbc {

    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/bluesun?serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    public static Connection getConnection(){
        Connection connect = null;
        try {
            Class.forName(DRIVER);//加载驱动
            connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取与数据库的连接
        }catch (Exception ee){
            ee.printStackTrace();
        }
        return connect;
    }

    public static int add(String fileName){

        String sql = "INSERT INTO file(title) VALUES (?)";//没有设置ID的插入
        int isSucceed = 0;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,fileName);
            isSucceed = statement.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                statement.close();
                conn.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return isSucceed;
    }

    public static List<String> getTitle(){
        String sql="select * from file ";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        conn = getConnection();
        List<String> list = new ArrayList();
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                list.add(rs.getString("title"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                rs.close();
                st.close();
                conn.close();//关闭数据库连接
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }
}
