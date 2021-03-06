/**
 * Copyright (c) 2009-2013, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name "jOOQ" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jooq.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;

import org.junit.Before;
import org.junit.Test;

/**
 * Some common tests related to {@link Settings}
 *
 * @author Lukas Eder
 */
public class SettingsTest {

    private Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = SettingsTools.defaultSettings();
    }

    @Test
    public void testDefaultSettings() {
        Settings settings2 = SettingsTools.defaultSettings();
        settings.setAttachRecords(false);

        // Check that the above change to the default settings has no effect
        // on the clone
        assertTrue(settings2.isAttachRecords());
        assertTrue(SettingsTools.defaultSettings().isAttachRecords());
        assertFalse(settings.isAttachRecords());
    }

    @Test
    public void testCloneable() {
        Settings settings1 = new Settings();
        Settings settings2 = SettingsTools.clone(settings1);

        assertEquals(settings1.isAttachRecords(), settings2.isAttachRecords());
        assertEquals(settings1.getExecuteListeners(), settings2.getExecuteListeners());

        // Check if clone makes a deep-copy
        settings1.getExecuteListeners().add("asdf");
        assertEquals(settings1.getExecuteListeners().size(), settings2.getExecuteListeners().size() + 1);
    }
}
