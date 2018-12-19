/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jafis.db.local;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author taufik
 */
public class sqliteutil {
    private static Connection con;
    private static void getConnection() throws  ClassNotFoundException,SQLException {
     // sqlite driver
     Class.forName("org.sqlite.JDBC");
     // database path, if it's new database, it will be created in the project folder
     con = DriverManager.getConnection("jdbc:sqlite:db/JAFIS.db");
   
}
    public static void datajafisInsert(String nama, Date tglLahir, String noreg, String rumusan) throws ClassNotFoundException,SQLException{
     if(con == null) {
         getConnection();
     }
         Calendar cal = new GregorianCalendar();
         cal.setTimeInMillis(new Date().getTime());
         PreparedStatement prep = con.prepareStatement("insert into fingerdata1(nama, tgl_lahir, noreg, rumusan) values(?,?,?,?)");
         prep.setString(1, nama);
         prep.setDate(2, new java.sql.Date(tglLahir.getTime()));
         prep.setString(3, noreg);
         prep.setString(4, rumusan);
         prep.execute();
         ResultSet res = prep.getGeneratedKeys();
         int key = 0;
         if(res.next()){
            PreparedStatement prep2 = con.prepareStatement("insert into fingerdata1(nama, tgl_lahir, noreg, rumusan) values(?,?,?,?)");
            prep2.setString(1, nama);
            prep2.setDate(2, new java.sql.Date(tglLahir.getTime()));
            prep2.setString(3, noreg);
            prep2.setString(4, rumusan);
           // prep.execute();
              System.out.println(" wew "+  res.getInt(1));
         }
        
     
    }
     public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws ClassNotFoundException,SQLException {
       // createNewDatabase("JAFIS.db");
     datajafisInsert("111" , new Date(), "asdasd222", "asdasdasd");
    }
}
