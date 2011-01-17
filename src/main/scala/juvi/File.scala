package juvi

import java.io._
import scala.io.Source
import Using._

object File
{
  def fileToString(file: File, encoding: String) = {
    val inStream = new FileInputStream(file)
    val outStream = new ByteArrayOutputStream
    try {
      var reading = true
      while ( reading ) {
        inStream.read() match {
          case -1 => reading = false
          case c => outStream.write(c)
        }
      }
      outStream.flush()
    }
    finally {
      inStream.close()
    }
    new String(outStream.toByteArray(), encoding)
  }

  def catenateFiles(files:Iterable[File], target:File, encoding:String, filter:(String) => Boolean = (_) => true)
  {
    val fileOutputStream = new FileOutputStream(target)

    using(new OutputStreamWriter(fileOutputStream, encoding))
    { writer =>

      for(file <- files)
      {
        for (line <- Source.fromFile(file,encoding).getLines)
        {
          if(filter(line))
            writer.write(line + "\n")
        }
      }
    }

  }
}