package com.topteam.dao

import org.springframework.scala.jdbc.core.JdbcTemplate
import javax.sql.DataSource

trait BaseDao {

  implicit def dataSource: DataSource

  lazy val jdbcTemplate = new JdbcTemplate(dataSource)

}