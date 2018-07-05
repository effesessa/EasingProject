package it.unical.utils;


import javax.servlet.http.HttpSession;

import it.unical.entities.User;

/**
 * Use this class to interact with the session (get and store attributes, etc.).
 */
public abstract class SessionUtils {

	private static final String ATTRIBUTE_USER_ID = "user";

	public static boolean isLoggedIn(HttpSession session) {
		return isUser(session);
	}

	public static boolean isUser(HttpSession session) {
		return session.getAttribute(ATTRIBUTE_USER_ID) != null;
	}

	/**
	 * This method returns the session user only if one exists, so check if it returns null or use
	 * the isUser method before.
	 */
	public static Integer getUserIdFromSessionOrNull(HttpSession session) {
		return (Integer) session.getAttribute(ATTRIBUTE_USER_ID);
	}


	public static void storeUserIdInSession(HttpSession session, User user) {
		session.setAttribute(ATTRIBUTE_USER_ID, user.getId());
	}

	public static void clearSession(HttpSession session) {
		session.invalidate();
	}
}