package com.liangshou.llmsrefactor.metrics.pmd.demos;

import net.sourceforge.pmd.util.database.DBURI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * @author X-L-S
 */
@Deprecated
public class PMDMySQLDBURICreatorDemo {
    public static void main(String[] args) {
        // Local MySQL connection details
        String username = "root";
        String password = "root";
        String host = "localhost";
        int port = 3306;
        String databaseName = "llm_refactor";

        // JDBC URL for MySQL
        String jdbcUrl = String.format("jdbc:mysql:%s:%d/%s",
                host, port, databaseName);
        String query = "characterset=utf8";

        try {
            // Create a URI instance using the JDBC URL and query
            URI uri = new URI(jdbcUrl);

            // Create a DBURI object
            DBURI dbURI = new DBURI(uri);

            // Set optional parameters if needed (e.g., specifying desired PMD languages, schemas, etc.)
            List<String> desiredPMDLanguages = Arrays.asList("java", "sql");
            dbURI.setLanguagesList(desiredPMDLanguages);

            System.out.println("Created DBURI: " + dbURI.toString());
        } catch (URISyntaxException | IOException e) {
            System.err.println("Error creating DBURI: " + e.getMessage());
        }
    }
}
