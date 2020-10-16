package org.panacea.drmp.ilg.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ilg.ILGGenerator;
import org.panacea.drmp.ilg.controller.APIPostNotifyData;
import org.panacea.drmp.ilg.domain.notifications.DataNotification;
import org.panacea.drmp.ilg.exception.ILGException;
import org.panacea.drmp.ilg.service.OrchestratorNotificationHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrchestratorNotificationHandlerServiceImpl implements OrchestratorNotificationHandlerService {

    public static final String INVALID_NOTIFICATION_ERR_MSG = "Invalid Data Notification Body.";

    @Autowired
    ILGGenerator ilgGenerator;

    @Override
    public APIPostNotifyData.DataNotificationResponse perform(DataNotification notification) throws ILGException {
        log.info("[ILG] Received Data Notification from Orchestrator: {}", notification);
        try {
            if (notification.getEnvironment() == null) {
                throw new ILGException("No environment defined for notification.");
            }
            ilgGenerator.generateILG(notification);


            return new APIPostNotifyData.DataNotificationResponse(notification.getEnvironment(), notification.getSnapshotId(), notification.getSnapshotTime());
        } catch (ILGException e) {
            log.info("HAGException occurred: ", e);
            throw new ILGException(INVALID_NOTIFICATION_ERR_MSG, e);
        }

    }


}

