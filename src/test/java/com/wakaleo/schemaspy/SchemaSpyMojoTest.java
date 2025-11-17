package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.wakaleo.schemaspy.util.DatabaseHelper;
import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * SchemaSpyReport unit tests Test POM files is kept in test/resources/unit directory.
 *
 * @author john
 */
@MojoTest
class SchemaSpyMojoTest {
  @BeforeEach
  void setUp(TestInfo testInfo) throws Exception {
    DatabaseHelper.setupDatabase("src/test/resources/sql/testdb.sql");
    Logger.getLogger("global").info("Starting :" + testInfo.getDisplayName());
  }

  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/test-plugin-config.xml")
  void testCustomConfiguration(SchemaSpyReport mojo) throws Exception {
    Log log = mock(Log.class);
    mojo.setLog(log);
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/test/schemaspy/index.html");
    System.out.println("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }

  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/full-test-plugin-config.xml")
  void testFullConfiguration(SchemaSpyReport mojo) throws Exception {
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/full-test/schemaspy/index.html");
    System.out.println("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
}
