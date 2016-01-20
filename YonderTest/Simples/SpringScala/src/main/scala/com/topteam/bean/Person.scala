package com.topteam.bean

import com.topteam.dao.Table

class Person(val firstName: String, val lastName: String) {
  var father: Person = _
  var mother: Person = _
  
}
