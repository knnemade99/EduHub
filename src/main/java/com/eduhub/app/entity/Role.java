package com.eduhub.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="role")
public class Role {
	
	@Id
	@SequenceGenerator(name = "roleId", sequenceName = "role_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "roleId")
	@Column(name="roleId")
	private int roleId;
	
	public Role() {
		super();
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + "]";
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="roleName")
	private String roleName;

}
