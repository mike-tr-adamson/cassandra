/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.cassandra.index.sai.cql;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.runners.Parameterized;

import org.apache.cassandra.inject.Injections;
import org.apache.cassandra.config.DatabaseDescriptor;

/**
 * Force generates segments due to a small RAM size on compaction, to test segment splitting
 */
public class SingleNodeTinySegmentQueryTest extends AbstractIndexQueryTest
{
    @Before
    public void setup() throws Throwable
    {
        requireNetwork();

        Injections.inject(INDEX_QUERY_COUNTER);

        DatabaseDescriptor.setSAISegmentWriteBufferSpace(0);
    }

    @SuppressWarnings("unused")
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> params()
    {
        List<Object[]> scenarios = new LinkedList<>();

        scenarios.add(new Object[]{ new DataModel.BaseDataModel(DataModel.NORMAL_COLUMNS, DataModel.NORMAL_COLUMN_DATA), BASE_QUERY_SETS });

        scenarios.add(new Object[]{ new DataModel.CompoundKeyDataModel(DataModel.NORMAL_COLUMNS, DataModel.NORMAL_COLUMN_DATA), BASE_QUERY_SETS });

        scenarios.add(new Object[]{ new DataModel.CompoundKeyWithStaticsDataModel(DataModel.STATIC_COLUMNS, DataModel.STATIC_COLUMN_DATA), STATIC_QUERY_SETS });

        scenarios.add(new Object[]{ new DataModel.CompositePartitionKeyDataModel(DataModel.NORMAL_COLUMNS, DataModel.NORMAL_COLUMN_DATA), BASE_QUERY_SETS });

        return scenarios;
    }
}