package mec.mec.excel16.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Competition {

    @SerializedName("contributors")
    private List<Contributor> contributors = null;
    @SerializedName("active")
    private Boolean active;
    @SerializedName("name")
    private String name;
    @SerializedName("codename")
    private String codename;
    @SerializedName("contactInfo")
    private List<ContactInfo> contactInfo = null;
    @SerializedName("registration")
    private String registration;
    @SerializedName("department")
    private String department;
    @SerializedName("category")
    private String category;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("venue")
    private String venue;
    @SerializedName("img")
    private String img;
    @SerializedName("color")
    private String color;
    @SerializedName("id")
    private Integer id;
    @SerializedName("buttons")
    private List<ButtonJSON> buttons = null;

    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ButtonJSON> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonJSON> buttonJSONS) {
        this.buttons = buttonJSONS;
    }

}