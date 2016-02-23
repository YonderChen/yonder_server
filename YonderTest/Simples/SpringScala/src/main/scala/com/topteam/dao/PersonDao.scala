package com.topteam.dao

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.sql.DataSource
import com.topteam.bean.Person
import java.sql.ResultSet

@Repository
class PersonDao extends BaseDao {

  @Autowired @Qualifier("dataSource") var ds: DataSource = _

  implicit lazy val dataSource = ds

  def findAllPerson: Seq[Person] = {
    def toPerson(rs: ResultSet, i: Int): Person = new Person(rs.getString(1), rs.getString(2))
    jdbcTemplate.queryAndMap("select * from Person")(toPerson)
  }

  def savePerson(person: Person) = {
    jdbcTemplate.updateWithSetter("insert into Person(firstName, LastName) values(?,?)")(ps => {
      ps.setString(1, person.firstName)
      ps.setString(2, person.lastName)
    }) 
  }
}