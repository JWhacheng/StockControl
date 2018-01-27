/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.producto;

import beans.Marca;
import beans.Producto;
import frame.producto.UIProductoMod;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author JosephAWB
 */
public class CProductoMod implements IProductoMod
{
    
    private UIProductoMod ventana;
    private Producto p;
    ArrayList<Marca> marcas;
    
    public CProductoMod(String codigo)    
    {
        p = Producto.buscar(codigo);
        marcas = Marca.getLista();
        ventana = new UIProductoMod(this);
    }

    @Override
    public void cargar(JTextField txtProCod, JTextField txtProNom) 
    {
        txtProCod.setText(p.getProCod());
        txtProNom.setText(p.getProDes());
    }

    @Override
    public void cancelar() 
    {
        new CProducto();
        ventana.dispose();
    }
    
    @Override
    public void aceptar(JTextField txtProCod, JTextField txtProNom) 
    {
       p.setProDes(txtProNom.getText());
       
       String err = p.modificar();
        if(err.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Se ha modificado el registro", "MODIFICACIÓN", JOptionPane.INFORMATION_MESSAGE);
            new CProducto();
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
}
