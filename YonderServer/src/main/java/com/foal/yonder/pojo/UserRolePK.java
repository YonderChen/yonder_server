package com.foal.yonder.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserRolePK implements Serializable{
	private static final long serialVersionUID = -8812557790194194642L;
	private ServerUser serverUser;
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_")
	public ServerUser getServerUser() {
		return serverUser;
	}

	public void setServerUser(ServerUser serverUser) {
		this.serverUser = serverUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id_")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((serverUser == null) ? 0 : serverUser.hashCode());
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
		UserRolePK other = (UserRolePK) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (serverUser == null) {
			if (other.serverUser != null)
				return false;
		} else if (!serverUser.equals(other.serverUser))
			return false;
		return true;
	}

}
