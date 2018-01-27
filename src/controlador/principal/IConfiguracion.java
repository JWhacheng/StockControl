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
public interface IConfiguracion 
{
    
    /**
     * Carga los parámetros de host y usuario del archivo 'conexion.dat' a los
     * campos de texto correspondientes.
     * @param txtHost       Campo de texto para Host (Ejemplo: localhost)
     * @param txtUsuario    Campo de texto para usuario de la base de datos
     */
    public void cargar(JTextField txtHost, JTextField txtUsuario);
    
    /**
     * Verifica que la configuración establecida por el usuario sea correcta,
     * realiza un intento de conexión y retorna mediante mensajes si la conexión
     * fue realizada exitosamente o no.
     * @param txtHost       Campo de texto para host de la BD
     * @param txtUsuario    Campo de texto para usuario de la base de datos
     * @param txtPass       Campo de texto para la contraseña de usuario
     * @param lblEstado     Etiqueta que indica el resultado del mensaje
     */
    public void verificar(JTextField txtHost, JTextField txtUsuario, JPasswordField txtPass, JLabel lblEstado);
    
    /**
     * Acepta y actualiza los datos del archivo de configuración 'conexion.dat'
     * con los ingresados por el usuario.
     * @param txtHost       Campo de texto para host de la BD
     * @param txtUsuario    Campo de texto para usuario de la base de datos
     * @param txtPass       Campo de texto para la contraseña de usuario
     */
    public boolean aceptar(JTextField txtHost, JTextField txtUsuario, JPasswordField txtPass);
    
    public boolean salir();
    
}
