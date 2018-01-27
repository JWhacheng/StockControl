/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IUsuario
{
    
    public void menu();
    public void registrar();
    public void cargar(JTable tblRegistros);
    public void modificar(JTable tblRegistros);
    public void eliminar(JTable tblRegistros);
    public void seleccionarFila(JTextField buscar, JComboBox tipo, JTable tblRegistros);
    
}
