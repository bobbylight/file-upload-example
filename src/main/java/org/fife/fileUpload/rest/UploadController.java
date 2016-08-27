package org.fife.fileUpload.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(method = RequestMethod.GET,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.ALL_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String get(HttpServletRequest request) {
        return "Hello world I am Robert Futrell: " + request.getClass().getName();
    }

}