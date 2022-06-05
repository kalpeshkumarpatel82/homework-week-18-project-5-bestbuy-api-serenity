package com.localhost.playgroundInfo;
/* 
 Created by Kalpesh Patel
 */

import com.localhost.constants.EndPoints;
import com.localhost.models.CategoriesPoJo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class CategoriesSteps {


    @Step("Creating Categories with name : {0}, ID: {1}")
    public ValidatableResponse createCategory(String name,
                                             String id) {
        CategoriesPoJo categoriesPoJo = new CategoriesPoJo();
        categoriesPoJo.setName(name);
        categoriesPoJo.setId(id);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPoJo)
                .when()
                .post(EndPoints.CREATE_CATEGORIES)
                .then();
    }

    @Step("Getting the Category information with ID: {0}")
    public HashMap<String,?> getCategoryInfoByName(String categoryID) {
        HashMap<String,?> categoryMap = SerenityRest.given().log().all()
                .when()
                .pathParam("categoryID",categoryID)
                .get(EndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");

        return categoryMap;
    }

    @Step("Updating Category with ID {0},name: {1}, ID: {2}")
    public ValidatableResponse updatingCategory(String categoryID,
                                               String name,
                                               String id){

        CategoriesPoJo categoriesPoJo = new CategoriesPoJo();
        categoriesPoJo.setName(name);
        categoriesPoJo.setId(id);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPoJo)
                .pathParam("categoryID",categoryID)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_CATEGORIES_BY_ID)
                .then();
    }

    @Step("Deleting Category with ID {0}")
    public ValidatableResponse deleteCategory(String categoryID){
        return SerenityRest.given().log().all()
                .pathParam("categoryID", categoryID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_CATEGORIES_BY_ID)
                .then();
    }

    @Step("Getting Category with ID {0}")
    public ValidatableResponse getCategoryByID(String categoryID){
        return SerenityRest.given().log().all()
                .pathParam("productID", categoryID)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then();
    }
}
