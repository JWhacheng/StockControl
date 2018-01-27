/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IKardex 
{
    public void cargar(JTable tblProductos);
    public void menu();
    public void modificar(JTable tblProductos);
    public void insertar(JTable tblProductos);
    public void eliminar(JTable tblProductos);
    public void actualizarOpe(JTable tblRegistros, JTable tblProductos, JTextField txtUsuario, JTextArea txtObs);
    public void actualizar(JTable tblRegistros, JTable tblProductos);
    public void insertarProducto();
    public void eliminarProducto(JTable tblProductos);
    public void buscarProducto( JTextField buscar, JTable tblProductos);
    public void seleccionarFila(JTextField buscar, JTable tblProductos);
}
