package model;

public class ProfileRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String address;
    private String countryCode;
    private Integer provinceId;
    private Integer municipalityId;

    public ProfileRequest() {
    }

    public ProfileRequest(String firstName, String middleName, String lastName, String phone,
                          String address, String countryCode, Integer provinceId, Integer municipalityId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.countryCode = countryCode;
        this.provinceId = provinceId;
        this.municipalityId = municipalityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }
}