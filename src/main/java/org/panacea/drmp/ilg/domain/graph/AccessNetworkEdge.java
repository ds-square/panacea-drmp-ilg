
package org.panacea.drmp.ilg.domain.graph;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class AccessNetworkEdge {

    private String source;
    private String destination;

    public AccessNetworkEdge(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }
}
