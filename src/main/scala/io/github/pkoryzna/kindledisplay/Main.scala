package io.github.pkoryzna.kindledisplay

import java.awt.Color
import java.io.File
import java.time.ZonedDateTime
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.util.Locale

/**
  * Created by patryk on 25.04.17.
  */
object Main extends App {
  val kindleImage = ImageUtil.image(600, 800)
  val g = ImageUtil.graphics(kindleImage)
  val latoFont = ImageUtil.loadFont(
    "/META-INF/resources/webjars/lato/0.3.0/font/lato-medium/lato-medium.ttf") // trust me it's there
  val fontAwesome = ImageUtil.loadFont(
    "/META-INF/resources/webjars/font-awesome/4.7.0/fonts/fontawesome-webfont.ttf")

  val now = ZonedDateTime.now()
  val niceDate = now.format(
    DateTimeFormatter
      .ofLocalizedDate(FormatStyle.LONG)
      .withLocale(Locale.ENGLISH))

  val tempFile = File.createTempFile("image", ".png")

  new Drawing(600, 800) {
    string(s"It's $niceDate",
           x = 20,
           y = 20,
           font = latoFont.toOption,
           size = Some(40f))

    string(s"Last updated: ${now.toString}",
           x = 20,
           y = 700,
           font = latoFont.toOption,
           size = Some(15f))

  }.savePng(tempFile)

  println(tempFile)
}
