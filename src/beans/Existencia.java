/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static gestordeinventarios.Principal.con;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JosephAWB
 */
public class Existencia 
{
    
    private String codPro;
    private String codMar;
    private String cantidad;
    
    public Existencia()
    {
        this("-1", "-1", "NULL");
    }
    
    public Existencia(String codPro, String codMar, String cantidad)
    {
        this.codPro = codPro;
        this.codMar = codMar;
        this.cantidad = cantidad;
    }

    /**
     * @return the codPro
     */
    public String getCodPro() {
        return codPro;
    }

    /**
     * @param codPro the codPro to set
     */
    public void setCodPro(String codPro) {
        this.codPro = codPro;
    }

    /**
     * @return the codMar
     */
    public String getCodMar() {
        return codMar;
    }

    /**
     * @param codMar the codMar to set
     */
    public void setCodMar(String codMar) {
        this.codMar = codMar;
    }

    /**
     * @return the cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    public String insertar(){
        String msg = "";
        try{
            con.ejecutar("INSERT INTO EXISTENCIA VALUES (?, ?, DEFAULT)", new String[] {codMar, codPro}, false);
        }catch(SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String modificar(){
        String msg = "";
        try{
            con.ejecutar("UPDATE EXISTENCIA SET idMarca = ? WHERE idProducto = ?", new String[] {codMar, codPro}, false);
        }catch (SQLException ex){
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String modificar(String cantidad){
        String msg = "";
        try
        {
            con.ejecutar("UPDATE EXISTENCIA SET cantidad = ? WHERE idProducto = ? AND idMarca = ?", new String [] {cantidad, codPro, codMar}, false);
        }catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
    
    public String eliminar()
    {
        String msg = "";
        try
        {
            con.ejecutar("DELETE FROM EXISTENCIA WHERE idProducto = ? AND idMarca = ?", new String[] {codPro, codMar}, false);
        }catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
    }
     
    public static String eliminar(String codigoProducto)
     {
        String msg = "";
        try
        {
            con.ejecutar("DELETE FROM EXISTENCIA WHERE idProducto = ?", new String[] {codigoProducto}, false);
        }catch (SQLException ex)
        {
            msg = ex.getMessage();
        }
        return msg;
     }
     
    public static ArrayList<Existencia> getLista(){
        ArrayList<Existencia> existencias = new ArrayList<>();
        try{
            ResultSet rs = con.ejecutar("SELECT * FROM EXISTENCIA ORDER BY idProducto ASC", null, true);
            while(rs.next()){
                String marca = rs.getString("idMarca");
                String codigo = rs.getString("idProducto");                
                String cantidad = rs.getString("cantidad");
                Existencia existencia = new Existencia(codigo, marca, cantidad);
                existencias.add(existencia);
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return existencias;
    }
    
    public static Existencia buscar(String codPro, String codMar){
        Existencia e = null;
        try{
            ResultSet rs = con.ejecutar("SELECT * FROM EXISTENCIA WHERE idProducto = ? and idMarca = ?", new String[] {codPro, codMar}, true);
            rs.next();
            e = new Existencia();
            e.setCodPro(rs.getString("idProducto"));
            e.setCodMar(rs.getString("idMarca"));
            e.setCantidad(rs.getString("cantidad"));
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return e;
    }
        
    public static ArrayList<ArrayList<Kardex>> getOperaciones(String proCod, String marCod)
    {
        ArrayList<ArrayList<Kardex>> operaciones = new ArrayList<>();
        try
        {
            ResultSet rs = con.ejecutar("SELECT * FROM KARDEX WHERE idProducto = ? AND idMarca = ?", new String [] {proCod, marCod}, true);
            while(rs.next())
            {
                ArrayList<Kardex> operacion = new ArrayList<>();
                Kardex k = new Kardex();
                k.setKarCod(rs.getString("idKardex"));
                k.setProCod(rs.getString("idProducto"));
                k.setMarCod(rs.getString("idMarca"));
                k.setKarAnio(rs.getString("año"));
                k.setKarMes(rs.getString("mes"));
                k.setKarDia(rs.getString("dia"));
                k.setUsrCod(rs.getString("idUsuario"));
                k.setKarCan(rs.getString("cantidad"));
                k.setKarValUni(rs.getString("precioUni"));
                k.setKarValTot(rs.getString("precioTot"));
                k.setKarOpe(rs.getString("tipoOpe"));
                k.setKarObs(rs.getString("observacion"));
                
                operacion.add(k);
                operaciones.add(operacion);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.\nConfigure la conexión correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return operaciones;
    }
    
    
}
