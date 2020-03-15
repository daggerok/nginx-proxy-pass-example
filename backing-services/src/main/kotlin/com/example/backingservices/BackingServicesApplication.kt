package com.example.backingservices

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

val app = beans {
  bean {
    router {
      "/".nest {
        contentType(MediaType.APPLICATION_JSON)
        path("/**") {
          ok().bodyValue(
              mapOf(
                  "hello" to "world",
                  "_self" to it.uri(),
                  "_base" to it.uri().run { "$scheme://$authority" }
              )
          )
        }
      }
    }
  }
}

@SpringBootApplication
class BackingServicesApplication : ApplicationContextInitializer<GenericApplicationContext> {
  override fun initialize(context: GenericApplicationContext) = app.initialize(context)
}

fun main(args: Array<String>) {
  SpringApplicationBuilder(BackingServicesApplication::class.java)
      .bannerMode(Banner.Mode.OFF)
      .properties(mapOf(
          "spring.output.ansi.enabled" to "always",
          "spring.main.lazy-initialization" to "true",
          "context.initializer.classes" to BackingServicesApplication::class.java.name
      ))
      .run(*args)
}
