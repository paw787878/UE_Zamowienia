object xml_test extends App  {
  val page =
    <html>
      <head>
        <title>Hello XHTML world</title>
      </head>
      <body>
        <h1>Hello world</h1>
        <p><a href="scala-lang.org">Scala</a> talks XHTML</p>
      </body>
    </html>;
  println(page.toString())

}
