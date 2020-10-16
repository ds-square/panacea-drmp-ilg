
package org.panacea.drmp.ilg.domain.graph;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class InterLayerAttackGraphRepr {

    private String environment;
    private String fileType;
    private String snapshotId;
    private String snapshotTime;
    private List<CNode> nodes;
    private List<HumanAccessEdge> humanAccessEdges;
    private List<AccessNetworkEdge> accessNetworkEdges;

    public InterLayerAttackGraphRepr(String environment, String snapshotId, String snapshotTime, List<CNode> nodes, List<HumanAccessEdge> humanAccessEdges, List<AccessNetworkEdge> accessNetworkEdges) {
        this.environment = environment;
        this.fileType = "InterLayerAttackGraphRepr";
        this.snapshotId = snapshotId;
        this.snapshotTime = snapshotTime;
        this.nodes = nodes;
        this.humanAccessEdges = humanAccessEdges;
        this.accessNetworkEdges = accessNetworkEdges;
    }
}
