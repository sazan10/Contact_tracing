package com.example.myapplication.classes;


public class ContactsListViewItem {
    private String hospitalStr ;
    private String provinceStr ;
    private String districtStr ;
    private String municipalityStr;
    private String contactStr;


    public String getHospitalStr() {
        return hospitalStr;
    }

    public void setHospitalStr(String hospitalStr) {
        this.hospitalStr = hospitalStr;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getDistrictStr() {
        return districtStr;
    }

    public void setDistrictStr(String districtStr) {
        this.districtStr = districtStr;
    }

    public String getMunicipalityStr() {
        return municipalityStr;
    }

    public void setMunicipalityStr(String municipalityStr) {
        this.municipalityStr = municipalityStr;
    }

    public String getContactStr() {
        return contactStr;
    }

    public void setContactStr(String contactStr) {
        this.contactStr = contactStr;
    }

    public void setVisibility(boolean visibility) {
    }
}