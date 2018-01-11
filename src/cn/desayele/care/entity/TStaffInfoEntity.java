package cn.desayele.care.entity;

import cn.desayele.care.util.Gettime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_staffinfo")
public class TStaffInfoEntity implements Serializable {
	@Id
	@Column(length=32)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String oid;
	@Column(length=20)
	private String gposition;
	@Column(length=20)
	private String gjobnum;
	@Column(length=20)
	private String gqq;
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

	public String getGposition() {
		return gposition;
	}

	public void setGposition(String gposition) {
		this.gposition = gposition;
	}

	public String getGjobnum() {
		return gjobnum;
	}

	public void setGjobnum(String gjobnum) {
		this.gjobnum = gjobnum;
	}

	public String getGqq() {
		return gqq;
	}

	public void setGqq(String gqq) {
		this.gqq = gqq;
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
