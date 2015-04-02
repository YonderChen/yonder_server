package com.foal.yonder.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DictionaryParamPK implements Serializable {
	private static final long serialVersionUID = -8048048698779863060L;
	private String index;
	private int module;

	@Column(name = "index_")
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + module;
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
		DictionaryParamPK other = (DictionaryParamPK) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (module != other.module)
			return false;
		return true;
	}

}
