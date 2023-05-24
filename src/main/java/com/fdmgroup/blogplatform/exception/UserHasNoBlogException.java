package com.fdmgroup.blogplatform.exception;

import com.fdmgroup.blogplatform.model.User;

public class UserHasNoBlogException extends Exception {
	public UserHasNoBlogException(User user) {
		super(user.getUsername() + " has no blog.");
	}
}
