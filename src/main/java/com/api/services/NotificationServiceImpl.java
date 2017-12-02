package com.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.enums.ApplicationStatus;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public void notifyApplicationStatusChange(ApplicationStatus oldStatus, ApplicationStatus newStatus) {
		switch (newStatus) {
		case APPLIED:
			applicationStatusChangeToApplied(oldStatus);
			break;

		case HIRED:
			applicationStatusChangeToHired(oldStatus);
			break;

		case INVITED:
			applicationStatusChangeToInvited(oldStatus);
			break;

		case REJECTED:
			applicationStatusChangeToRejected(oldStatus);
		}

	}

	/**
	 * Handle business case for new status APPLIED
	 * 
	 * @param oldStatus
	 * @see ApplicationStatus
	 */
	private void applicationStatusChangeToApplied(ApplicationStatus oldStatus) {
		logger.info("Application status changed from " + oldStatus + " to APPLIED");
	}

	/**
	 * Handle business case for new status HIRED
	 * 
	 * @param oldStatus
	 * @see ApplicationStatus
	 */
	private void applicationStatusChangeToHired(ApplicationStatus oldStatus) {
		logger.info("Application status changed from " + oldStatus + " to HIRED");
	}

	/**
	 * Handle business case for new status INVITED
	 * 
	 * @param oldStatus
	 * @see ApplicationStatus
	 */
	private void applicationStatusChangeToInvited(ApplicationStatus oldStatus) {
		logger.info("Application status changed from " + oldStatus + " to INVITED");
	}

	/**
	 * Handle business case for new status REJECTED
	 * 
	 * @param oldStatus
	 * @see ApplicationStatus
	 */
	private void applicationStatusChangeToRejected(ApplicationStatus oldStatus) {
		logger.info("Application status changed from " + oldStatus + " to REJECTED");
	}

}
