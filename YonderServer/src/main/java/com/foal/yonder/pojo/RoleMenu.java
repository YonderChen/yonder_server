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
@Table(name = "t_role_menu")
@Cache(region = "yonderHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenu implements Serializable{
	private static final long serialVersionUID = -3262032370296478702L;
	private RoleMenuPK pk;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "role", column = @Column(name = "role_id_")),
			@AttributeOverride(name = "menu", column = @Column(name = "menu_id_")) })
	public RoleMenuPK getPk() {
		return pk;
	}

	public void setPk(RoleMenuPK pk) {
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
		RoleMenu other = (RoleMenu) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
}
