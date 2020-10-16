
package org.panacea.drmp.ilg.domain.credential;

import lombok.Data;

@Data
@SuppressWarnings("unused")

public class Destination {

    private String deviceId;
    private PrivilegeLevel privilegeLevel;
    private String serviceName;

    public enum PrivilegeLevel{
        NONE,
        USER,
        ROOT
    }
}
