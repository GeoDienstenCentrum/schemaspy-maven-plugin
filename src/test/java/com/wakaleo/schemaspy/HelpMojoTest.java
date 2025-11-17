/*
 * Copyright 2018 Mark Prins, GeoDienstenCentrum.
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.apache.maven.api.plugin.testing.MojoTest;
import org.apache.maven.api.plugin.testing.InjectMojo;
import org.junit.jupiter.api.Test;
import org.apache.maven.plugin.logging.Log;
/**
 * Testcase for {@link com.wakaleo.schemaspy.HelpMojo } (which is a generated class).
 *
 * @author mprins
 */
@MojoTest
public class HelpMojoTest {

  /**
   * Test method for {@link com.wakaleo.schemaspy.HelpMojo#execute() } , it tests execution.
   *
   * @throws Exception if any
   */
  @Test
  @InjectMojo(goal = "help", pom = "classpath:/unit/mssql-plugin-config.xml")
  public void testExecute(SchemaSpyReport myMojo) throws Exception {

    Log log = mock(Log.class);

    myMojo.setLog(log);

    //        final File projectCopy = this.resources.getBasedir("unit");
    //        final File pom = new File(projectCopy, "mssql-plugin-config.xml");
    //        assumeTrue(pom.exists() && pom.isFile(), "POM file should exist as file.");
    //
    //        final HelpMojo myMojo = (HelpMojo) this.rule.lookupEmptyMojo("help", pom);
    assertNotNull(myMojo, "The 'help' mojo should exist");

    // should execute and not error
    myMojo.execute();
  }
}
