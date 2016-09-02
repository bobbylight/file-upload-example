package org.fife.fileUpload.rest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fife.fileUpload.reps.UploadResponseRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Collection;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(method = RequestMethod.POST, path = "/part",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UploadResponseRep getViaRequestPart(HttpServletRequest request) throws Exception {

        Collection<Part> parts = request.getParts();

        UploadResponseRep rep = new UploadResponseRep();
        rep.setRequestType(request.getClass().getName());

        Part part = parts.iterator().next();
        rep.setDesc("Part type == " + part.getClass().getName());
        try (InputStream in = part.getInputStream()) {
            rep.setInputStreamType(in.getClass().getName());
        }

        return rep;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/multipartFile",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UploadResponseRep getViaMultipartFile(@RequestParam MultipartFile file,
                                                 HttpServletRequest request) throws Exception {

        UploadResponseRep rep = new UploadResponseRep();
        rep.setRequestType(request.getClass().getName());

        try (InputStream in = file.getInputStream()) {
            rep.setInputStreamType(in.getClass().getName());
        }

        return rep;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/commonsFileUploadStreamingApi",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadResponseRep> getViaCommonsFileUploadStreamingApi(HttpServletRequest request) throws Exception {

        UploadResponseRep rep = new UploadResponseRep();
        rep.setRequestType(request.getClass().getName());

        ServletFileUpload upload = new ServletFileUpload();

        // NOTE: We're doing no real validation here, this is just for test purposes
        FileItemIterator iter = upload.getItemIterator(request);
        // TODO: This seems to always evaluate to true.  Is Spring parsing the HTTP request before we get here for some reason?
        if (!iter.hasNext()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FileItemStream item = iter.next();

        try (InputStream in = item.openStream()) {
            rep.setInputStreamType(in.getClass().getName());
        }

        return new ResponseEntity<>(rep, HttpStatus.OK);
    }

}