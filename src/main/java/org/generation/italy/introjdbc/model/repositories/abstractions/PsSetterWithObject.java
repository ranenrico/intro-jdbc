package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PsSetterWithObject {
    void setParameters(PreparedStatement ps,Object o)throws SQLException;
}
