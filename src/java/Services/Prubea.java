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
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 *
 * @author Nico
 */
@Path("prueba")
public class Prubea {
    
    @POST
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pruba(UsuarioDto usuario)
    {
        return Response.ok(usuario,MediaType.APPLICATION_JSON).build();
    }
    
}
