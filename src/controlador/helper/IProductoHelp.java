/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.helper;

import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IProductoHelp 
{
    public void cargar(JTable tblRegistros);
    public boolean aceptar(JTable tblRegistros);
    public void seleccionarFila(JTextField buscar, JTable tblRegistros);
    public void buscarProducto(JTextField buscar, JTable tblRegistros);
}
