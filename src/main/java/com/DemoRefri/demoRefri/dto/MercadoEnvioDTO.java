package com.DemoRefri.demoRefri.dto;

import lombok.Data;

@Data
public class MercadoEnvioDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public StatusHistory getStatus_history() {
        return status_history;
    }

    public void setStatus_history(StatusHistory status_history) {
        this.status_history = status_history;
    }

    private String status;
    private String tracking_number;

    private StatusHistory status_history;

    @Data
    public static class StatusHistory {
        private String date_shipped;
        private String date_delivered;
    }
}