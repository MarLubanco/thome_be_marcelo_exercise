package com.ecore.roles.service.impl;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersClient usersClient;

    public User getUser(UUID id) {
        return usersClient.getUser(id).getBody();
    }

    public List<User> getUsers() {
        return usersClient.getUsers().getBody();
    }
}
