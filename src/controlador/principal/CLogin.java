/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.principal;

import beans.Usuario;
import frame.principal.UILogin;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static gestordeinventarios.Principal.user;
import static gestordeinventarios.Principal.con;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class CLogin implements ILogin
{
    
    private UILogin ventana;
    
    public CLogin()
    {
        this.ventana = new UILogin(this);
    }

    @Override
    public void validar(JTextField txtUsuario, JPasswordField txtPass) 
    {
        Usuario u = Usuario.validar(txtUsuario.getText(), String.valueOf(txtPass.getPassword()));
        if (u != null)
        {
            user = u;
            new CMenu();
            ventana.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE, null);
            txtUsuario.setText("");
            txtPass.setText("");
            txtUsuario.requestFocus();
        }    
    }

    @Override
    public void configuracion() {
        new CConfiguracion(ventana);
    }

    @Override
    public void salir() {
        System.exit(0);
        con.desconectar();
    }
    
    
    
}
