package ru.pqm.template.ui.drivers.factory;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import ru.pqm.template.ui.drivers.DriverSetting;
import ru.pqm.template.ui.drivers.exception.DriverInitializingException;

import java.nio.file.Paths;

public class DefaultDriverFactory implements DriverFactory {

    @Override
    public WebDriver getWebDriver(DriverSetting driverSetting) {
        switch (driverSetting.getBrowserType()) {
            case BrowserType.CHROME: {
                System.setProperty("webdriver.chrome.driver"
                        , Paths.get(System.getProperty("user.dir"))
                                .resolve(driverSetting.getDriversPath())
                                .resolve(DriverFileNames.getFileName(driverSetting.getBrowserType())).toString());
                MutableCapabilities mutableCapabilities = driverSetting.getMutableCapabilities();
                if (mutableCapabilities == null) {
                    return new ChromeDriver();
                }
                return new ChromeDriver((ChromeOptions) mutableCapabilities);
            }
            case BrowserType.FIREFOX: {
                return new FirefoxDriver((FirefoxOptions) driverSetting.getMutableCapabilities());
            }
            case BrowserType.SAFARI: {
                return new SafariDriver((SafariOptions) driverSetting.getMutableCapabilities());
            }
            case BrowserType.IE: {
                return new InternetExplorerDriver((InternetExplorerOptions) driverSetting.getMutableCapabilities());
            }
            default:
                throw new DriverInitializingException("Not support for type " + driverSetting.getBrowserType());
        }
    }
}