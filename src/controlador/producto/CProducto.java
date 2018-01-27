/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.producto;

import com.mxrck.autocompleter.TextAutoCompleter;
import beans.Producto;
import controlador.principal.CMenu;
import frame.producto.UIProducto;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JosephAWB
 */
public class CProducto implements IProducto
{
    
    private UIProducto ventana;
    private ArrayList<Producto> productos;
    
    public CProducto()
    {
        productos = Producto.getLista();
        ventana = new UIProducto(this);
    }

    @Override
    public void cargar(JTable tblRegistros) 
    {
        DefaultTableModel model = (DefaultTableModel) tblRegistros.getModel();
        model.setRowCount(0);
		
        int productosSize = productos.size();
        for(int i = 0; i < productosSize; i++)
        {
            model.addRow(new Object[]{  productos.get(i).getProCod(),
                                        productos.get(i).getProDes()
                                     });
        }
    }

    @Override
    public void menu() 
    {
        new CMenu();
        ventana.dispose();
    }

    @Override
    public void modificar(JTable tblRegistros) 
    {
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {
            Producto p = productos.get(i);
            CProductoMod modificar = new CProductoMod(p.getProCod());
            ventana.dispose();
        }
        else
            JOptionPane.showMessageDialog(null, "Seleccione un registro a modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void eliminar(JTable tblRegistros) 
    {
        int i = tblRegistros.getSelectedRow();
        if(i != -1)
        {
            Producto p = productos.get(i);
            if(JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                p.eliminar();
                new CProducto();
                ventana.dispose();
            }
        }
        else
             JOptionPane.showMessageDialog(null, "Seleccione un registro a eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void insertar() 
    {
        new CProductoIns();
        ventana.dispose();
    }

    @Override
    public void buscarProducto(JTextField buscar, JTable tblRegistros) 
    {
        TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( buscar );
        textAutoAcompleter.setMode(-1); // prefijo
        textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
        TableModel tableModel = tblRegistros.getModel();
        String filtro = "Descripción";
        
        int i;
        int column = tableModel.getColumnCount();
        
	for(i = 0; i < column; i++)
        {
            if(filtro.compareTo(tableModel.getColumnName(i)) == 0)
                break;
        }
        int row = tableModel.getRowCount();
        for(int k = 0; k < row; k++)
        {
            textAutoAcompleter.addItem(tableModel.getValueAt(k, i));
        }
    }

    @Override
    public void seleccionarFila(JTextField buscar, JTable tblRegistros) 
    {
        TableModel tableModel = tblRegistros.getModel();
        String dato = buscar.getText();
        String filtro = "Descripción";
        int col;
		int column = tableModel.getColumnCount();
        for(col = 0; col < column; col++)
            if(filtro.compareTo(tableModel.getColumnName(col)) == 0)
                break;
        int row;
        try
        {
			int rowC = tableModel.getRowCount();
            for(row = 0; row < rowC; row++)
                if(dato.compareTo((String) tableModel.getValueAt(row, col)) == 0)
                    break;

            if(row == 0)
                tblRegistros.changeSelection(0,0,false,true);
            else
                tblRegistros.getSelectionModel().setSelectionInterval(row - 1, row);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "No se encontraron los datos buscados", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
