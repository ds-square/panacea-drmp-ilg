package org.panacea.drmp.ilg;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ilg.domain.credential.Credential;
import org.panacea.drmp.ilg.domain.credential.CredentialInventory;
import org.panacea.drmp.ilg.domain.credential.Destination;
import org.panacea.drmp.ilg.domain.graph.AccessNetworkEdge;
import org.panacea.drmp.ilg.domain.graph.CNode;
import org.panacea.drmp.ilg.domain.graph.HumanAccessEdge;
import org.panacea.drmp.ilg.domain.graph.InterLayerAttackGraphRepr;
import org.panacea.drmp.ilg.domain.notifications.DataNotification;
import org.panacea.drmp.ilg.exception.ILGException;
import org.panacea.drmp.ilg.service.ILGInputRequestService;
import org.panacea.drmp.ilg.service.ILGPostOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class ILGGenerator {
    @Autowired
    ILGInputRequestService ilgInputRequestService;

    @Autowired
    ILGPostOutputService ilgPostOutputService;

    private CredentialInventory credentialInventory;

    @Synchronized
    public void generateILG(DataNotification notification) {
        try {
            this.getInput(notification.getSnapshotId());
        } catch (ILGException e) {
            log.error(e.getMessage());
        }

        //Build data structures
        Set<String> uuids = new HashSet<String>();
        List<CNode> nodes = new ArrayList<>();
        Map<EdgeKey, HumanAccessEdge> humanAccessEdges = new HashMap<>();
        Map<EdgeKey, AccessNetworkEdge> accessNetworkEdges = new HashMap<>();

        String source;
        String dest;
        for (Credential c : credentialInventory.getCredentials()) {
            String credId = c.getId();
            String privLevel = c.getPrivilegeType().name();
            String employeeId = c.getEmployeeId();
            if (!uuids.contains(credId)) {
                nodes.add(new CNode(credId, privLevel));
                uuids.add(credId);
            }
            source = generateNodeUUID(employeeId, Privilege.OWN.name());
            EdgeKey ek = new EdgeKey(source, credId);
            HumanAccessEdge humanAccessEdge = humanAccessEdges.get(ek);
            if (humanAccessEdge == null) {
                humanAccessEdge = new HumanAccessEdge(source, credId);
                humanAccessEdges.put(ek, humanAccessEdge);
//                log.info("Adding edge " + humanAccessEdge.toString() + " - " + humanAccessEdges.get(ek));
            }
            //TODO Improve check credential type
            if (!c.isPhysical()) {
                source = generateNodeUUID(employeeId, Privilege.USE.name());
                ek = new EdgeKey(source, credId);
                humanAccessEdge = humanAccessEdges.get(ek);
                if (humanAccessEdge == null) {
                    humanAccessEdge = new HumanAccessEdge(source, credId);
                    humanAccessEdges.put(ek, humanAccessEdge);
//                    log.info("Adding edge " + humanAccessEdge.toString() + " - " + humanAccessEdges.get(ek));
                }
                source = generateNodeUUID(employeeId, Privilege.EXECUTE.name());
                ek = new EdgeKey(source, credId);
                humanAccessEdge = humanAccessEdges.get(ek);
                if (humanAccessEdge == null) {
                    humanAccessEdge = new HumanAccessEdge(source, credId);
                    humanAccessEdges.put(ek, humanAccessEdge);
//                    log.info("Adding edge " + humanAccessEdge.toString() + " - " + humanAccessEdges.get(ek));
                }
            }
            for (Destination destNode : c.getDestination()) {
                dest = generateNodeUUID(destNode.getDeviceId(), destNode.getPrivilegeLevel().name());
                ek = new EdgeKey(credId, dest);
                AccessNetworkEdge accessNetworkEdge = accessNetworkEdges.get(ek);
                if (accessNetworkEdge == null) {
                    accessNetworkEdge = new AccessNetworkEdge(credId, dest);
                    accessNetworkEdges.put(ek, accessNetworkEdge);
//                    log.info("Adding edge " + accessNetworkEdge.toString() + " - " + accessNetworkEdges.get(ek));
                }
            }
/*            log.info(nodes.toString());
            log.info(accessNetworkEdges.toString());
            log.info(humanAccessEdges.toString());*/
        }

        InterLayerAttackGraphRepr graph = new InterLayerAttackGraphRepr(
                notification.getEnvironment(),
                notification.getSnapshotId(),
                notification.getSnapshotTime(),
                nodes,
                new ArrayList<>(humanAccessEdges.values()),
                new ArrayList<>(accessNetworkEdges.values())
        );

//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(graph));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        ilgPostOutputService.postInterLayerAttackGraphRepr(graph);

    }

    private void getInput(String version) {

        // get input data from REST service
        this.credentialInventory = ilgInputRequestService.performCredentialInventoryRequest(version);

//        log.info("[CredentialInventory]" + this.credentialInventory);
    }

    private String generateNodeUUID(String employee, String privilege) {
        return privilege + '@' + employee;
    }

    enum Privilege {
        EXECUTE(0),
        USE(1),
        OWN(2);
        public int level;

        Privilege(int level) {
            this.level = level;
        }
    }
}


class EdgeKey {
    String src;
    String dest;

    public EdgeKey(String src, String dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeKey edgeKey = (EdgeKey) o;
        return src.equals(edgeKey.src) &&
                dest.equals(edgeKey.dest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest);
    }
}
