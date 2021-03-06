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
package org.eclipse.che.multiuser.organization.api.event;

import static org.eclipse.che.multiuser.organization.shared.event.EventType.MEMBER_ADDED;

import org.eclipse.che.api.core.model.user.User;
import org.eclipse.che.multiuser.organization.shared.event.EventType;
import org.eclipse.che.multiuser.organization.shared.event.MemberEvent;
import org.eclipse.che.multiuser.organization.shared.model.Organization;

/**
 * Defines the event of adding the organization member.
 *
 * @author Anton Korneta
 */
public class MemberAddedEvent implements MemberEvent {

  private final String initiator;
  private final User member;
  private final Organization organization;

  public MemberAddedEvent(String initiator, User member, Organization organization) {
    this.initiator = initiator;
    this.member = member;
    this.organization = organization;
  }

  @Override
  public Organization getOrganization() {
    return organization;
  }

  @Override
  public EventType getType() {
    return MEMBER_ADDED;
  }

  @Override
  public String getInitiator() {
    return initiator;
  }

  @Override
  public User getMember() {
    return member;
  }
}
