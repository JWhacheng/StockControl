/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.principal;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import beans.Conexion;
import static gestordeinventarios.Principal.con;
import static gestordeinventarios.Principal.conn;
import beans.Consulta;
import frame.principal.UIConfiguracion;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class CConfiguracion implements IConfiguracion {
    
    private UIConfiguracion ventana;
    
    public CConfiguracion(JFrame owner)
    {
        con.desconectar();
        ventana = new UIConfiguracion(owner, true, this);
    }

    @Override
    public void cargar(JTextField txtHost, JTextField txtUsuario) 
    {
        conn.cargarDB();
        String[] conexion_data = Consulta.getData();
        txtHost.setText(conexion_data[0]);
        txtUsuario.setText(conexion_data[1]);
    }

    @Override
    public void verificar(JTextField txtHost, JTextField txtUsuario, JPasswordField txtPass, JLabel lblEstado) 
    {
        Conexion newCon = new Conexion(txtHost.getText(), "DIMASAC", txtUsuario.getText(), String.valueOf(txtPass.getPassword()));
        if(newCon.conectar(true))
        {
            lblEstado.setForeground(new Color(0, 150, 0));
            lblEstado.setText("Configuración correcta");
        }
        else
        {
            lblEstado.setForeground(new Color(250, 0, 0));
            lblEstado.setText("Configuración incorrecta");
        }
        newCon.desconectar();
    }

    @Override
    public boolean aceptar(JTextField txtHost, JTextField txtUsuario, JPasswordField txtPass) 
    {
        boolean cambiado = false;
        try
        {
            con.setHost(txtHost.getText());
            con.setUser(txtUsuario.getText());
            con.setPassword(String.valueOf(txtPass.getPassword()));
            
            conn.consultaMod("UPDATE CONEXION SET hostName = ?, userName = ?, contra = ? WHERE idConexion = 1", new String[] {txtHost.getText(), txtUsuario.getText(), String.valueOf(txtPass.getPassword())});
                        
            con.conectar(false);
            
            cambiado = true;
            JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
            
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error AQUI");
        }
        return cambiado;
    }

    @Override
    public boolean salir() 
    {
        con.conectar(false);
        return true;
    }
    
}
