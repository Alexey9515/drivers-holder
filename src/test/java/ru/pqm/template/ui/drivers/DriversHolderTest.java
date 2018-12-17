package ru.pqm.template.ui.drivers;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import ru.pqm.template.ui.drivers.factory.DefaultDriverFactory;

import static org.junit.Assert.*;

public class DriversHolderTest {

    private DriversHolder driversHolder;

    @Before
    public void init() {
        driversHolder = new DriversHolder("src/test/resources/drivers");
        driversHolder.setDriverFactory(new DefaultDriverFactory());
    }

    @Test
    public void openWebDriver() {
        String name = "test-chrome";
        driversHolder.openWebDriver(name, BrowserType.CHROME);
        driversHolder.changeWebDriver(name);
        assertNotNull(driversHolder.getWebDriver());
    }
}