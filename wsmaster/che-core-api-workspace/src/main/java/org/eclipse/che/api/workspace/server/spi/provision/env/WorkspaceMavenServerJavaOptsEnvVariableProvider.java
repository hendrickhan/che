/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.workspace.server.spi.provision.env;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.che.api.core.model.workspace.runtime.RuntimeIdentity;
import org.eclipse.che.commons.lang.Pair;

public class WorkspaceMavenServerJavaOptsEnvVariableProvider implements EnvVarProvider {

  /** Env variable for jvm settings */
  public static final String CHE_WORKSPACE_MAVEN_SERVER_JAVA_OPTIONS_DEFAULT =
      "CHE_WORKSPACE_MAVEN_SERVER_JAVA_OPTIONS_DEFAULT";

  private String javaOpts;

  @Inject
  public WorkspaceMavenServerJavaOptsEnvVariableProvider(
      @Named("che.workspace.maven.server.java.options.default") String javaOpts) {
    this.javaOpts = javaOpts;
  }

  @Override
  public Pair<String, String> get(RuntimeIdentity runtimeIdentity) {
    return Pair.of(CHE_WORKSPACE_MAVEN_SERVER_JAVA_OPTIONS_DEFAULT, javaOpts);
  }
}
