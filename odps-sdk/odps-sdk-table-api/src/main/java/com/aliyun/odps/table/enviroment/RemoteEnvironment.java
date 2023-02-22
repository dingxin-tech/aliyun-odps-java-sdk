/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.aliyun.odps.table.enviroment;

import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;

public class RemoteEnvironment extends ExecutionEnvironment {

    public RemoteEnvironment(EnvironmentSettings settings) {
        super(settings);
    }

    @Override
    protected void initialize() {
        // Do noting
    }

    @Override
    public String getTunnelEndpoint(String targetProject) {
        return settings.getTunnelEndpoint().orElseGet(() -> {
            try {
                Odps odps = createOdpsClient();
                return odps.projects().get(targetProject).getTunnelEndpoint();
                        // TODO: support tunnel quota name
                        // .getTunnelEndpoint(settings.getQuotaName().orElse(null));
            } catch (OdpsException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}