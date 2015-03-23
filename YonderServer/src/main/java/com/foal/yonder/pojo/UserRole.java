package com.foal.yonder.pojo;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;

@Entity
@Table(name = "t_user_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRole implements Serializable{
	private static final long serialVersionUID = -232044930064003506L;
	private UserRolePK pk;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "serverUser", column = @Column(name = "user_id_")),
			@AttributeOverride(name = "role", column = @Column(name = "role_id_")) })
	public UserRolePK getPk() {
		return pk;
	}

	public void setPk(UserRolePK pk) {
		this.pk = pk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		UserRole other = (UserRole) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
}
