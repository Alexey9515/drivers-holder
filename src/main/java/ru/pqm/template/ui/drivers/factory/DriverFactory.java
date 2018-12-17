package ru.pqm.template.ui.drivers.factory;

import org.openqa.selenium.WebDriver;
import ru.pqm.template.ui.drivers.DriverSetting;

public interface DriverFactory {
    WebDriver getWebDriver(DriverSetting driverSetting);
}
