package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    
    @NotNull
    @NotEmpty(message="Required field")
    @Size(min= 3, max= 128, message="Title's length must be between 3 and 128 characters.")
    private String title;

    @NotNull
    @NotEmpty(message="Required field")
    @Email
    private String poster;

    @NotNull(message="Required field")
    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date postDate;

    @NotNull
    @NotEmpty(message="Required field")
    @Size(min=1, message="Must include at least one category")
    private List<String> categories;

    @NotNull
    @NotEmpty(message="Required field")
    private String text;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
   
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    @Override
    public String toString() {
        return "Notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }

    public long dateEpochmilliseconds() {
        return postDate != null ? postDate.getTime(): 0;
    }
    
    
}
