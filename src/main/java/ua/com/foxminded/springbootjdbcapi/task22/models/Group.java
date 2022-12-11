package ua.com.foxminded.springbootjdbcapi.task22.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@OneToOne(targetEntity = Student.class, cascade = CascadeType.ALL)
	private Student student;

	public Group() {
	}

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

	public Student getStudent() {

		return student;

	}

	public void setStudent(Student student) {

		this.student = student;

	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", groupName=" + groupName + "]";
	}

}