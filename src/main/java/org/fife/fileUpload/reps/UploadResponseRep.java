package org.fife.fileUpload.reps;

/**
 * The response from a file upload POST.
 */
public class UploadResponseRep {

    private String desc;
    private String inputStreamType;
    private String multipartResolverType;
    private String requestType;
    private String fileSizeThreshold;

    public String getDesc() {
        return desc;
    }

    public String getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public String getInputStreamType() {
        return inputStreamType;
    }

    public String getMultipartResolverType() {
        return multipartResolverType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFileSizeThreshold(String fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public void setInputStreamType(String partInputStreamType) {
        this.inputStreamType = partInputStreamType;
    }

    public void setMultipartResolverType(String multipartResolverType) {
        this.multipartResolverType = multipartResolverType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
