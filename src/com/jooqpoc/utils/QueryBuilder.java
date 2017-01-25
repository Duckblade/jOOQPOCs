package com.jooqpoc.utils;

import static com.jooqpoc.model.Tables.CLIENT;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.jooq.Catalog;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Meta;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jooqpoc.model.tables.Client;

/**
 * Classe permettant de mettre a l'essai un CRUD utilisant jOOQ. Requetes en
 * direct et avec Metadonnees uniquement.
 */
public class QueryBuilder {

    /** Le Logger */
    public static Logger LOGGER = LoggerFactory.getLogger(QueryBuilder.class);

    /** Le nom d'utilisateur de la BDD */
    private String userName;

    /** Le mot de passe de la BDD */
    private String password;

    /** L'url de connexion JDBC a la BDD */
    private String url;

    /** Le contexte jOOQ cree avec la connexion a la base et les parametres choisis */
    private DSLContext dslContext;

   /** Le wrapper d'une table specifique (ici la table author) recuperee des metadonnees de la connexion */
    private Table<?> authorTable;

    /**
     * Reverse de toutes les operations effectuees pour retour a l'etat initial
     */
    public void cleanup() {
        dslContext.dropTable(CLIENT).execute();
        dslContext.update(authorTable).set((Field<String>) authorTable.field("first_name"), "Alice")
                .where(((Field<Integer>) authorTable.field("id")).equal(3)).execute();
        dslContext.close();

    }

    /**
     * Creation du DSLContext et affichage de la structure des tables grace aux metadonnees
     *
     * @param settingsID L'arrangement de parametres selectionne dans la liste des parametres
     * @throws SQLException erreur SQL
     */
    public void createContext(Integer settingsID) throws SQLException {
        final Connection conn = DriverManager.getConnection(url, userName, password);

        dslContext = DSL.using(conn, SQLDialect.MYSQL, createSettingsList().get(settingsID));

        final Meta metadata = dslContext.meta();
        final List<Catalog> catalogs = metadata.getCatalogs();

        final List<Table<?>> tables = metadata.getTables();

        // Recuperation de la table "Author"
        for (final Table<?> table : tables) {
            if (table.getName().equals("author")) {
                authorTable = table;
            }
        }

        // Affichage des champs de la table "Author"
        if (authorTable != null) {

            for (final Field<?> field : authorTable.fields()) {
                LOGGER.info(field.toString());
                LOGGER.info(field.getDataType().toString());
                LOGGER.info(Integer.toString(field.getDataType().length()));

            }
        }

        // metadata.getTables().stream().map(t -> t.getPrimaryKey())
        // .filter(Objects::nonNull).forEach(System.out::println);

        LOGGER.info("toto");

    }

    /**
     * Definition des parametres
     *
     * @return une liste de differents arrangements de parametres
     */
    private List<Settings> createSettingsList() {
        final List<Settings> settingsList = new ArrayList<>();

        // Avec expressions regulieres
        final Settings settings0 = new Settings().withRenderMapping(new RenderMapping().withSchemata(
                new MappedSchema().withInputExpression(Pattern.compile("DEV_(.*)")).withOutput("PROD_$1").withTables(
                        new MappedTable().withInputExpression(Pattern.compile("DEV_(.*)")).withOutput("PROD_$1"))));

        // Avec MappedSchema
        final Settings settings1 = new Settings().withRenderMapping(
                new RenderMapping().withSchemata(new MappedSchema().withInput("DEV").withOutput("PREPROD")));

        // Avec default schema
        final Settings settings2 = new Settings().withRenderSchema(false);

        // Avec un fichier de configuration XML
        final Settings settings3 = JAXB.unmarshal(new File("resources/jooq-runtime.xml"), Settings.class);

        settingsList.add(settings0);
        settingsList.add(settings1);
        settingsList.add(settings2);
        settingsList.add(settings3);
        return settingsList;

    }

    /**
     * Wrapper pour la creation
     */
    public void jooqCreate() {

        // Un exemple de table en local utilisee pour etre ajoutee a la DB
        final Table<?> clientTable = new Client();

        // Creation en base a partir d'une classe Java
        dslContext.createTable(clientTable).column(CLIENT.ID, SQLDataType.INTEGER)
                .column(CLIENT.FIRST_NAME, SQLDataType.VARCHAR.length(50))
                .column(CLIENT.LAST_NAME, SQLDataType.VARCHAR.length(50)).execute();
    }

    /**
     * Wrapper pour la suppression
     *
     * @param pTarget ID du record a supprimer
     */
    public void jooqDelete(Integer pTarget) {

        // La casse a une importance
        dslContext.deleteFrom(authorTable).where(((Field<Integer>) authorTable.field("id")).equal(pTarget)).execute();

    }

    /**
     * Wrapper de l'insertion
     *
     * @param pValue La valeur a inserer
     */
    public void jooqInsert(String pValue) {

        final Field<String> targetField;

        if (authorTable.field(1) != null) {
            targetField = (Field<String>) authorTable.field(1);
            dslContext.insertInto(authorTable, (Field<Integer>) authorTable.field(0), targetField).values(25, pValue)
                    .execute();
        }

        // Alternative
        // dslContext.insertInto(table(name("Author")))
        // .set(field(name("first_name")), 1).execute();

    }

    /**
     * Wrapper de Select Request
     */
    public void jooqSelect() {
        final Result<Record> result = dslContext.select().from(authorTable).fetch();
        LOGGER.info(result.toString());

    }

    /**
     * Wrapper pour l'update
     *
     * @param pValue  nouvelle valeur
     * @param pTarget ID du record a update
     */
    public void jooqUpdate(String pValue, Integer pTarget) {

        // La casse a une importance
        dslContext.update(authorTable).set((Field<String>) authorTable.field("first_name"), pValue)
                .where(((Field<Integer>) authorTable.field("id")).equal(pTarget)).execute();

    }

    /**
     * Definit l'url de connection a la base et les credentiels
     *
     * @param pUrl      url de ocnnexion a la bsase
     * @param pUserName l'username
     * @param pPassword le mot de passe
     */
    public void setUrlAndCredentials(String pUrl, String pUserName, String pPassword) {
        url = pUrl;
        userName = pUserName;
        password = pPassword;
    }

}
