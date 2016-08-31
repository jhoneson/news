package com.example.scxh.news.pictureNews;

import java.util.List;

/**
 * Created by scxh on 2016/8/15.
 */
public class Lists {
    private String title;
    private String long_title;
    private String kpic;
    private String pic;
    private String link;
    private String intro;
    private List<Pics> pics;
    private String id;

    public List<Pics> getPics() {
        return pics;
    }

    public void setPics(List<Pics> pics) {
        this.pics = pics;
    }

    private String lead;
    private String content;

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getKpic() {
        return kpic;
    }

    public void setKpic(String kpic) {
        this.kpic = kpic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

}
