package com.localhost.playgroundInfo;
/* 
 Created by Kalpesh Patel
 */

import com.localhost.testbase.TestBase;
import com.localhost.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.annotation.Order;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCRUDTest extends TestBase {
    static String name = "Gift Cards" + TestUtils.getRandomValue();
    static String id = "abcat0010001"+ TestUtils.getRandomValue();
    static String categoryID;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a New Category")
    @Test
    @Order(1)
    public void test001() {
        ValidatableResponse response = categoriesSteps.createCategory(name, id);
        response.log().all().statusCode(201);
        categoryID = response.log().all().extract().path("id");
        System.out.println(categoryID);
    }

    @Title("Verify if the Category was added to the application")
    @Test
    @Order(2)
    public void test002() {
        HashMap<String, ?> categoryMap = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(categoryMap, hasValue(name));
        System.out.println(categoryMap);
    }

    @Title("Update the Category information")
    @Test
    @Order(3)
    public void test003() {
        name = name + "_updated";
        categoriesSteps.updatingCategory(categoryID,name, id);
        HashMap<String, ?> productList = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("Delete the Category by ID")
    @Test
    @Order(4)
    public void test004() {
        categoriesSteps.deleteCategory(categoryID).statusCode(200);
        categoriesSteps.getCategoryByID(categoryID).statusCode(404);
    }
}
