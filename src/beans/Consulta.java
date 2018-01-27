/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import static gestordeinventarios.Principal.con;
import static gestordeinventarios.Principal.conn;

/**
 *
 * @author JosephAWB
 */
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class Consulta 
{
    public static ArrayList<ArrayList<String>> existenciaProducto(String codigoProducto)
    {
        ArrayList<ArrayList<String>> existencias = new ArrayList<>();       
        try
        {        
            ResultSet resultado = con.ejecutar("SELECT * FROM EXISTENCIA WHERE idProducto = ?", new String[] {codigoProducto}, true);
            while(resultado.next())
            {
                ArrayList<String> data = new ArrayList<>();
                String codigo = resultado.getString("idMarca");
                String nombre = Marca.buscar(codigo).getMarNom();
                String cantidad = resultado.getString("cantidad");
                data.add(codigo);
                data.add(nombre);
                data.add(cantidad);
                existencias.add(data);
            }
        }catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return existencias;
    }
    
    public static String existenciaTotal(String codigoProducto)
    {
        String total = "0";
        
        try
        {        
            ResultSet resultado = con.ejecutar("SELECT SUM(cantidad) FROM EXISTENCIA WHERE idProducto = ?", new String[] {codigoProducto}, true);
            resultado.next();
            total = resultado.getString("SUM(cantidad)");
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        return total;
    }
    
    public static ArrayList<ArrayList<String>> operaciones(String codigoProducto, String anio, String ope)
    {
        ArrayList<ArrayList<String>> ops = new ArrayList<>();
        try
        {        
            ResultSet resultado = con.ejecutar("SELECT idMarca, mes, dia, cantidad "
                                                + "FROM KARDEX WHERE idProducto = ? "
                                                + "AND año = ? AND tipoOpe = ? "
                                                + "ORDER BY dia DESC, mes DESC",
                                                new String[] {codigoProducto, anio, ope}, 
                                                true
                                               );
            while(resultado.next())
            {
                ArrayList<String> data = new ArrayList<>();
                String mes = resultado.getString("mes");
                String dia = resultado.getString("dia");
                String fecha = dia + "/" + toStringMes(mes);
                String codMarca = resultado.getString("idMarca");
                String nomMarca = Marca.buscar(codMarca).getMarNom();
                String cantidad = resultado.getString("cantidad");
                
                data.add(fecha);
                data.add(codMarca);
                data.add(nomMarca);
                data.add(cantidad);
                
                ops.add(data);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return ops;
    }
    
    public static ArrayList<String> getAnioOp(String producto, String ope)
    {
        ArrayList<String> anios = new ArrayList<>();       
        try
        {        
            ResultSet resultado = con.ejecutar("SELECT DISTINCT año FROM KARDEX WHERE idProducto = ? AND tipoOpe = ?", new String[] {producto, ope}, true);
            while(resultado.next())
            {
                anios.add(resultado.getString("año"));
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return anios;
    }
    
    public static ArrayList<ArrayList<String>> getMarcaCantidad(String codProducto, String anio, String ope)
    {
        ArrayList<ArrayList<String>> detalle = new ArrayList<>();
        try
        {
            ResultSet resultado = con.ejecutar("SELECT DISTINCT idMarca, SUM(cantidad) FROM KARDEX "
                                                + "WHERE idProducto = ? "
                                                + "AND año = ? "
                                                + "AND tipoOpe = ? "
                                                + "GROUP BY idMarca", 
                                                new String[] {codProducto, anio, ope}, 
                                                true
                                               );
            while(resultado.next())
            {
                ArrayList<String> data = new ArrayList<>();
                data.add(resultado.getString("idMarca"));
                data.add(Marca.buscar(resultado.getString("idMarca")).getMarNom());
                data.add(resultado.getString("SUM(cantidad)"));
                
                detalle.add(data);
            }
        }catch(SQLException ex)
        {
           JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE); 
        }
        return detalle;
    }
        
    public static String toStringMes(String mes)
    {
        String mesString = "";
        int mesNum = Integer.parseInt(mes);
        switch  (mesNum)
        {
            case 1: mesString = "Ene";
                    break;
            case 2: mesString = "Feb";
                    break;
            case 3: mesString = "Mar";
                    break;
            case 4: mesString = "Abr";
                    break;
            case 5: mesString = "May";
                    break;
            case 6: mesString = "Jun";
                    break;
            case 7: mesString = "Jul";
                    break;
            case 8: mesString = "Ago";
                    break;
            case 9: mesString = "Set";
                    break;
            case 10: mesString = "Oct";
                    break;
            case 11: mesString = "Nov";
                    break;
            case 12: mesString = "Dic";
                    break;
        }
        return mesString;
    }
    
    public static String[] getData()
    {
        String[] data = new String[3];
        try
        {
            ResultSet rs = conn.ejecutar("SELECT hostName, userName, contra FROM CONEXION WHERE idConexion  = 1",new String[] {}, true);
            while(rs.next())
            {
                data[0] = rs.getString("hostName");
                data[1] = rs.getString("userName");
                data[2] = rs.getString("contra");
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());
        }
        return data;
    }
    
}
