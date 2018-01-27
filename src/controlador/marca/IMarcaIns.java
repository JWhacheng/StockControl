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
public interface IMarcaIns
{
    
    public void cargar(JTextField txtMarCod);
    public void aceptar(JTextField txtMarCod, JTextField txtMarNom);
    public void cancelar();
    
    
}
