package com.appaji.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Integer;
	private String number;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "panCard") // mapped by used to avoid generation of foreign key
																// column in current Entity or table(PanCard)
//	@JoinColumn(name = "person")  // this annotation is for customize name for foreign key column
	@ToString.Exclude
	private Person person;
}
