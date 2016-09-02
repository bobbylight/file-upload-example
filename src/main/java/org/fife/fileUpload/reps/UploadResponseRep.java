package org.fife.fileUpload.reps;

/**
 * The response from a file upload POST.
 */
public class UploadResponseRep {

    private String desc;
    private String inputStreamType;
    private String requestType;

    public String getDesc() {
        return desc;
    }

    public String getInputStreamType() {
        return inputStreamType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setInputStreamType(String partInputStreamType) {
        this.inputStreamType = partInputStreamType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
