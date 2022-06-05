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

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCRUDTest extends TestBase {
    static String name = "Kalpesh" + TestUtils.getRandomValue();
    static String type = "giftcards" + TestUtils.getRandomValue();
    static String address = "11 New Road";
    static String address2 = "75001 India";
    static String city = "Mumbai";
    static String state = "Antwerp";
    static String zip = "75411";
    static int lat = 45;
    static int lng = 78;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9;Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create a New Store")
    @Test
    public void test001() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours,servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("Update the product information")
    @Test
    public void test003() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        storesSteps.updateStore(storeID,name, type, address, address2, city, state, zip, lat, lng, hours,servicesData);
        HashMap<String, ?> productList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("Delete the product by ID")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreByID(storeID).statusCode(404);
    }



}
