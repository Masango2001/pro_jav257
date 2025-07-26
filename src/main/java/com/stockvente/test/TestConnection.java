/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockvente.test;

import com.stockvente.utils.DatabaseConnect;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author masan
 */
public class TestConnection {
     public static void main(String[] args) throws SQLException {
         Connection conn =DatabaseConnect.getConnection();
        if (conn != null){
        System.out.println("connection reussie!");
    }
        else{
            System.out.println("connection echouee!");
        }
        DatabaseConnect.closeConnection();
            
        }
         
    
}
