package com.topteam.spider

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.topteam.spider.SpiderEvent._
import org.jsoup.Jsoup

/**
 * Created by jf on 15/4/30.
 */
class PageSpider extends Actor {
  val urls = Seq("http://auto.qq.com/newcar.htm","http://auto.qq.com/news.htm")

  override def receive: Receive = {
    case Run => {
        //val doc = Jsoup.connect(url).timeout(10 * 1000).get
      // doc.body().getElementById()
    }
  }
}
