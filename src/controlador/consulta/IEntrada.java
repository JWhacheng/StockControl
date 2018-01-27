/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.consulta;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IEntrada 
{
    public void verProducto(JTextField txtProCod, JTextField txtProDes, JComboBox cbxAnio);
    public void menu();
    public void consultar(JTable tblConsultas, JTextField txtProCod);
    public void generarReporte(JTextField txtProCod, JComboBox cbxAnio);
    public void actualizarAnio(JComboBox cbxAnio);
}
