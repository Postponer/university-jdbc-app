package ua.com.foxminded.springbootjdbcapi.task22.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;
	
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	public Group() {}
	
	public Group(int groupId, String groupName) {

		this.groupId = groupId;
		this.groupName = groupName;

	}

	public int getGroupId() {

		return groupId;

	}

	public void setGroupId(int groupId) {

		this.groupId = groupId;

	}

	public String getGroupName() {

		return groupName;

	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;

	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", groupName=" + groupName + "]";
	}

}