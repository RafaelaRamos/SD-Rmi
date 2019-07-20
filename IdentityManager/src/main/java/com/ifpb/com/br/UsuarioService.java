package com.ifpb.com.br;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Cliente
 */
public class UsuarioService {

    private AtomicInteger id = new AtomicInteger(0);
    
    public Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/sd",
                "postgres", "123");
        return connection;
    }

    public synchronized int IdUsuario() throws SQLException {
        int key = id.incrementAndGet();
        System.out.println("id: " + key);
        return key;
//        String sql = "SELECT id from usuario ORDER BY id DESC LIMIT 1";
//        Connection con = getConnection();
//        PreparedStatement stm = con.prepareStatement(sql);
//        ResultSet rs = stm.executeQuery();
//        int id = 0;
//        while (rs.next()) {
//            id = rs.getInt("id")+1;
//        }
//        System.out.println("id: " + id);
//        stm.close();
//        con.close();
        //return id;
    }
}
