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
package org.jooq.tools;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.jooq.impl.DefaultExecuteListener;

/**
 * A default {@link ExecuteListener} that just logs events to java.util.logging,
 * log4j, or slf4j using the {@link JooqLogger}
 *
 * @author Lukas Eder
 */
public class LoggerListener extends DefaultExecuteListener {

    private static final JooqLogger log   = JooqLogger.getLogger(LoggerListener.class);

    @Override
    public void renderEnd(ExecuteContext ctx) {
        if (log.isDebugEnabled()) {
            if (ctx.query() != null) {

                // Actual SQL passed to JDBC
                log.debug("Executing query", ctx.sql());

                // [#1278] DEBUG log also SQL with inlined bind values, if
                // that is not the same as the actual SQL passed to JDBC
                String inlined = ctx.query().getSQL(true);
                if (!ctx.sql().equals(inlined)) {
                    log.debug("-> with bind values", inlined);
                }
            }
            else if (!StringUtils.isBlank(ctx.sql())) {

                // [#1529] Batch queries should be logged specially
                if (ctx.type() == ExecuteType.BATCH) {
                    log.debug("Executing batch query", ctx.sql());
                }
                else {
                    log.debug("Executing query", ctx.sql());
                }
            }
        }
    }

    @Override
    public void recordEnd(ExecuteContext ctx) {
        if (log.isTraceEnabled() && ctx.record() != null)
            log.trace("Record fetched", ctx.record());
    }

    @Override
    public void resultEnd(ExecuteContext ctx) {
        if (log.isDebugEnabled() && ctx.result() != null) {
            String comment = "Fetched result";

            for (String line : ctx.result().format(5).split("\n")) {
                log.debug(comment, line);
                comment = "";
            }
        }
    }
}
