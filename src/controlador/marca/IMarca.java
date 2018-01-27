/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.marca;

import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IMarca 
{
    
    public void cargar(JTable tblRegistros);
    public void menu();
    public void insertar();
    public void modificar(JTable tblRegistros);
    public void eliminar(JTable tblRegistros);
    public void buscarMarca( JTextField buscar, JTable tablaMarca);
    public void seleccionarFila(JTextField buscar, JTable tablaMarca);
    
}
