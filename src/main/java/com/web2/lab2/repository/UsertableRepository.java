package com.web2.lab2.repository;

import com.web2.lab2.model.UserTable;
import org.springframework.data.repository.CrudRepository;

public interface UsertableRepository extends CrudRepository<UserTable, Integer> {
    UserTable findUserTableByUsername(String username);
}
