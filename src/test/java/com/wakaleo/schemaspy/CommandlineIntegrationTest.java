/*
 * Copyright 2025 Mark Prins, GeoDienstenCentrum.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/** Testcases for the {@code default-cli} commandline. */
public class CommandlineIntegrationTest {
  private static final String pomFile = "src/test/projects/unit/pgsql-plugin-config-cli.xml";
  private String version;

  @BeforeEach
  public void setUp() {
    version = System.getenv("PLUGIN_VERSION");
  }

  @Test
  public void testCommandlineHelp(TestInfo testInfo) throws Exception {
    Process mvn =
        new ProcessBuilder(
                "mvn",
                "schemaspy:help",
                // "-X",
                "-U",
                "-V",
                "-f",
                pomFile,
                "-Dplugin.version=" + version)
            .redirectErrorStream(true)
            .start();

    BufferedReader stdOutReader = new BufferedReader(new InputStreamReader(mvn.getInputStream()));
    String line;
    while ((line = stdOutReader.readLine()) != null) {
      System.out.println(testInfo.getDisplayName() + "--" + line);
    }
    assertEquals(0, mvn.waitFor(), "Maven default-cli 'schemaspy:help' command failed");
  }

  @Test
  public void testCommandlineSchemaspy(TestInfo testInfo) throws Exception {
    // /target/reports/pgsql-cli-test/schemaspy/index.html
    final String generatedFile = "./target/reports/pgsql-cli-test/schemaspy/index.html";
    Process mvn =
        new ProcessBuilder(
                "mvn",
                "schemaspy:schemaspy",
                // "-X",
                "-U",
                "-V",
                "-f",
                pomFile,
                // "-DoutputDirectory=./../../../../target/reports/pgsql-cli-test"
                "-Dplugin.version=" + version)
            .redirectErrorStream(true)
            .start();

    BufferedReader stdOutReader = new BufferedReader(new InputStreamReader(mvn.getInputStream()));
    String line;
    while ((line = stdOutReader.readLine()) != null) {
      System.out.println(testInfo.getDisplayName() + "--" + line);
    }

    assertEquals(0, mvn.waitFor(), "Maven default-cli 'schemaspy:schemaspy' command failed");
    assertTrue(Paths.get(generatedFile).toFile().exists());
  }
}
