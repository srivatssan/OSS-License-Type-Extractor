package com.sri.licenseanalyzer.model;

public class DependencyLicenseInfo {
    private String name;
    private String version;
    private String license;
    private String licenseUrl;

    public DependencyLicenseInfo(String name, String version, String license, String licenseUrl) {
        this.name = name;
        this.version = version;
        this.license = license;
        this.licenseUrl = licenseUrl;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }
}
