package it.unical.dao;

import java.util.List;

import it.unical.entities.User;

public interface UserDAO {

	public void create(User user);

	public void delete(User user);

	public void update(User user);

	User get(String email);
	
	User get(Integer id);

	public List<User> getAll();
}