package com.hongqiao.jdbc;  
import java.sql.Connection;  
import java.sql.DriverManager;  
/** 
 * DBConnection.java 
 * 功能:测试H2提供的JDBC连接 
 * @author boonya 
 * @version 1.0 2013-03-11 
 */  
public class DBConnection {  
       private static String url="jdbc:h2:~/test";  
       private static String driver="org.h2.Driver";  
       private static String username="sa";  
       private static String password="123456";  
      
       public static Connection getConnection(){  
           Connection conn=null;  
           try{  
               Class.forName(driver);  
               conn=DriverManager.getConnection(url,username,password);  
           }catch(Exception e){  
                System.out.println("DBConnection create connection is failed:");  
                e.printStackTrace();  
           }  
           return conn;  
       }  
         
       public static void main(String[] args) {  
           System.out.println(DBConnection.getConnection());//打印:conn1: url=jdbc:h2:~/test user=SA  
       }  
  
}  
