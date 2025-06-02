package com.appaji;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.appaji.model.PanCard;
import com.appaji.model.Person;

//HibernatePersistenceProvider -> for hibernate provider in persistence.xml
// Driver -> for current jdbc driver
// PostgreSQL82Dialect -> dialect for current database

public class OneToOneOperations {
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
		manager.close(); // Recommended to close manager before factory
		factory.close();
	}

	/**
	 * Main method to test basic CRUD operations. Demonstrates creation of Person,
	 * PanCard, and their one-to-one association.
	 */
	public static void main(String[] args) {
		startConnection();

		System.out.println("Create Person Data : " + createPerson("appaji", 25));

		System.out.println("Create Pan Card Data : " + createPanCard("abc"));

		Person person = new Person();
		PanCard card = new PanCard();
		person.setName("sharan");
		person.setAge(24);
		card.setNumber("xyz");
		System.out.println("Create Person and Pan Card Data together : " + createPersonAndPanCard(person, card));

		System.out.println("Find Person Data by ID : " + findPersonById(1));

		System.out.println("Find Pan Card Data by ID : " + findPancardById(1));

		System.out.println("Update Person Data by ID : " + updatePersonById(1, "appu", 40));

		System.out.println("Update Pan Card Data by ID : " + updatePanCardById(1, "cba"));

		System.out.println("Delete Person Data by ID : " + deletePersonById(1));

		System.out.println("Delete Pan Card Data by ID : " + deletePanCardById(1));

		System.out.println("Get all Person Data Details : " + getAllPersonDetails());

		endConnection();
	}

	/**
	 * Creates and persists a new Person entity in the database.
	 *
	 * @param name the name of the person
	 * @param age  the age of the person
	 * @return the persisted Person object
	 */
	public static Person createPerson(String name, int age) {
		Person person = new Person();
		person.setName(name);
		person.setAge(age);
		manager.persist(person);
		transaction.begin();
		transaction.commit();
		return person;
	}

	/**
	 * Creates and persists a new PanCard entity in the database.
	 *
	 * @param number the PAN card number
	 * @return the persisted PanCard object
	 */
	public static PanCard createPanCard(String number) {
		PanCard card = new PanCard();
		card.setNumber(number);
		transaction.begin();
		manager.persist(card);
		transaction.commit();
		return card;
	}

	/**
	 * Creates a Person and associated PanCard with a one-to-one mapping. Both
	 * entities are persisted in a single transaction.
	 *
	 * @param person the Person entity to persist
	 * @param card   the PanCard entity to associate and persist
	 * @return the persisted Person object with associated PanCard
	 */
	public static Person createPersonAndPanCard(Person person, PanCard card) {
		person.setPanCard(card);
		card.setPerson(person);
		transaction.begin();
		manager.persist(person);
		transaction.commit();
		return person;
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
