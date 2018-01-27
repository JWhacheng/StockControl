/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.producto;

import beans.Marca;
import beans.Producto;
import frame.producto.UIProductoIns;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CProductoIns implements IProductoIns 
{
    private UIProductoIns ventana;
    ArrayList<Marca> marcas;
    
    public CProductoIns()
    {
       marcas = Marca.getLista();
       ventana = new UIProductoIns(this);
    }

    @Override
    public void cancelar() 
    {
        new CProducto();
        ventana.dispose();
    }

    @Override
    public void cargar(JTextField txtProCod) 
    {
        txtProCod.setText(Producto.sgtCodigo());
    }

    @Override
    public void aceptar(JTextField txtProCod, JTextField txtProNom) 
    {
        Producto p = new Producto(txtProCod.getText(), txtProNom.getText());
        String err = p.insertar();
        if(err.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Se ha agregado el registro nuevo", "INSERCION", JOptionPane.INFORMATION_MESSAGE);
            new CProducto();
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
}
