package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
<<<<<<< HEAD
public interface RowMapper<T> {
=======
public interface RowMapper<T> { //parte di codice che gira nel while(rs.next)
>>>>>>> origin/francesca_piccitto
    T mapRow(ResultSet rs) throws SQLException;
}
