/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import java.io.Serializable;

/**
 *
 * @author Nico
 */
public class EspectaculoDto implements Serializable{
    private String nombre, descripcion, artista, duracion, espectadoresMinimo, espectadoresMaximo, URL, costo, plataforma, categorias, imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEspectadoresMinimo() {
        return espectadoresMinimo;
    }

    public void setEspectadoresMinimo(String espectadoresMinimo) {
        this.espectadoresMinimo = espectadoresMinimo;
    }

    public String getEspectadoresMaximo() {
        return espectadoresMaximo;
    }

    public void setEspectadoresMaximo(String espectadoresMaximo) {
        this.espectadoresMaximo = espectadoresMaximo;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
