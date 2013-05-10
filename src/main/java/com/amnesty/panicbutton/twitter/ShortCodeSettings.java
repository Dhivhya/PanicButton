package com.amnesty.panicbutton.twitter;

public class ShortCodeSettings {
    private String country;
    private String serviceProvider;
    private String shortCode;

    public ShortCodeSettings(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
