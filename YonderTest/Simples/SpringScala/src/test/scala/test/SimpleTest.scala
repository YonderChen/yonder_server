package test

import com.topteam.bean.Person
import org.junit.Test
import com.topteam.services.PersonService

class SimpleTest extends SpringTestContext {

  @Test
  def beanTest() {
    val person = applicationContext.getBean("john", classOf[Person])
    println(person.father.firstName)
  }

  @Test
  def beanInjectTest() {
    val personService = applicationContext.getBean(classOf[PersonService])
    println(personService.john.firstName)
  }

  @Test
  def jdbcQueryTest() {
    val personService = applicationContext.getBean(classOf[PersonService])
    println(personService.getAllPerson.size)
  }

  @Test
  def jdbcInsertTest() {
    val personService = applicationContext.getBean(classOf[PersonService])
    personService.savePerson(new Person("test2", "test2"))
    personService.savePerson(new Person("test", "test2"))
    println(personService.getAllPerson.size)
  }

  @Test
  def jdbcTransactionTest() {
    val personService = applicationContext.getBean(classOf[PersonService])
    personService.saveTransactionTest("1111")
    println(personService.getAllPerson.size)
  }

  @Test
  def test() {
    
    val s = "a" -> 1
    println(s)
  }
}