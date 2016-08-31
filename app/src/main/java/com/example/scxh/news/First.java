package com.example.scxh.news;

import java.util.List;

public class First {
    private String title;
    private List<Imagextra> imgextra;
    private List<HeadNews> ads;
    private String imgsrc;
    private String ptime;
    private String url_3w;
    private String docid;
    private String digest;
    private String source;

    public List<Imagextra> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<Imagextra> imgextra) {
        this.imgextra = imgextra;
    }

    public List<HeadNews> getAds() {
        return ads;
    }

    public void setAds(List<HeadNews> ads) {
        this.ads = ads;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}
