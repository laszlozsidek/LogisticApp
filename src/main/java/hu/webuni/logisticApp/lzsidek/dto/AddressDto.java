package hu.webuni.logisticApp.lzsidek.dto;

import javax.validation.constraints.NotEmpty;

public class AddressDto {
    private Long id;
    @NotEmpty
    private String countryCode;
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String number;
    private Double longitude;
    private Double latitude;

    public AddressDto() {
    }

    public AddressDto(Long id, String countryCode, String city, String street, String postalCode, String number, Double longitude, Double latitude) {
        this.id = id;
        this.countryCode = countryCode;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.number = number;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
