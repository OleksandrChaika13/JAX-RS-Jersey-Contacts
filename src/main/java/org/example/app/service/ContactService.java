package org.example.app.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contact;

import java.net.URI;
import java.util.*;

@Path("/api/v1.0/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactService {
    private static final List<Contact> CONTACTS;

    static {
        CONTACTS = new ArrayList<>();
        CONTACTS.add(new Contact(1L, "Fernando", "555 444-8574"));
        CONTACTS.add(new Contact(2L, "Oscar", "547 854-8441"));
        CONTACTS.add(new Contact(3L, "Lando", "777 777-3233"));
        CONTACTS.add(new Contact(4L, "Max", "444 523-4875"));
        CONTACTS.add(new Contact(5L, "Lewis", "789 111-1234"));
    }

    @GET
    public List<Contact> getContacts() {
        return CONTACTS;
    }

    @GET
    @Path("{id: [0-9]+}")
    public Contact getContact(@PathParam("id") Long id) {
        Contact contact = new Contact(id, null, null);

        int index = Collections.binarySearch(CONTACTS, contact, Comparator.comparing(Contact::getId));

        if (index >= 0)
            return CONTACTS.get(index);
        else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createContact(Contact contact) {
        if (Objects.isNull(contact.getId()))
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        int index = Collections.binarySearch(CONTACTS, contact, Comparator.comparing(Contact::getId));

        if (index < 0) {
            CONTACTS.add(contact);
            return Response
                    .status(Response.Status.CREATED)
                    .location(URI.create(String.format("/api/v1.0/contacts/%s", contact.getId())))
                    .build();
        } else
            throw new WebApplicationException(Response.Status.CONFLICT);
    }


    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        contact.setId(id);
        int index = Collections.binarySearch(CONTACTS, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            Contact updatedContact = CONTACTS.get(index);
            updatedContact.setPhone(contact.getPhone());
            CONTACTS.set(index, updatedContact);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteContact(@PathParam("id") Long id) {
        Contact contact = new Contact(id, null, null);
        int index = Collections.binarySearch(CONTACTS, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            CONTACTS.remove(index);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}