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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;

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
    @Order(1)
    public void test001() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("Verify if the Store was added to the application")
    @Test
    @Order(2)
    public void test002() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("Update the Store information")
    @Test
    @Order(3)
    public void test003() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        storesSteps.updateStore(storeID, name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        HashMap<String, ?> productList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("Delete the Store by ID")
    @Test
    @Order(4)
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreByID(storeID).statusCode(404);
    }

    @Title("1. Verify the if the total is equal to 1563")
    @Test
    @Order(5)
    public void test005() {
        ValidatableResponse response = storesSteps.getAllStores();
        int total = response.extract().path("total");
        Assert.assertEquals(1563, total);
    }

    @Title("2. Verify the if the stores of limit is equal to 10")
    @Test
    @Order(6)
    public void test006() {
        ValidatableResponse response = storesSteps.getAllStores();
        int limit = response.extract().path("limit");
        Assert.assertEquals(10, limit);
    }

    @Title("3. Check the single ‘Name’ in the Array list (Inver Grove Heights)")
    @Test
    @Order(7)
    public void test007() {
        ValidatableResponse response = storesSteps.getAllStores();
        List<String> name = response.extract().path("data.name");
        Assert.assertThat(name, hasItem("Inver Grove Heights"));
    }


    @Title("4. Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)")
    @Test
    @Order(8)
    public void test008() {
        ValidatableResponse response = storesSteps.getAllStores();
        List<String> names = response.extract().path("data.name");
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Roseville");
        expectedNames.add("Burnsville");
        expectedNames.add("Maplewood");
        for (String data : expectedNames) {
            Assert.assertThat(names, hasItem(data));
        }
    }

    @Title("5. Verify the storied=7 inside storeservices of the third store of second services")
    @Test
    @Order(9)
    public void test009() {
        ValidatableResponse response = storesSteps.getAllStores();
        int storeID = response.extract().path("data[2].services[1].storeservices.storeId");
        Assert.assertEquals(7, storeID);
    }

    @Title("6. Check hash map values ‘createdAt’ inside storeservices map where store name = Roseville")
    @Test
    @Order(10)
    public void test010() {
        ValidatableResponse response = storesSteps.getAllStores();
        List<String> createdAt = response.extract().path("data.findAll{it.name=='Roseville'}.services[0].storeservices.createdAt");
        for (String data : createdAt) {
            Assert.assertEquals("2016-11-17T17:57:09.417Z", data);
        }
    }

    @Title("7. Verify the state = MN of forth store")
    @Test
    @Order(11)
    public void test011() {
        ValidatableResponse response = storesSteps.getAllStores();
        String stateName = response.extract().path("data[3].state");
        Assert.assertEquals("MN", stateName);
    }

    @Title("8. Verify the store name = Rochester of 9th stor")
    @Test
    @Order(12)
    public void test012() {
        ValidatableResponse response = storesSteps.getAllStores();
        String storeName = response.extract().path("data[8].name");
        Assert.assertEquals("Rochester", storeName);
    }

    @Title("9. Verify the storeId = 11 for the 6th store")
    @Test
    @Order(13)
    public void test013() {
        ValidatableResponse response = storesSteps.getAllStores();
        List<Integer> storeIDSixthStore = response.extract().path("data[5].services.storeservices.storeId");
        for (int data : storeIDSixthStore) {
            Assert.assertEquals(11, data);
        }
    }

    @Title("10. Verify the serviceId = 4 for the 7th store of forth service")
    @Test
    @Order(14)
    public void test014() {
        ValidatableResponse response = storesSteps.getAllStores();
        int serviceID = response.extract().path("data[6].services[3].storeservices.serviceId");
        Assert.assertEquals(4, serviceID);
    }
}
