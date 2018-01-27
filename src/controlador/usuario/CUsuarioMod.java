/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import beans.Usuario;
import frame.usuario.UIUsuarioMod;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CUsuarioMod implements IUsuarioMod{

    private UIUsuarioMod ventana;
    private Usuario u;
    
    CUsuarioMod(String usrCod) 
    {
        u = Usuario.buscar(usrCod);
        ventana = new UIUsuarioMod(this);
    }

    @Override
    public void cancelar() 
    {
        new CUsuario();
        ventana.dispose();
    }

    @Override
    public void cargar(JFormattedTextField txtDNI, JTextField txtUsrIde, JTextField txtUsrNom, JTextField txtUsrApe, JRadioButton rbAdmin, JRadioButton rbUsuario) 
    {
        txtDNI.setText(u.getUsrDni());
        txtUsrIde.setText(u.getUsrIde());
        txtUsrNom.setText(u.getUsrNom());
        txtUsrApe.setText(u.getUsrApe());
        if(u.getUsrPer().equals("1"))
        {
            rbAdmin.setSelected(true);
            rbUsuario.setSelected(false);
        }
        else
        {
            rbAdmin.setSelected(false);
            rbUsuario.setSelected(true);
        }
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
                String err = u.modificar(String.valueOf(txtRepCon.getPassword()));
                if(err.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Se ha modificado el registro", "MODIFICACIÓN", JOptionPane.INFORMATION_MESSAGE);
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
