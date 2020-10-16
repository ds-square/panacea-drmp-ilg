package org.panacea.drmp.ilg.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ilg.domain.credential.CredentialInventory;
import org.panacea.drmp.ilg.service.ILGInputRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ILGInputRequestServiceImpl implements ILGInputRequestService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${credentialInventory.endpoint}")
    private String credentialInventoryURL;
    @Value("${credentialInventory.fn}")
    private String credentialInventoryFn;

    @Override
    public CredentialInventory performCredentialInventoryRequest(String snapshotId) {
        log.info("[ILG] GETting " + credentialInventoryURL);
        ResponseEntity<CredentialInventory> responseEntity = restTemplate.exchange(
                credentialInventoryURL + '/' + snapshotId, // + '/' + credentialInventoryFn,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CredentialInventory>() {
                });
        CredentialInventory credentialInventory = responseEntity.getBody();
        return credentialInventory;
    }
}
