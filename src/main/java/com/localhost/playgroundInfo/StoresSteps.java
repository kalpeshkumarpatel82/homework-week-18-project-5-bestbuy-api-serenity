package com.localhost.playgroundInfo;
/* 
 Created by Kalpesh Patel
 */

import com.localhost.constants.EndPoints;
import com.localhost.models.StoresPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoresSteps {

    @Step("Creating Products with name : {0}, type: {1}, address: {2}, address2: {3}, city: {4}, state: {5}, zip: {6}, lat: {7}, lng: {8}, hours: {9}, servicesDate: {10}")
    public ValidatableResponse createStore(String name,
                                             String type,
                                             String address,
                                             String address2,
                                             String city,
                                             String state,
                                             String zip,
                                             int lat,
                                             int lng,
                                             String hours,
                                             HashMap<Object, Object> servicesData) {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);
        storesPojo.setServices(servicesData);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post(EndPoints.CREATE_STORES)
                .then();
    }

    @Step("Getting the Product information with ID: {0}")
    public HashMap<String, ?> getStoreInfoByName(int storeID) {
        HashMap<String, ?> storeMap = SerenityRest.given().log().all()
                .when()
                .pathParam("storeID", storeID)
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");
        return storeMap;
    }

    @Step("Creating Products with name : {0}, type: {1}, address: {2}, address2: {3}, city: {4}, state: {5}, zip: {6}, lat: {7}, lng: {8}, hours: {9}, servicesDate: {10}")
    public ValidatableResponse updateStore(int storeID,
                                           String name,
                                           String type,
                                           String address,
                                           String address2,
                                           String city,
                                           String state,
                                           String zip,
                                           int lat,
                                           int lng,
                                           String hours,
                                           HashMap<Object, Object> servicesData) {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);
        storesPojo.setServices(servicesData);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .pathParam("storeID",storeID)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_STORES_BY_ID)
                .then();
    }

    @Step("Deleting Store with ID {0}")
    public ValidatableResponse deleteStore(int storeID){
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_STORES_BY_ID)
                .then();
    }

    @Step("Getting Store with ID {0}")
    public ValidatableResponse getStoreByID(int storeID){
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeID)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }
}
