/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */
package org.eclipse.dataspaceconnector.iam.did;

import okhttp3.OkHttpClient;
import org.eclipse.dataspaceconnector.iam.did.impl.DistributedIdentityService;
import org.eclipse.dataspaceconnector.spi.iam.IdentityService;
import org.eclipse.dataspaceconnector.spi.security.Vault;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtension;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtensionContext;
import org.eclipse.dataspaceconnector.spi.types.TypeManager;

import java.util.Set;

/**
 *
 */
public class DidServiceExtension implements ServiceExtension {
    private static final String RESOLVER_URL = "http://23.97.144.59:3000/identifiers/";

    @Override
    public Set<String> provides() {
        return Set.of("iam");
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        var httpClient = context.getService(OkHttpClient.class);
        var vault = context.getService(Vault.class);
        var typeManager = context.getService(TypeManager.class);

        var identityService = new DistributedIdentityService(RESOLVER_URL, vault, httpClient, typeManager);

        context.registerService(IdentityService.class, identityService);

        context.getMonitor().info("Initialized Distributed Identity Service extension");
    }
}