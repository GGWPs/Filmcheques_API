package nl.han.ise.Controller;

import nl.han.ise.Service.RapportageService;

import javax.inject.Inject;
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
    public Response getAllRapportage(){
        try {
            return Response.ok().entity(rapportageService.retrieveLijst()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/{rapportage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRapportage(@PathParam("rapportage") String rapportage) {
        try {
            return Response.ok().entity(rapportageService.getRapportage(rapportage)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRapportages(){
        try {
            return Response.status(Response.Status.OK).entity(rapportageService.updateRapportages()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }




}
