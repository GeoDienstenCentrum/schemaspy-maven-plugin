package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;

@MojoTest
class MSSQLExternalDatabaseTest {
  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/mssql-plugin-config.xml")
  void testMSSQLConfiguration(SchemaSpyReport mojo) throws Exception {
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/mssql-test/schemaspy/index.html");
    Logger.getLogger("global").info("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
}
