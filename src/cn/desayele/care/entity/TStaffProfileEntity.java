package cn.desayele.care.entity;


import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_staffprofile")
public class TStaffProfileEntity implements Serializable{
    @Id
    @Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String oid;
    @Column(length=50)
    private String sportrait;
    @Column(length=50)
    private String ssign;
    @Column(length=200)
    private String sgoodat;
    @Column(length=200)
    private String sexp;
    @Column(length=32)
    private String sid;
    @Column(length=20)
    private String ts= Gettime.getTS();

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getSportrait() {
        return sportrait;
    }

    public void setSportrait(String sportrait) {
        this.sportrait = sportrait;
    }

    public String getSsign() {
        return ssign;
    }

    public void setSsign(String ssign) {
        this.ssign = ssign;
    }

    public String getSgoodat() {
        return sgoodat;
    }

    public void setSgoodat(String sgoodat) {
        this.sgoodat = sgoodat;
    }

    public String getSexp() {
        return sexp;
    }

    public void setSexp(String sexp) {
        this.sexp = sexp;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
