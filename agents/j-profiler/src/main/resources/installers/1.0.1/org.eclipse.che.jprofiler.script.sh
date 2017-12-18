#
# Copyright (c) 2012-2017 Red Hat, Inc.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   Red Hat, Inc. - initial API and implementation
#
echo "j1"
wget https://download-keycdn.ej-technologies.com/jprofiler/jprofiler_linux_10_0_4.tar.gz -P /tmp/
echo "j2"
tar -xzf /tmp/jprofiler_linux_10_0_4.tar.gz -C ~/
echo "j3"
rm /tmp/jprofiler_linux_10_0_4.tar.gz
echo "j4"
