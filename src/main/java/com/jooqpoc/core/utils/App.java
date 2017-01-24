package main.java.com.jooqpoc.core.utils;

// For convenience, always static import your generated tables and jOOQ functions to decrease verbosity:
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	public static void main(String[] args) throws SQLException {

		final QueryBuilder jooqQueryBuilder = new QueryBuilder();

		// Connexion a la base
		jooqQueryBuilder.setUrlAndCredentials(
				"jdbc:mysql://localhost:3306/library", "root", "");

		// Choix du contexte :
		// 0- Expression regulieres (a customiser dans le QueryBuilder)
		// 1- MappedSchema (a customiser dans le QueryBuilder)
		// 2- Pas de schema (default)
		// 3- Avec fichier XML (schemas multiples, cf. ressources/jooq-runtime)
		jooqQueryBuilder.createContext(2);

		// Un simple Select initial
		LOGGER.info("==== INITIAL ====");
		jooqQueryBuilder.jooqSelect();

		// Une creation de table
		LOGGER.info("==== CREATE ====");
		jooqQueryBuilder.jooqCreate();
		jooqQueryBuilder.jooqSelect();

		// Insert d'un auteur avec seul un prenom
		LOGGER.info("==== INSERT ====");
		jooqQueryBuilder.jooqInsert("InsertedFirstName");
		jooqQueryBuilder.jooqSelect();

		// Update du prenom d'un auteur
		LOGGER.info("==== UPDATE ====");
		jooqQueryBuilder.jooqUpdate("UpdatedFirstName", 3);
		jooqQueryBuilder.jooqSelect();

		// Supression du dernier auteur cree lors de l'insert (ID a
		// incrementer)
		LOGGER.info("==== DELETE ====");
		jooqQueryBuilder.jooqDelete(25);
		jooqQueryBuilder.jooqSelect();

		jooqQueryBuilder.cleanup();
		LOGGER.info("==== CLEANED AND DONE ====");
	}

	public static Logger LOGGER = LoggerFactory.getLogger(App.class);

}