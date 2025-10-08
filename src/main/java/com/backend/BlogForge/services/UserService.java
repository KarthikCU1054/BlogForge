package com.backend.BlogForge.services;

import com.backend.BlogForge.domain.entities.User;

import java.util.UUID;

public interface UserService {
	User getUserById(UUID id);
}
