package com.example.interviotrial.Database;
import android.annotation.SuppressLint;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnection {
    @SuppressLint("AuthLeak")
    public static Connection getConnection(){
        Connection connection = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://interviioserver.database.windows.net:1433/interviioDB;","interviioAdmin@interviioserver", "Microsoft123$");
            System.out.println("Connected");
        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        return connection;
    }
    public static void close(Object object) {
        try {
            if (object != null) {
                if (object instanceof Connection){
                    ((Connection)object).close();
                }
                else if (object instanceof Statement) {
                    ((Statement)object).close();
                }
                else if(object instanceof PreparedStatement) {
                    ((PreparedStatement)object).close();
                }
                else if (object instanceof CallableStatement) {
                    ((CallableStatement)object).close();
                }
                else if (object instanceof ResultSet) {
                    ((ResultSet)object).close();
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }



}
