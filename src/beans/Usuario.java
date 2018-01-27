/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;

import static gestordeinventarios.Principal.con;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class Usuario {
    
    private String usrDni;
    private String usrIde;
    private String usrNom;
    private String usrApe;
    private String usrPer;
    
    public Usuario()
    {
        this("00000000", "NULL", "NULL", "NULL", "0");
    }
    
    public Usuario(String usrDni, String usrIde, String usrNom, String usrApe, String usrPer)
    {
        this.usrIde = usrIde;
        this.usrDni = usrDni;
        this.usrNom = usrNom;
        this.usrApe = usrApe;
        this.usrPer = usrPer;
    }

    public String getUsrIde()
    {
        return usrIde;
    }

    public void setUsrIde(String usrIde)
    {
        this.usrIde = usrIde;
    }

    public String getUsrDni()
    {
        return usrDni;
    }

    public void setUsrDni(String usrDni)
    {
        this.usrDni = usrDni;
    }

    public String getUsrNom()
    {
        return usrNom;
    }

    public void setUsrNom(String usrNom)
    {
        this.usrNom = usrNom;
    }

    public String getUsrApe()
    {
        return usrApe;
    }

    public void setUsrApe(String usrApe)
    {
        this.usrApe = usrApe;
    }

    public String getUsrPer()
    {
        return usrPer;
    }

    public void setUsrPer(String usrPer)
    {
        this.usrPer = usrPer;
    }

    
    public static Usuario validar(String usr, String pass)
    {
        Usuario user = null;
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM USUARIO WHERE BINARY identificacion = ? AND contraseña = MD5(?)", new String[] {usr, pass}, true);
            rs.next();
            if(rs.isLast())
            {
                user = new Usuario();
                user.setUsrDni(rs.getString("idUsuario"));
                user.setUsrIde(rs.getString("identificacion"));
                user.setUsrNom(rs.getString("nombre"));
                user.setUsrApe(rs.getString("apellido"));
                user.setUsrPer(rs.getString("permiso"));
            }
        }
        catch (SQLException | NullPointerException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }
    
    public String insertar(String pass)
    {
	String msg = "";
        try
        {
            con.ejecutar("INSERT INTO USUARIO VALUES(?, ?, MD5(?), ?, ?, ?)", new String[] {usrDni, usrIde, pass, usrNom, usrApe, usrPer}, false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String modificar(String pass)
    {
	String msg = "";
        try
        {
            con.ejecutar("UPDATE USUARIO SET idUsuario = ?, identificacion = ?, contraseña = MD5(?), nombre = ?, apellido = ?, permiso = ? WHERE idUsuario = ?", new String[] {usrDni, usrIde, pass, usrNom, usrApe, usrPer, usrDni}, false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String eliminar()
    {
	String msg = "";
        try
        {
            con.ejecutar("DELETE FROM USUARIO WHERE idUsuario = ?", new String[] {usrDni}, false);
        }
        catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public static ArrayList<Usuario> getLista()
    {
        ArrayList<Usuario> usuarios = new ArrayList<> ();
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM usuario", null, true);
            
            while(rs.next())
            {
                String codigo = rs.getString("idUsuario");
                String ident = rs.getString("identificacion");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String permiso = rs.getString("permiso");
                
                Usuario u = new Usuario(codigo, ident, nombre, apellido, permiso);
                usuarios.add(u);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        return usuarios;
    }
    
    public static Usuario buscar(String codigo)
    {
        Usuario u = null;
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM USUARIO WHERE idUsuario = ?", new String[] {codigo}, true);
            rs.next();
            u = new Usuario();
            u.setUsrDni(rs.getString("idUsuario"));
            u.setUsrIde(rs.getString("identificacion"));
            u.setUsrNom(rs.getString("nombre"));
            u.setUsrApe(rs.getString("apellido"));
            u.setUsrPer(rs.getString("permiso"));
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return u;
    }
    
}
