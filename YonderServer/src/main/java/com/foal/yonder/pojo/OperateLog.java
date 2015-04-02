package com.foal.yonder.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_operate_log")
@Cache(region = "yonderHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 7380301920408544875L;
	private String logId;
	private Date operateTime;
	private ServerUser operator;
	private String operateContent;
	private int module;
	private String logIp;
	
	public class Module {
		public static final int GameConfigRefresh = 1030;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "log_id_")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "operate_time_")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_")
	public ServerUser getOperator() {
		return operator;
	}

	public void setOperator(ServerUser operator) {
		this.operator = operator;
	}

	@Column(name = "operate_content_")
	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	@Column(name = "log_ip_")
	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	
	@Column(name = "module_")
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((logIp == null) ? 0 : logIp.hashCode());
		result = prime * result + module;
		result = prime * result
				+ ((operateContent == null) ? 0 : operateContent.hashCode());
		result = prime * result
				+ ((operateTime == null) ? 0 : operateTime.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperateLog other = (OperateLog) obj;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (logIp == null) {
			if (other.logIp != null)
				return false;
		} else if (!logIp.equals(other.logIp))
			return false;
		if (module != other.module)
			return false;
		if (operateContent == null) {
			if (other.operateContent != null)
				return false;
		} else if (!operateContent.equals(other.operateContent))
			return false;
		if (operateTime == null) {
			if (other.operateTime != null)
				return false;
		} else if (!operateTime.equals(other.operateTime))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		return true;
	}
	
}
