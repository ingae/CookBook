import java.io.FileWriter

object CookBook extends App {
  val srcName = "13177-8.txt"
  val dstName = "13177-8-results.txt"

  def openSource(fName:String) = {
    //actually get a real sequence of strings
    val filePointer = scala.io.Source.fromFile(fName)
    val myLines = filePointer.getLines.toSeq

    filePointer.close()
    myLines
  }

  def processSeq(mySeq:Seq[String])= {
    // Recipes with ingredient list start after "Recipes Specially Prepared _by_ Miss Elizabeth Kevill Burr"
    // and end before "WALTER BAKER & CO., Ltd."
    val startText = "Recipes Specially Prepared _by_ Miss Elizabeth Kevill Burr"
    val endText = "WALTER BAKER & CO., Ltd."
    var inRecipe = false
    mySeq.slice(mySeq.indexOf(startText), mySeq.indexOf(endText)).filter(line => {
      // Recipe names start with at least 2 capital characters
      // Ingredients start with four spaces
      line.take(2).matches("[A-Z][A-Z]") || line.startsWith("    ")
    })
  }

  def saveSeq(destName:String, mySeq:Seq[String]) = {
    println(s"Saving my Sequence to file $destName")
    mySeq.foreach(println) //we are good up to here
    val fw = new FileWriter(destName)
    mySeq.map(_ + "\n").foreach(fw.write) // adding new line to each line before writing
    fw.close()
  }

  //the actual program runs here, little tiny pipeline like a factory
  val mySeq = openSource(srcName)
  val filteredSeq = processSeq(mySeq)
  saveSeq(dstName,filteredSeq)
}