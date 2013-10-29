package com.careeropts.rurse.dao.support;


import org.hibernate.dialect.MySQL5Dialect;

import java.sql.Types;

/**
 * Custom MySQL Hibernate dialect to work around issue https://hibernate.atlassian.net/browse/HHH-6935
 * when using validate on a database with
 */
public class CustomMySQL5Dialect extends MySQL5Dialect {
    public CustomMySQL5Dialect() {
        super();
        registerColumnType(Types.BOOLEAN, "bit");
    }
}
