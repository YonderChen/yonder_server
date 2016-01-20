package com.topteam.core.config

import org.springframework.scala.context.function.FunctionalConfiguration
import com.topteam.bean.Person
import org.springframework.scala.jdbc.core.JdbcTemplate
import org.apache.commons.dbcp.BasicDataSource

class ApplicationConfiguration extends FunctionalConfiguration {

  val jack = bean() {
    new Person("Jack", "Doe")
  }

  val jane = bean() {
    new Person("Jane", "Doe")
  }

  val john = bean("john") {
    val john = new Person("John", "Doe")
    john.father = jack()
    john.mother = jane()
    john
  }

  val dataSource = bean("dataSource") {
    val dataSource = new BasicDataSource
    dataSource.setDriverClassName("com.mysql.jdbc.Driver")
    dataSource.setUrl("jdbc:mysql://localhost:3306/Simples")
    dataSource.setUsername("root")
    dataSource.setPassword("12345")
    dataSource
  } destroy {
    _.close()
  }
}