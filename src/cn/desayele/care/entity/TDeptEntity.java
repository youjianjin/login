package cn.desayele.care.entity;

import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_dept")
public class TDeptEntity implements Serializable {
	@Id
	@Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String oid;
	@Column(length=20)
	private String gdept;
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

	public String getGdept() {
		return gdept;
	}

	public void setGdept(String gdept) {
		this.gdept = gdept;
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
