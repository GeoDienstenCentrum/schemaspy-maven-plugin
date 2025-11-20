package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;

/**
 * SchemaSpyReport unit tests Test POM files is kept in test/resources/unit directory.
 *
 * @author john
 */
@MojoTest
class OracleExternalDatabaseTest {
  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/oracle-plugin-config.xml")
  void testOracleConfiguration(SchemaSpyReport mojo) throws Exception {
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/oracle-test/schemaspy/index.html");
    Logger.getLogger("global").info("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
}
