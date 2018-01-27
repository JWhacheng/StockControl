/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IProductoMarca 
{
    public void cancelar();
    public void aceptar(JTextField txtProCod, JTextField txtMarCod);
    public void verProducto(JTextField txtProCod, JTextField txtProDes);
    public void verMarca(JTextField txtMarCod, JTextField txtMarNom);
    
}
