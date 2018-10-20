package ml.arjunnair.excelapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionDetailed {

    @SerializedName("contributors")
    @Expose
    private List<Contributor> contributors = null;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("codename")
    @Expose
    private String codename;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("rules")
    @Expose
    private String rules;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("contactInfo")
    @Expose
    private List<ContactInfo> contactInfo = null;
    @SerializedName("prize")
    @Expose
    private String prize;
    @SerializedName("registration")
    @Expose
    private String registration;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("buttons")
    @Expose
    private List<ButtonJSON> buttons = null;
    @SerializedName("id")
    @Expose
    private Integer id;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
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

    public List<ButtonJSON> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonJSON> buttons) {
        this.buttons = buttons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
