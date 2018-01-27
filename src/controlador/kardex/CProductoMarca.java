/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.kardex;

import beans.Existencia;
import controlador.helper.CMarcaHelp;
import controlador.helper.CProductoHelp;
import frame.kardex.UIProductoMarca;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CProductoMarca implements IProductoMarca
{
    private UIProductoMarca ventana;
    
    public CProductoMarca()
    {
        ventana = new UIProductoMarca(this);
    }

    @Override
    public void cancelar() 
    {
        new CKardex();
        ventana.dispose();
    }

    @Override
    public void aceptar(JTextField txtProCod, JTextField txtMarCod) 
    {
        Existencia e = new Existencia(txtProCod.getText(), txtMarCod.getText(), "0");
        String err = e.insertar();
        
        if(err.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
            new CKardex();
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void verProducto(JTextField txtProCod, JTextField txtProDes) 
    {
        new CProductoHelp(this.ventana, txtProCod, txtProDes);
    }

    @Override
    public void verMarca(JTextField txtMarCod, JTextField txtMarNom) 
    {
        new CMarcaHelp(this.ventana, txtMarCod, txtMarNom);
    }
    
    
}
