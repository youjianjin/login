package cn.desayele.care.entity;

import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_staff")
public class TStaffEntity implements Serializable {
	@Id
	@Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String oid;
	@Column(length=32)
	private String gusername;
	@Column(length=11)
	private String gmobile;
	@Column(length=32)
	private String gemail;
	@Column(length=50)
	private String gpassword;
	@Column(length=32)
	private String cid;
	@Column(length=32)
	private String did;
	@Column(length=32)
	private String siid;
	@Column(length=2)
	private String glev1;
	@Column(length=2)
	private String glev2;
	@Column(length=2)
	private String glev3;
	@Column(length=20)
	private String ts=Gettime.getTS();

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getGusername() {
		return gusername;
	}

	public void setGusername(String gusername) {
		this.gusername = gusername;
	}

	public String getGmobile() {
		return gmobile;
	}

	public void setGmobile(String gmobile) {
		this.gmobile = gmobile;
	}

	public String getGemail() {
		return gemail;
	}

	public void setGemail(String gemail) {
		this.gemail = gemail;
	}

	public String getGpassword() {
		return gpassword;
	}

	public void setGpassword(String gpassword) {
		this.gpassword = gpassword;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getSiid() {
		return siid;
	}

	public void setSiid(String siid) {
		this.siid = siid;
	}

	public String getGlev1() {
		return glev1;
	}

	public void setGlev1(String glev1) {
		this.glev1 = glev1;
	}

	public String getGlev2() {
		return glev2;
	}

	public void setGlev2(String glev2) {
		this.glev2 = glev2;
	}

	public String getGlev3() {
		return glev3;
	}

	public void setGlev3(String glev3) {
		this.glev3 = glev3;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}
