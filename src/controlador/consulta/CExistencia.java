    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.consulta;


import beans.Consulta;
import beans.Producto;
import beans.Reporte;
import controlador.helper.CProductoHelp;
import controlador.principal.CMenu;
import frame.consulta.UIExistencia;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JosephAWB
 */
public class CExistencia implements IExistencia
{
    private ArrayList<ArrayList<String>> resultado;
    protected UIExistencia ventana;
    private boolean consultaRealizada;
    
    public CExistencia()
    {
        consultaRealizada = false;
        resultado = new ArrayList<ArrayList<String>>();
        ventana = new UIExistencia(this);
    }

    @Override
    public void verProducto(JTextField txtProCod, JTextField txtProDes) 
    {
        new CProductoHelp(ventana, txtProCod, txtProDes);
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void consultar(JTable tblConsultas, JTextField txtProCod, JTextField txtTotal) 
    {
        if(txtProCod.getText().length() != 0)
        {
            resultado = Consulta.existenciaProducto(txtProCod.getText());
            DefaultTableModel model = (DefaultTableModel) tblConsultas.getModel();
            model.setRowCount(0);
            
            for(int i = 0; i < resultado.size(); i++)
            {
                model.addRow(new Object[]   {
                                                resultado.get(i).get(0),
                                                resultado.get(i).get(1),
                                                resultado.get(i).get(2)
                                            });
            }
            
            txtTotal.setText(Consulta.existenciaTotal(txtProCod.getText()));
            consultaRealizada = true;
            
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un producto", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void generarReporte(JTextField txtProCod, JTextField txtCantidad) 
    {
        if(consultaRealizada)
        {
            Producto p = Producto.buscar(txtProCod.getText());
            Reporte.generarReporte1(p, resultado, txtCantidad);
        }
        else
            JOptionPane.showMessageDialog(null, "Realize primero una consulta", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
