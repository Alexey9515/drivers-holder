package ru.pqm.template.ui.drivers;

import org.openqa.selenium.WebDriver;
import org.reflections.Reflections;
import ru.pqm.template.ui.drivers.event.InitializingDriverPostProcessor;
import ru.pqm.template.ui.drivers.exception.AutotestException;
import ru.pqm.template.ui.drivers.exception.DriverNotFoundException;
import ru.pqm.template.ui.drivers.factory.DriverFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class DriversHolder {

    private final Map<String, WebDriver> webDrivers;
    private final String driversPath;
    private final List<InitializingDriverPostProcessor> driversPostProcessors;

    private DriverFactory driverFactory;
    private WebDriver changedDriver;

    public DriversHolder(String driversPath) {
        this.driversPath = driversPath;
        webDrivers = new HashMap<>();
        driversPostProcessors = new ArrayList<>();
        Set<Class<? extends InitializingDriverPostProcessor>> subTypesOf = new Reflections("ru.pqm.template.ui").getSubTypesOf(InitializingDriverPostProcessor.class);
        for (Class<? extends InitializingDriverPostProcessor> aClass : subTypesOf) {
            try {
                driversPostProcessors.add(aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new AutotestException("Error to create InitializingDriverPostProcessor by class " + aClass.getName());
            }
        }
    }

    public WebDriver getWebDriver() {
        if (changedDriver == null) {
            throw new DriverNotFoundException("Not found changed driver. Please, activate driver by name");
        }
        return changedDriver;
    }

    public void changeWebDriver(String name) {
        if (!webDrivers.containsKey(name)) {
            throw new DriverNotFoundException("Not found driver by name " + name);
        }
        changedDriver = webDrivers.get(name);
    }

    public void openWebDriver(String name, String browserType) {
        AtomicReference<DriverSetting> driverSetting = new AtomicReference<>(new DriverSetting());
        driverSetting.get().setBrowserType(browserType);
        driverSetting.get().setDriversPath(driversPath);
        driversPostProcessors.forEach(initializingDriverPostProcessor -> driverSetting.set(initializingDriverPostProcessor.optionsInitDriver(driverSetting.get())));
        AtomicReference<WebDriver> webDriver = new AtomicReference<>(driverFactory.getWebDriver(driverSetting.get()));
        driversPostProcessors.forEach(initializingDriverPostProcessor -> webDriver.set(initializingDriverPostProcessor.afterInitDriver(driverSetting.get(), webDriver.get())));
        webDrivers.put(name, webDriver.get());
    }

    public DriverFactory getDriverFactory() {
        return driverFactory;
    }

    public void setDriverFactory(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public void close(String name) {
        if (webDrivers.containsKey(name)) {
            webDrivers.get(name).close();
        }
    }
}
