package mec.mec.excel16.models;

import com.google.gson.annotations.SerializedName;

public class ButtonJSON {

    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}