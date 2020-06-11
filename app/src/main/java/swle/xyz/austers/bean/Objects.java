package swle.xyz.austers.bean;

import java.sql.Date;


public class Objects {
    private String kind;
    private String info;
    private Date date;
    private String imgAddress;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    @Override
    public String toString() {
        return "Objects{" +
                "kind='" + kind + '\'' +
                ", info='" + info + '\'' +
                ", date=" + date +
                ", imgAddress='" + imgAddress + '\'' +
                '}';
    }
}
