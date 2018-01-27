/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.principal;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface ILogin {
    
    /**
     * Valida el nombre de usuario y la contraseña ingresados, si los datos
     * son correctos accede a la ventana del menú principal.
     * @param txtUsuario    Nombre de usuario
     * @param txtPass       Contraseña
     */
    public void validar(JTextField txtUsuario, JPasswordField txtPass);
    
    /**
     * Acceso a la ventana de configuración de la base de datos.
     */
    public void configuracion();
    
    /**
     * Sale del software.
     */
    public void salir();
    
}
