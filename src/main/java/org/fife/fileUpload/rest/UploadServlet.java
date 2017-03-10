package org.fife.fileUpload.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fife.fileUpload.exceptions.FileUploadException;
import org.fife.fileUpload.reps.UploadResponseRep;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * An old-school servlet implementation, just for comparison.  Check out {@code UploadController} for a more modern
 * approach.<p>
 *
 * This servlet uses the commons-fileupload's streaming API to read the uploaded file right off of the request.  It
 * shows that this is possible in a servlet, even when spring.http.multipart.enabled=true, since, because we're not
 * in a controller, Spring doesn't parse the request itself.
 */
public class UploadServlet extends HttpServlet {

    /**
     * Just so we can return information about it.
     */
    private MultipartResolver multipartResolver;

    /**
     * Overridden with hideous hackery just so we can fetch our multipartResolver, so we can return info about it.
     *
     * @param config The servlet configuration.
     * @throws ServletException If something bad happens.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext)config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        try {
            multipartResolver = (MultipartResolver) ac.getBean("multipartResolver");
        } catch (NoSuchBeanDefinitionException e) {
            // NOSONAR - swallow, this just means we've removed the multipartResolver from our config
        }
    }

    /**
     * Creates and initializes basic properties of a response about an upload.
     *
     * @param request The request received.
     * @return A response rep, with some fields populated.
     */
    private UploadResponseRep createUploadResponseRep(HttpServletRequest request) {
        UploadResponseRep rep = new UploadResponseRep();
        rep.setRequestType(request.getClass().getName());
        rep.setMultipartResolverType(multipartResolver == null ? null : multipartResolver.getClass().getName());
        return rep;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UploadResponseRep rep = createUploadResponseRep(request);

        ServletFileUpload upload = new ServletFileUpload();

        try {

            // NOTE: We're doing no real validation here, this is just for test purposes
            FileItemIterator iter = upload.getItemIterator(request);
            // Note: This is identical to UploadController.getViaCommonsFileUploadStreamingApi().
            if (!iter.hasNext()) {
                throw new FileUploadException("FileItemIterator was empty");
            }
            FileItemStream item = iter.next();

            try (InputStream in = item.openStream()) {
                rep.setInputStreamType(in.getClass().getName());
            }

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter w = response.getWriter();
            w.println(new ObjectMapper().writeValueAsString(rep));

        } catch (org.apache.commons.fileupload.FileUploadException e) {
            throw new IOException(e);
        }
    }
}
