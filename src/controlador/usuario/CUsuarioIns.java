/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import beans.Usuario;
import frame.usuario.UIUsuarioIns;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CUsuarioIns implements IUsuarioIns
{

    private UIUsuarioIns ventana;
    
    public CUsuarioIns()
    {
        ventana = new UIUsuarioIns(this);
        
    }
    @Override
    public void cancelar() 
    {
        new CUsuario();
        ventana.dispose();
    }

    @Override
    public void aceptar(JFormattedTextField txtDNI, JTextField txtUsrIde, JPasswordField txtCon, JPasswordField txtRepCon, JTextField txtUsrNom, JTextField txtUsrApe, JRadioButton rbAdmin) 
    {
        String permiso = "0";
        if(rbAdmin.isSelected())
        {
            permiso = "1";
        }
        
        Usuario u = new Usuario(txtDNI.getText(),
                                txtUsrIde.getText(),
                                txtUsrNom.getText(),
                                txtUsrApe.getText(),
                                permiso);
        
        if(String.valueOf(txtRepCon.getPassword()).equals(String.valueOf(txtCon.getPassword())))
        {
           if(String.valueOf(txtCon.getPassword()).length() >= 5 && String.valueOf(txtCon.getPassword()).length() <= 16)
            {
                String err = u.insertar(String.valueOf(txtRepCon.getPassword()));
                if(err.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
                    new CUsuario();
                    ventana.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener entre 5 y 16 caracteres.", "ERROR", JOptionPane.ERROR_MESSAGE);
                txtCon.setText("");
                txtRepCon.setText("");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.\nIntente de nuevo", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtCon.setText("");
            txtRepCon.setText("");
        }
    }
       
}
