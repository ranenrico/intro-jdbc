package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> { //parte di codice che gira nel while(rs.next)
    T mapRow(ResultSet rs) throws SQLException;
}
