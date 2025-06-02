package com.appaji;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.appaji.model.College;
import com.appaji.model.PanCard;
import com.appaji.model.Person;
import com.appaji.model.Student;

public class OneToManyOperations {
	private static EntityManagerFactory factory;
	private static EntityManager manager;
	private static EntityTransaction transaction;

	/**
	 * Initializes the EntityManager and EntityTransaction for database operations.
	 * Must be called before performing any persistence operation.
	 */
	public static void startConnection() {
		factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
	}

	/**
	 * Closes the EntityManager and EntityManagerFactory to release resources. Must
	 * be called after all persistence operations are done.
	 */
	public static void endConnection() {
		manager.close();
		factory.close();
	}

	/**
	 * Main method to test basic CRUD operations. Demonstrates creation of College,
	 * Student, and their one-to-many association.
	 */
	public static void main(String[] args) {
		startConnection();
		endConnection();
	}

	public static College createCollege(String name, String loc) {
		College college = new College();
		college.setName(name);
		college.setLocation(loc);
		transaction.begin();
		manager.persist(college);
		transaction.commit();
		return college;
	}

	public static Student createStudent(String name, String address) {
		Student student = new Student();
		student.setName(name);
		student.setAddress(address);
		transaction.begin();
		manager.persist(student);
		transaction.commit();
		return student;
	}

	public static College createCollegeAndStudent(College college, List<Student> students) {
		college.setStudents(students);
		students.forEach(student -> student.setCollege(college));
		transaction.begin();
		manager.persist(college);
		transaction.commit();
		return college;
	}

	/**
	 * Retrieves a Person entity by its ID.
	 *
	 * @param id the primary key of the Person
	 * @return the found Person entity, or null if not found
	 */
	public static Person findPersonById(int id) {
		return manager.find(Person.class, id);
	}

	/**
	 * Retrieves a PanCard entity by its ID.
	 *
	 * @param id the primary key of the PanCard
	 * @return the found PanCard entity, or null if not found
	 */
	public static PanCard findPancardById(int id) {
		return manager.find(PanCard.class, id);
	}

	/**
	 * Updates the name and age of a Person entity identified by its ID.
	 *
	 * @param id   the ID of the person to update
	 * @param name the new name
	 * @param age  the new age
	 * @return the updated Person entity, or null if not found
	 */
	public static Person updatePersonById(int id, String name, int age) {
		Person person = manager.find(Person.class, id);
		if (person != null) {
			transaction.begin();
			person.setName(name);
			person.setAge(age);
//			manager.merge(person); // not mandatory
			transaction.commit();
		}
		return person;
	}

	/**
	 * Updates the number of a PanCard entity identified by its ID.
	 *
	 * @param id     the ID of the PanCard to update
	 * @param number the new PAN card number
	 * @return the updated PanCard entity, or null if not found
	 */
	public static PanCard updatePanCardById(int id, String number) {
		PanCard card = manager.find(PanCard.class, id);
		if (card != null) {
			transaction.begin();
			card.setNumber(number);
//			manager.merge(card); // not mandatory due to JPA dirty checking after every transaction.commit();
			transaction.commit();
		}
		return card;
	}

	/**
	 * Deletes a Person entity from the database using its ID.
	 *
	 * @param id the ID of the Person to delete
	 * @return true if the entity was deleted, false if not found
	 */
	public static boolean deletePersonById(int id) {
		Person person = manager.find(Person.class, id);
		if (person != null) {
			transaction.begin();
			manager.remove(person);
			transaction.commit();
			return true;
		}
		return false;
	}

	/**
	 * Deletes a PanCard entity from the database using its ID.
	 *
	 * @param id the ID of the PanCard to delete
	 * @return true if the entity was deleted, false if not found
	 */
	public static boolean deletePanCardById(int id) {
		PanCard card = manager.find(PanCard.class, id);
		if (card != null) {
			transaction.begin();
			manager.remove(card);
			transaction.commit();
			return true;
		}
		return false;
	}

	/**
	 * Retrieves all Person entities from the database.
	 *
	 * @return a list of all Person entities
	 */
	public static List<Person> getAllPersonDetails() {
		return manager.createQuery("SELECT u FROM Person u", Person.class).getResultList();
	}
}
