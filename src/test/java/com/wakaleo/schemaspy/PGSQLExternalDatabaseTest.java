package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;

@MojoTest
class PGSQLExternalDatabaseTest {
  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/pgsql-plugin-config.xml")
  void testPGSQLConfiguration(SchemaSpyReport mojo) throws Exception {
    mojo.executeReport(Locale.getDefault());
    // check if the reports generated
    File generatedFile = new File("./target/reports/pgsql-test/schemaspy/index.html");
    Logger.getLogger("global").info("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
}
