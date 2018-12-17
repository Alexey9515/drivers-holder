package ru.pqm.template.ui.drivers.event;

import org.openqa.selenium.WebDriver;
import ru.pqm.template.ui.drivers.DriverSetting;

public interface InitializingDriverPostProcessor {
    default DriverSetting optionsInitDriver(DriverSetting driverSetting) {
        return driverSetting;
    }

    default WebDriver afterInitDriver(DriverSetting driverSetting, WebDriver webDriver) {
        return webDriver;
    }
}
