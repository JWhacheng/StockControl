/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author JosephAWB
 */

public class ConexionInterna 
{
    Connection conInt;
    
    public Connection crearBD()
    {
        String barra = File.separator; 
        String proyecto = System.getProperty("user.dir")+barra+"DB";
        File url = new File(proyecto);
        if(url.exists())
        {
            System.out.println("La base de datos ya existe");
        }
        else
        {   
            try
            {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                String db = "jdbc:derby:"+proyecto+";create=true";
                conInt = DriverManager.getConnection(db);
                
                String table = "CREATE TABLE conexion (idConexion INT PRIMARY KEY, hostName VARCHAR(50) NOT NULL, userName VARCHAR(50) NOT NULL, contra VARCHAR(50) NOT NULL)";
                PreparedStatement ps = conInt.prepareStatement(table);
                ps.execute();
                ps.close();
                
                String datos = "INSERT INTO conexion VALUES (1,'localhost','root','1234')";
                PreparedStatement ps2 = conInt.prepareStatement(datos);
                ps2.execute();
                ps2.close();
                System.out.println("La base de datos ha sido creada");
                return conInt;
            } 
            catch(ClassNotFoundException | SQLException ex){System.out.println("Error: " + ex);}
        }
        return null;
    }
    
    public Connection cargarDB()
    {
        String barra = File.separator; 
        String proyecto = System.getProperty("user.dir")+barra+"DB";
        
        try 
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String bd = "jdbc:derby:"+proyecto;
            conInt = DriverManager.getConnection(bd);
            
            System.out.println("Base de datos cargada");
            return conInt;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ERROR DE CARGA");
        }
        return null;
    }
    
    
    public void cerrarCon()
    {
        try
        {
            conInt.close();
        }catch (SQLException | NullPointerException ex)
        {
        }
    }
    
    // OPERACIONES
    public ResultSet ejecutar(String comando, String[] data,  boolean receive) throws SQLException
    {
        ResultSet rs = null;
        if(conInt != null)
        {
            PreparedStatement preparedStmt = conInt.prepareStatement(comando);
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
    public ResultSet consultaInt(String consulta) throws SQLException
    {
        ResultSet rs = null;
        
        PreparedStatement ps = conInt.prepareStatement(consulta);
        rs = ps.executeQuery();
        
        return rs;
    }
    
    public void consultaMod(String consulta, String[] data) throws SQLException
    {        
        PreparedStatement ps = conInt.prepareStatement(consulta);
        if(data != null)
                for(int i = 0; i < data.length; i++)
                {
                    ps.setString(i + 1, data[i]);
                }
        ps.execute();     
    }
    
}
