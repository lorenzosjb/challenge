Your account number is: 70674

Your new database is now ready to use.

To connect to your database use these details Host: sql5.freesqldatabase.com

Database name: sql552195
Database user: sql552195
Database password: mN2%dX6*
Port number: 3306

phpMyAdmin: http://www.phpmyadmin.co 

--------------

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://sql5.freesqldatabase.com:3306/sql552195?zeroDateTimeBehavior=convertToNull</property>
    <property name="hibernate.connection.username">sql552195</property>
    <property name="hibernate.connection.password">mN2%dX6*</property>
    <mapping resource="database/Champions.hbm.xml"/>
    <mapping resource="database/Tournaments.hbm.xml"/>
  </session-factory>
</hibernate-configuration>

--------------

CREATE DATABASE IF NOT EXISTS sql552195;
USE sql552195;
SET foreign_key_checks = 0;

-- DROP TABLE tournaments;
CREATE TABLE tournaments (
    tournament_id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    PRIMARY KEY (tournament_id)
) ENGINE=InnoDB;

-- DROP TABLE champions;
CREATE TABLE champions (
    record_id int NOT NULL AUTO_INCREMENT primary key,
    name varchar(45) NOT NULL,
    tournament_id int NOT NULL,
    points_earned smallint NOT NULL,
    FOREIGN KEY fk_tou (tournament_id)
    REFERENCES tournaments(tournament_id)
) ENGINE=InnoDB;
