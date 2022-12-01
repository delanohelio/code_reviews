[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.samza.context;
[CtUnresolvedImport]import org.apache.samza.config.Config;
[CtUnresolvedImport]import org.apache.samza.job.model.JobModel;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * The framework-provided context for the job.
 */
public interface JobContext {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the final configuration for this job.
     *
     * @return the configuration for this job
     */
    [CtTypeReferenceImpl]org.apache.samza.config.Config getConfig();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the name of the job.
     *
     * @return the name of this job
     */
    [CtTypeReferenceImpl]java.lang.String getJobName();

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the id for this job.
     *
     * @return the id for this job
     */
    [CtTypeReferenceImpl]java.lang.String getJobId();

    [CtMethodImpl][CtCommentImpl]/* Returns the job model for this job */
    [CtTypeReferenceImpl]org.apache.samza.job.model.JobModel getJobModel();
}