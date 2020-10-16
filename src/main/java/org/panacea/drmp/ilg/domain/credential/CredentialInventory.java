
package org.panacea.drmp.ilg.domain.credential;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CredentialInventory {

    private List<Credential> credentials;
    private String environment;
    private String fileType;
    private String snapshotId;
    private String snapshotTime;

}
