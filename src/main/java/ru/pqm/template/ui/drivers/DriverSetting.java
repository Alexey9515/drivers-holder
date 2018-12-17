package ru.pqm.template.ui.drivers;

import org.openqa.selenium.MutableCapabilities;

public class DriverSetting {
    private String browserType;
    private MutableCapabilities mutableCapabilities;
    private String driversPath;

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public MutableCapabilities getMutableCapabilities() {
        return mutableCapabilities;
    }

    public void setMutableCapabilities(MutableCapabilities mutableCapabilities) {
        this.mutableCapabilities = mutableCapabilities;
    }

    public void setDriversPath(String driversPath) {
        this.driversPath = driversPath;
    }

    public String getDriversPath() {
        return driversPath;
    }
}
