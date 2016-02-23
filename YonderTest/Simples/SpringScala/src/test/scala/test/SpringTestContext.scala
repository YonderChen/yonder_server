package test

import org.springframework.scala.context.function.FunctionalConfigApplicationContext
import com.topteam.core.config.ApplicationConfiguration
import org.springframework.context.support.ClassPathXmlApplicationContext

trait SpringTestContext {
  lazy val applicationContext =new ClassPathXmlApplicationContext("classpath:spring-context.xml")
}