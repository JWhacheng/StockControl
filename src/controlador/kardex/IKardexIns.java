/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public interface IKardexIns 
{
    public void cancelar();
    public void calcular(JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot);
    public void cargar(JTextField txtKarCod, JTextField txtProCod, JTextField txtMarCod);
    public void aceptar(JTextField txtKarCod, JTextField txtProCod, JTextField txtMarCod, JDateChooser fecha, JComboBox cbxOpe, JTextField txtCan, JTextField txtPreUni, JTextField txtPreTot, JTextArea txtObs);
}
