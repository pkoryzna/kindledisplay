package io.github.pkoryzna.kindledisplay

import java.awt.{Color, Font, Graphics2D, RenderingHints}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.util.Try


object ImageUtil {
  final val TextAntialiasing: RenderingHints = new RenderingHints(
    RenderingHints.KEY_TEXT_ANTIALIASING,
    RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

  def image(width: Int, height: Int): BufferedImage =
    new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

  def graphics(img: BufferedImage): Graphics2D = img.createGraphics()

  def loadFont(path: String, fontType: Int = Font.TRUETYPE_FONT): Try[Font] =
    Try {
      Font.createFont(fontType, getClass.getResourceAsStream(path))
    }

  def savePng(img: BufferedImage, file: File): Try[Boolean] = Try {
    ImageIO.write(img, "png", file)
  }

}


class Drawing(width: Int, height: Int) {
  private val image = ImageUtil.image(width, height)
  private val g = ImageUtil.graphics(image)
  private def colorFont: (Color, Font) = g.getColor -> g.getFont
  private def restoreCF(cf: (Color, Font)) = { g.setColor(cf._1); g.setFont(cf._2) }

  g.setRenderingHints(ImageUtil.TextAntialiasing)
  g.setBackground(Color.WHITE)
  g.setColor(Color.BLACK)

  g.clearRect(0,0,width,height)

  def string(s: String, x: Int, y: Int, color: Option[Color] = None, font: Option[Font] = None, size: Option[Float] = None) = {
    val (c,f) = colorFont

    val derivedFont = {
      val baseFont = font.getOrElse(f)
      size.map(s => baseFont.deriveFont(s)).getOrElse(baseFont)
    }

    color.foreach(g.setColor)
    g.setFont(derivedFont)
    g.drawString(s, x, y + size.map(_.toInt).getOrElse(0))
    restoreCF (c,f)
  }

  def savePng(file: File) = {
    ImageUtil.savePng(image, file)
  }
}