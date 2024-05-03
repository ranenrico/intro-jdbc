package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcTemplate<T>{

    List<T> query(String sql){
        try(Connection c = ConnectionUtils.createConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Customer(rs.getInt("custid"),rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"),
                    rs.getString("address"), rs.getString("city"), rs.getString("region"),rs.getString("postalcode") , 
                    rs.getString("country"),rs.getString("phone"), rs.getString("fax")));
                } else {
                    return Optional.empty();
                }
            
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca del cliente per id",e);
        }
    }

}
