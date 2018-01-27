/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.marca;

import beans.Marca;
import frame.marca.UIMarcaMod;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CMarcaMod implements IMarcaMod
{
    
    private UIMarcaMod ventana;
    private Marca m;
    
    public CMarcaMod(String codigo)
    {
        m = Marca.buscar(codigo);
        ventana = new UIMarcaMod(this);
    }
    
    @Override
    public void cargar(JTextField txtMarCod, JTextField txtMarNom) 
    {
        txtMarCod.setText(m.getMarCod());
        txtMarNom.setText(m.getMarNom());
    }

    @Override
    public void aceptar(JTextField txtMarNom) 
    {
        m.setMarNom(txtMarNom.getText());
        String err = m.modificar();
        
        if(err.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Se ha modificado el registro", "MODIFICACIÓN", JOptionPane.INFORMATION_MESSAGE);
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
