package com.appaji.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "college_data") // @Table annotation is to have customized table name.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class College {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is for get unique indentity auto-generated
														// primary value
	private Integer id;
	private String name;
	private String location;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Student> students;
}
