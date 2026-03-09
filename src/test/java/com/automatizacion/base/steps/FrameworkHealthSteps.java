package com.automatizacion.base.steps;

import com.automatizacion.base.config.FrameworkConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class FrameworkHealthSteps {

    private FrameworkConfig config;
    private String env;

    @Given("the framework loads the configuration")
    public void frameworkCargaConfiguracion() {
        FrameworkConfig.reset();
        config = FrameworkConfig.getInstance();
    }

    @When("I query the active environment")
    public void consultoEntornoActivo() {
        env = config.getActiveEnv();
    }

    @Then("I should get a valid environment")
    public void deberiaObtenerEntornoValido() {
        Assertions.assertNotNull(env);
        Assertions.assertFalse(env.isBlank());
    }
}

