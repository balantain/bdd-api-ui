package com.epam.api.managers;

import com.epam.api.enums.PetStatus;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.epam.api.enums.InfoType.PET;
import static com.epam.utils.PathBuilder.FIND_BY_STATUS;
import static com.epam.utils.PathBuilder.getPath;

public class RequestManager {
    private final RequestSpecification requestSpecification;

    public RequestManager(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getPetByStatus(PetStatus... statuses) {
        if (statuses.length>0){
            for (PetStatus status : statuses) {
                requestSpecification.param("status", status.getStatus());
            }
        }
        return requestSpecification.when().get(getPath(PET, FIND_BY_STATUS));
    }

    public Response getPetByStatus(String... statuses) {
        if (statuses.length>0){
            for (String status : statuses) {
                if (status!=null && !status.isEmpty()){
                    requestSpecification.param("status", status);
                }
            }
        }
        return requestSpecification.when().get(getPath(PET, FIND_BY_STATUS));
    }

    public Response getPetByStatusWithEndpoint(String endpoint, String... statuses) {
        if (statuses.length>0){
            for (String status : statuses) {
                if (status!=null && !status.isEmpty()){
                    requestSpecification.param("status", status);
                }
            }
        }
        return requestSpecification.when().get(endpoint);
    }

    public Response getPetByStatus() {
        return requestSpecification.when().get(getPath(PET, FIND_BY_STATUS));
    }

    public Response getPetByIdWithEndpoint(String endpoint, long id) {
        return requestSpecification.when().get("%s/%s".formatted(endpoint, id));
    }
}
