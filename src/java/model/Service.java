/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dmx
 */
public class Service {

    private int serviceId;
    private String serviceName, description;
    //thêm code để phù hợp với màn hình service của HongNT
    private int doctorId;
    private int specId;
    public Service(int serviceId, String serviceName, int doctorId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.doctorId = doctorId;
    }

    public Service(int serviceId, String serviceName, String description, int specId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.specId = specId;
    }
 
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public int getSpecId() {
        return specId;
    }
    public void setSpecId(int specId) {
        this.specId = specId;
    }
    //
    public Service() {
    }

    public Service(int serviceId, String serviceName, String description) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}