/*******************************************************************************
 * Copyright (c) 2013 Edward Wagner. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.careeropts.rurse.web.controller;


import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple JAX-RS controller
 */
@Path("/foo")
public class SimpleController {

    /**
     * Simply echos the value back
     *
     * @param value Value to be echoed.
     * @return The echo value.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/echo")
    public String postExample(@QueryParam("value") String value) {
        return value;
    }


    /**
     * Testing the file upload capability
     * @param uploadedInputStream
     * @param bodyPart
     * @return
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String> uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataBodyPart bodyPart) throws IOException {

        int size = 0;
        while (uploadedInputStream.read() != -1)
            size++;

        uploadedInputStream.close();

        Map<String,String> details = new HashMap<>();
        details.put("mediaType", bodyPart.getMediaType().toString());
        details.put("type", bodyPart.getContentDisposition().getType());
        details.put("filename", bodyPart.getContentDisposition().getFileName());
        details.put("size", "" + size);



        return details;

    }
}
