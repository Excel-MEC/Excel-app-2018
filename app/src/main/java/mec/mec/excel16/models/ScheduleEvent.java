package mec.mec.excel16.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleEvent implements Comparable<ScheduleEvent>{

    @SerializedName("contributors")
    @Expose
    private List<Contributor> contributors = null;
    @SerializedName("createdBy")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("updatedBy")
    @Expose
    private UpdatedBy updatedBy;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start")
    @Expose
    private Double start;
    @SerializedName("end")
    @Expose
    private Double end;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public UpdatedBy getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UpdatedBy updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(ScheduleEvent scheduleEvent) {
        return getStart().compareTo(scheduleEvent.getStart());
    }


}