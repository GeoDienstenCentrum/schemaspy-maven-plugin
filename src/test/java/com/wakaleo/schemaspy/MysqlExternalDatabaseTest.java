package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.apache.maven.plugin.testing.junit5.InjectMojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * SchemaSpyReport unit tests Test POM files is kept in test/resources/unit directory.
 *
 * @author john
 */
@MojoTest
public class MysqlExternalDatabaseTest {

  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/mysql-plugin-config.xml")
  public void testMySqlConfiguration(SchemaSpyReport mojo, TestInfo testInfo) throws Exception {
    Logger.getLogger("global").info("Starting: " + testInfo.getDisplayName());
    //        File projectCopy = this.resources.getBasedir("unit");
    //        File testPom = new File(projectCopy,"mysql-plugin-config.xml");
    //        assumeTrue(testPom.exists() && testPom.isFile(),
    //                "POM file should exist as file.");
    //
    //        SchemaSpyReport mojo = new SchemaSpyReport();
    //        mojo = (SchemaSpyReport) this.rule.configureMojo(mojo,
    // rule.extractPluginConfiguration("schemaspy-maven-plugin", testPom));
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/mysql-test/schemaspy/index.html");
    Logger.getLogger("global").info("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
}
