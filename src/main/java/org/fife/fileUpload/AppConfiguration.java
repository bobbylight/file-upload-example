package org.fife.fileUpload;

import org.fife.fileUpload.rest.UploadServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AppConfiguration {

//    /**
//     * Needed for commons-fileupload support if you need to meet the following criteria:
//     * <ul>
//     *     <li>You want to use commons-fileupload's standard, non-streaming API (e.g. just grab a {@code MultipartFile} in a controller)</li>
//     * </ul>
//     * If you're looking to use commons-fileupload's streaming API, you'll need to remove this method in addition to
//     * turning off Spring's multipart support ({@code multipart.enabled=false}).
//     */
//    @Bean
//    public MultipartResolver multipartResolver() {
//        return new CommonsMultipartResolver();
//    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new UploadServlet(), "/upload/rawServlet");
    }
}
