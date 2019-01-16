package common;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Map;

public class RestEndPoint {

    private URI baseUrl;
    private Map<String, String> queryParamMap;
    private String restMethod = "GET";
    private String requestType = MediaType.APPLICATION_JSON;
    private String responseType = MediaType.APPLICATION_JSON;


    public URI getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URI baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, String> getQueryParamMap() {
        return queryParamMap;
    }

    public void setQueryParamMap(Map<String, String> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public String getRestMethod() {
        return restMethod;
    }

    public void setRestMethod(String restMethod) {
        this.restMethod = restMethod;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "common.RestEndPoint{" +
                "baseUrl=" + baseUrl +
                ", queryParamMap=" + queryParamMap +
                ", restMethod='" + restMethod + '\'' +
                ", requestType=" + requestType +
                ", responseType=" + responseType +
                '}';
    }
}
