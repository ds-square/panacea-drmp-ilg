
package org.panacea.drmp.ilg.domain.graph;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class HumanAccessEdge {

    private String source;
    private String destination;

    public HumanAccessEdge(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }
}
