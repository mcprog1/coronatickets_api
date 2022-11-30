/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import DTOs.*;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import logica.Fabrica;
import logica.interfaz.IControladorBusqueda;
import logica.interfaz.IControladorPaquetes;
import org.json.simple.JSONObject;
import Clases.*;
import java.io.*;
import org.json.simple.JSONArray;
/**
 *
 * @author Nico
 */
@Path("paquetes")
public class Paquetes {
    
    IControladorPaquetes ICP = Fabrica.getInstance().getIControladorPaquete();
    
    @POST
    @Path("/crear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(@Valid PaqueteDto paquete)
    {
        try{
            JSONObject json = new JSONObject();
            int continuo = 1;
            if(paquete.getNombre().isEmpty())
            {
                json.put("msg", "El nombre es obligatorio.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1 && paquete.getDescripcion().isEmpty())
            {
                json.put("msg", "La descripcion es obligatoria.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1 && paquete.getDescuento().isEmpty())
            {
                json.put("msg", "El descuento es obligatorio.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1 && Float.valueOf(paquete.getDescuento()) <= 0)
            {
                json.put("msg", "El descuento tiene que ser mayor que 0.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1 && paquete.getFechaFin().isEmpty())
            {
                json.put("msg", "La fecha fin es obligatoria.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1 && paquete.getFechaInicio().isEmpty())
            {
                json.put("msg", "La fecha de inicio es obligatoria.");
                json.put("code","1000");
                continuo = 0;
            }
            
            if(continuo == 1)
            {
                ICP.AltaPaquete(paquete.getNombre(), paquete.getDescripcion(), paquete.getFechaInicio(), paquete.getFechaFin(), paquete.getDescuento(), paquete.getImagen());
                json.put("msg", "Proceso exitoso.");
                json.put("data", "Se creo correctamente.");
                json.put("code","0");
            }
            
            return Response.ok(json.toJSONString(), MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/obtener")
    public Response obtenerPaquetes()
    {
        try{
            List<Clases.Paquetes> paquetes = (List<Clases.Paquetes>) ICP.obtenerPaquetes();
            JSONObject json = new JSONObject();
            JSONArray pq = new JSONArray();
            pq.add(paquetes);
             
            StringWriter out = new StringWriter();
            pq.writeJSONString(out); 
            json.put("paquetes", out);
            
            return Response.ok(json.toJSONString(), MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/obtener/espectaculo")
    public Response obtenerPaquetesEspectaculo(@QueryParam("paquete") String paquete)
    {
        try{
            List<Clases.Espectaculo> listado = (List<Clases.Espectaculo>) ICP.obtenerEspectaculosPaquetes(paquete);
            JSONObject json = new JSONObject();
            JSONArray es = new JSONArray();
            es.add(listado);
             
            StringWriter out = new StringWriter();
            es.writeJSONString(out); 
            json.put("espectaculos", out);
            
            return Response.ok(json.toJSONString(), MediaType.APPLICATION_JSON).build();
        }catch(Exception e)
        {
            return Response.ok(e.getMessage()).build();
        }
    }
    
}
