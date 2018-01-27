/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.marca;

import beans.Marca;
import frame.marca.UIMarcaIns;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CMarcaIns implements IMarcaIns
{
    private UIMarcaIns ventana;
    
    public CMarcaIns()
    {
        ventana = new UIMarcaIns(this);
    }
    @Override
    public void cargar(JTextField txtMarCod) 
    {
        txtMarCod.setText(Marca.sgteCodigo());
    }

    @Override
    public void aceptar(JTextField txtMarCod, JTextField txtMarNom) 
    {
        Marca m = new Marca(txtMarCod.getText(), txtMarNom.getText());
        String err = m.insertar();
        
        if(err.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
            new CMarca();
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void cancelar() 
    {
        new CMarca();
        ventana.dispose();
    }
    
}
