package com.tt.ruokalista.dto;

public class Meta {

    private Long generated_timestamp;

    private Long requested_timestamp;

    private String ref_url;

    private String ref_title;

    public Meta() {
    }

    public Long getGenerated_timestamp() {
        return generated_timestamp;
    }

    public void setGenerated_timestamp(Long generated_timestamp) {
        this.generated_timestamp = generated_timestamp;
    }

    public Long getRequested_timestamp() {
        return requested_timestamp;
    }

    public void setRequested_timestamp(Long requested_timestamp) {
        this.requested_timestamp = requested_timestamp;
    }

    public String getRef_url() {
        return ref_url;
    }

    public void setRef_url(String ref_url) {
        this.ref_url = ref_url;
    }

    public String getRef_title() {
        return ref_title;
    }

    public void setRef_title(String ref_title) {
        this.ref_title = ref_title;
    }

}
