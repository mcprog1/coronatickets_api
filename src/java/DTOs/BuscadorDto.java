/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 *
 * @author Nico
 */
public class BuscadorDto implements Serializable, JSONStreamAware{
    private String nombre, tipo, descripcion, imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    @Override
    public void writeJSONString(Writer out) throws IOException {
       Map obj = new LinkedHashMap();
       obj.put("nombre", this.nombre);
       obj.put("descripcion", this.descripcion);
       obj.put("tipo", this.tipo);
       obj.put("imagen", this.imagen);
       JSONValue.writeJSONString(obj, out);        
    }
}
