/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.producto;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IProductoMod 
{
    public void cargar(JTextField txtProCod, JTextField txtProNom);
    public void cancelar();
    public void aceptar(JTextField txtProCod, JTextField txtProNom);
}
