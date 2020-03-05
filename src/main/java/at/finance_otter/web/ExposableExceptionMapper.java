package at.finance_otter.web;

import at.finance_otter.service.ExposableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExposableExceptionMapper implements ExceptionMapper<ExposableException> {

    @Override
    public Response toResponse(ExposableException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

}
