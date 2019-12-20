package nl.han.ise.Controller;

import nl.han.ise.DAO.RapportageDAO;
import nl.han.ise.Service.RapportageService;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/rapportage")
public class RapportageController {


    @Inject
    private RapportageService rapportageService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRapportage(){
        try {
            return Response.ok().entity(rapportageService.rapportage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


}
