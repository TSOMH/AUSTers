package swle.xyz.austers.bean;

public class Objects {
    private String currentHost; // 当前主人
    private String host; // 原主人
    private String kind;
    private String info;
    private String lostOrFoundDate;
    private String imgaddress;
    private int tag;//1为丢失，0为捡到

    public String getCurrentHost() {
        return currentHost;
    }

    public void setCurrentHost(String currentHost) {
        this.currentHost = currentHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

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

    public String getLostOrFoundDate() {
        return lostOrFoundDate;
    }

    public void setLostOrFoundDate(String lostOrFoundDate) {
        this.lostOrFoundDate = lostOrFoundDate;
    }

    public String getImgaddress() {
        return imgaddress;
    }

    public void setImgaddress(String imgaddress) {
        this.imgaddress = imgaddress;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

}
