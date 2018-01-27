/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Connection;

/**
 *
 * @author JosephAWB
 */
public class Conexion {
    
    private Connection con;
    private String host;
    private String database;
    private String user;
    private String password;
    
    public Conexion(String host, String database, String user, String password)
    {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean conectar(boolean verificar){
        boolean ok = true;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, password);
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            if(!verificar)
                JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
            ok = false;
        }
        return ok;
    }
    
    public ResultSet ejecutar(String comando, String[] data,  boolean receive) throws SQLException
    {
        ResultSet rs = null;
        if(con != null)
        {
            PreparedStatement preparedStmt = con.prepareStatement(comando);
            if(data != null)
                for(int i = 0; i < data.length; i++)
                {
                    preparedStmt.setString(i + 1, data[i]);
                }
            if(receive)
                rs = preparedStmt.executeQuery();
            else
                preparedStmt.execute();
        }
        return rs;
    }
    
    public void desconectar()
    {
        try
        {
            con.close();
        }
        catch (SQLException | NullPointerException ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Access denied for User: " + user + ", Password: " + password + ". Configure DB connection.");
        }
    }
}
