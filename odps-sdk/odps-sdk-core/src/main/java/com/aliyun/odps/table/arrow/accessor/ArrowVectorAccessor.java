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

package com.aliyun.odps.table.arrow.accessor;

import org.apache.arrow.vector.ValueVector;

import com.aliyun.odps.table.utils.Preconditions;

/**
 * Access arrow column vector through specific subclasses.
 */
public abstract class ArrowVectorAccessor {

    private final ValueVector vector;

    public ArrowVectorAccessor(ValueVector vector) {
        this.vector = Preconditions.checkNotNull(vector, "Value vector");
    }

    public boolean isNullAt(int rowId) {
        return vector.isNull(rowId);
    }

    public final int getNullCount() {
        return vector.getNullCount();
    }

    public final void close() {
        vector.close();
    }
}
