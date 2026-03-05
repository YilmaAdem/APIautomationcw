package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RetrieveResponseBody {
    private int id;
    private String name;
    private String email;

    @JsonProperty("isInstructor")
    private boolean instructor;

    private List<String> skills;
    private List<Course> courses;
    private SocialLinks socialLinks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public SocialLinks getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(SocialLinks socialLinks) {
        this.socialLinks = socialLinks;
    }
/*
    public String getPrice() {
        return courses.get(0).getPrice();
    }*/
}