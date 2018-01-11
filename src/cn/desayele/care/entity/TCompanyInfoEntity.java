package cn.desayele.care.entity;

import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_companyinfo")
public class TCompanyInfoEntity implements Serializable {
	@Id
	@Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String oid;
	@Column(length=32)
	private String gcontact;
	@Column(length=32)
	private String gcity;
	@Column(length=50)
	private String gaddress;
	@Column(length=200)
	private String ginfo;
	@Column(length=50)
	private String gmould;
	@Column(length=32)
	private String cid;
	@Column(length=20)
	private String ts=Gettime.getTS();
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getGcontact() {
		return gcontact;
	}

	public void setGcontact(String gcontact) {
		this.gcontact = gcontact;
	}

	public String getGcity() {
		return gcity;
	}

	public void setGcity(String gcity) {
		this.gcity = gcity;
	}

	public String getGaddress() {
		return gaddress;
	}

	public void setGaddress(String gaddress) {
		this.gaddress = gaddress;
	}

	public String getGinfo() {
		return ginfo;
	}

	public void setGinfo(String ginfo) {
		this.ginfo = ginfo;
	}

	public String getGmould() {
		return gmould;
	}

	public void setGmould(String gmould) {
		this.gmould = gmould;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}
