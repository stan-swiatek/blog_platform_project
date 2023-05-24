package com.fdmgroup.blogplatform.exception;

public class UserIdNotFoundException extends Exception {
	public UserIdNotFoundException(int id) {
		super("The user with the id " + id + " could not be found.");
	}
}
