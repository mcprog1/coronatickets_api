/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import DTOs.UsuarioDto;
import java.io.StringWriter;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import logica.Fabrica;
import logica.interfaz.IControladorBusqueda;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
/**
 *
 * @author Nico
 */
@Path("search")
public class Buscador {
    
        IControladorBusqueda ICB = Fabrica.getInstance().getIControladorBusqueda();
    
       @GET
       @Path("/buscar")
       public Response search(@QueryParam("plataformas") String plataformas, @QueryParam("categorias") String categorias, @QueryParam("busqueda") String busqueda)
       {
           try{
               
                String busquea = ICB.busqueda(plataformas, categorias, busqueda);
                
                
                return Response.ok("["+busquea+ "]",MediaType.APPLICATION_JSON).build();
           }catch(Exception e){
               return Response.ok(e.getMessage()).build();
           }
       }
    
}
