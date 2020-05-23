package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Base class for jrpc
 */
@JsonPropertyOrder({ "jsonrpc", "id"})
public abstract class JrpcBase {

    @JsonProperty("jsonrpc")
    protected String version = "2.0";
    protected Long id;

//    public JrpcBase() {
//        version = "2.0";
//    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "JrpcBase{" +
            "id=" + id +
            ", version='" + version + '\'' +
            '}';
    }
}


