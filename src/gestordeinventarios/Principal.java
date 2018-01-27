/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestordeinventarios;

import beans.Conexion;
import beans.ConexionInterna;
import beans.Consulta;
import beans.Usuario;
import controlador.principal.CLogin;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author JosephAWB
 */
public class Principal {

    public static Conexion con;
    public static Usuario user;
    public static ConexionInterna conn;
    
    public Principal()
    {
        try
        {        
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        
            ConexionInterna cn = new ConexionInterna();
            conn = new ConexionInterna();
            conn.crearBD();
            conn.cargarDB();
            String[] conexion_data = Consulta.getData();
                      
            con = new Conexion(conexion_data[0], "DIMASAC", conexion_data[1], conexion_data[2]);
            con.conectar(false);
            user = new Usuario();
            
            new CLogin();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex){}
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new Principal();
    }
    
}
