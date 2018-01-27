/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.marca;

import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IMarcaMod 
{
    
    public void cargar(JTextField txtMarCod, JTextField txtMarNom);
    public void aceptar(JTextField txtMarNom);
    public void cancelar();
    
}
