
package org.panacea.drmp.ilg.domain.credential;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Credential {
    private List<Destination> destination;
    private String employeeId;
    private String id;
    private CredentialType privilegeType;
    private Double robustness;

    public enum CredentialType {
        PASSWORD(0),
        SHARED_KEY(1),
        BADGE(2),
        TOKEN(3);
        public int credType;

        CredentialType(int credType) {
            this.credType = credType;
        }
//
//        public boolean isPhysical() {
//            return this.credType >= 2;
//        }
    }

    public boolean isPhysical(){
        return this.privilegeType.credType >=2;
    }


}
