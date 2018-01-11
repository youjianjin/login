package cn.desayele.care.entity;

import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_company")
public class TCompanyEntity implements Serializable {
	@Id
	@Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String oid;
	@Column(length=32)
	private String gcompany;
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

	public String getGcompany() {
		return gcompany;
	}

	public void setGcompany(String gcompany) {
		this.gcompany = gcompany;
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
