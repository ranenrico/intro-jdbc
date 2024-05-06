package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PsSetterWithObject<T> {
    void setParameters(PreparedStatement ps,T o)throws SQLException;
}
