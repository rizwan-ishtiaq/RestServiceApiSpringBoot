package com.api.services;

import com.api.enums.ApplicationStatus;

/**
 * Service to handle notifications
 * 
 * @author Rizwan Ishtiaq
 *
 */
public interface NotificationService {

	/**
	 * Trigger the event according to business requirement. Implementation is
	 * depends on use case. One can give implementation of each status differently
	 * or handle all in single case
	 * 
	 * @param oldStatus
	 *            Old status of Application
	 * @param newStatus
	 *            New status of Application
	 * 
	 * @see ApplicationStatus
	 */
	void notifyApplicationStatusChange(ApplicationStatus oldStatus, ApplicationStatus newStatus);

}
