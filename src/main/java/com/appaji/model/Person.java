package com.appaji.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Person_data") // @Table annotation is to have customized table name.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid") // this annotation is for customize name for column
	private Integer id;
	private String name;
	@Column(nullable = false)
	private Integer age;
	@OneToOne(cascade = CascadeType.ALL) // if cascade is mentioned all operations performed on parent table will
											// reflect on child therefore child object no need to persist
	private PanCard panCard;

}
