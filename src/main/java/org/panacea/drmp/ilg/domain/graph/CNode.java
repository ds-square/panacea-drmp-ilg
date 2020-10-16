
package org.panacea.drmp.ilg.domain.graph;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CNode {

    private String uuid;
    private String credentialType;

    public CNode(String uuid, String credentialType) {
        this.uuid = uuid;
        this.credentialType = credentialType;
    }
}
