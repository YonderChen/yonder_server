package com.topteam.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Qualifier
import com.topteam.bean.Person
import org.springframework.beans.factory.annotation.Autowired
import com.topteam.dao.PersonDao

@Service
class PersonService {

  @Autowired @Qualifier("john") var john:Person = _
  
  @Autowired var personDao: PersonDao =_ 
  
  def getAllPerson():List[Person] = personDao.findAllPerson.toList 
  
  def savePerson(person:Person) = personDao.savePerson(person)
  
  def saveTransactionTest(pk:String) = { 
    val p1 = new Person(pk,"test111")
    personDao.savePerson(p1)
    throw new Exception
  }
  
}