package org.panacea.drmp.ilg.service;

import org.panacea.drmp.ilg.domain.credential.CredentialInventory;

public interface ILGInputRequestService {
    CredentialInventory performCredentialInventoryRequest(String snapshotId);
}
