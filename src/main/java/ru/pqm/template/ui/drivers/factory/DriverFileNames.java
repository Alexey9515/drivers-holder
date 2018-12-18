package ru.pqm.template.ui.drivers.factory;

public enum DriverFileNames {
    CHROME("chrome","chromedriver.exe"),
    IE("internet explorer","MicrosoftWebDriver.exe");
    private String browserType;
    private String fileName;

    DriverFileNames(String browserType, String fileName) {
        this.browserType = browserType;
        this.fileName = fileName;
    }

    public static String getFileName(String browserType){
        for (DriverFileNames driverFileNames : values()) {
            if (driverFileNames.browserType.equals(browserType)){
                return driverFileNames.fileName;
            }
        }
        return "";
    }
}
