package com.cashix.playPackage;

public class model {
    int image , rate;
    String heading;
    String desc;

    public model(int image, int rate, String heading, String desc) {
        this.image = image;
        this.rate = rate;
        this.heading = heading;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
