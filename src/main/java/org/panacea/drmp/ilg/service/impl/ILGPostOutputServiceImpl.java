package org.panacea.drmp.ilg.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ilg.domain.graph.InterLayerAttackGraphRepr;
import org.panacea.drmp.ilg.service.ILGPostOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class ILGPostOutputServiceImpl implements ILGPostOutputService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${interLayerAttackGraphRepr.endpoint}")
    private String interLayerAttackGraphReprURL;

    @Value("${interLayerAttackGraphRepr.fn}")
    private String interLayerAttackGraphReprFn;


    @Override
    public void postInterLayerAttackGraphRepr(InterLayerAttackGraphRepr repr) {
        HttpEntity<InterLayerAttackGraphRepr> requestEntity
                = new HttpEntity<>(repr);

        String endPointUrl = interLayerAttackGraphReprURL; // + '/' + repr.getSnapshotId() + '/';

        log.info("[ILG] POST interLayerAttackGraphRepr to " + endPointUrl);
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        try {

            response = restTemplate
                    .postForEntity(endPointUrl, requestEntity, String.class);
        } catch (HttpClientErrorException e) {

            System.out.println("Response from storage service: " + response);
            byte[] bytes = e.getResponseBodyAsByteArray();


            //Convert byte[] to String
            String s = new String(bytes);

            log.error(s);
            e.printStackTrace();

        }

    }

}
