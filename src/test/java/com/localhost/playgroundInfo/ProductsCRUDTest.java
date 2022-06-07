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
public class ProductsCRUDTest extends TestBase {
    static String name = "Kalpesh" + TestUtils.getRandomValue();
    static String type = "giftcards" + TestUtils.getRandomValue();
    static Integer price = 6;
    static Integer shipping = 28;
    static String upc = "0123456789";
    static String description = "Available in 10, 20 & 30 USD";
    static String manufacturer = "Disney";
    static String model = "DIS_EU_10-20-30";
    static String url = "www.disney.com";
    static String image = "www.disney.com/images/diseu102030";
    static int productID;

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create a New product")
    @Test
    @Order(1)
    public void test001() {
        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");
        System.out.println(productID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    @Order(2)
    public void test002() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Update the Product information")
    @Test
    @Order(3)
    public void test003() {
        name = name + "_updated";
        productsSteps.updatingProduct(productID,name, type, price, shipping, upc, description, manufacturer, model, url, image);
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Delete the Product by ID")
    @Test
    @Order(4)
    public void test004() {
        productsSteps.deleteProduct(productID).statusCode(200);
        productsSteps.getProductByID(productID).statusCode(404);
    }

}
