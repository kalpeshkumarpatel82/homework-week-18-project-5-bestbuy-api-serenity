package com.localhost.playgroundInfo;
/* 
 Created by Kalpesh Patel
 */

import com.localhost.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasKey;

@RunWith(SerenityRunner.class)
public class UtilitiesCRUDTest extends TestBase {
    @Steps
    UtilitiesSteps utilitiesSteps;

    @Title("Get the version of Application")
    @Test
    public void getVersion() {
        ValidatableResponse response = utilitiesSteps.gettingVersion();
        response.log().all().statusCode(200);
    }

    @Title("Get the Health check of Application")
    @Test
    public void getHealthCheck() {
        ValidatableResponse response = utilitiesSteps.gettingHealthCheck();
        response.log().all().statusCode(200);
        HashMap<String, ?> healthMap = response.extract().path("");
        Assert.assertThat(healthMap, hasKey("uptime"));
        Assert.assertThat(healthMap, hasKey("readonly"));
        Assert.assertThat(healthMap, hasKey("documents"));
    }
}
