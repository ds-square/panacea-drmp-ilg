package org.panacea.drmp.ilg.service;

import org.panacea.drmp.ilg.controller.APIPostNotifyData.DataNotificationResponse;
import org.panacea.drmp.ilg.domain.notifications.DataNotification;
import org.panacea.drmp.ilg.exception.ILGException;

public interface OrchestratorNotificationHandlerService {
    DataNotificationResponse perform(DataNotification dataNotification) throws ILGException;
}
