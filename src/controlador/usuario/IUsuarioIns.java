/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IUsuarioIns 
{
    public void cancelar();
    public void aceptar(JFormattedTextField txtDNI, JTextField txtUsrIde, JPasswordField txtCon, JPasswordField txtRepCon, JTextField txtUsrNom, JTextField txtUsrApe, JRadioButton rbAdmin);
}
