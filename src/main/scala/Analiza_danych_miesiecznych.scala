object Analiza_danych_miesiecznych {
  def main(args: Array[String]): Unit = {
    /*
    Ten obiekt przeprowadza analize wstepnie przetworzonych danych znajdujacych sie w folderze "wyniki".
    Przeprowadza dwie analizy. Pierwsza analiza w metodzie "analiza_bez_exchange()" utworzy plik "wyniki_bez_exchange.txt"
    w ktorym znajdowac sie będą kwoty policzone w oryginalnych jednostkach w których wystąpiły w dokumentach.
    Metoda "analiza_w_euro()" natomiast przelicza kwotę na euro jeśli mam dostępny kurs wymiany
    tej waluty  na euro z czasu wystąpienia transakcji. Nie uwzględniam inflacji.
     */

    analiza_w_euro()
    analiza_bez_exchange()


    def analiza_bez_exchange(): Unit = {
      var wszystkie = new InfoPanstwaDanyOkres()
      val do_przerobienia = Wstepne_przetwarzanie.ls("wyniki")
      for (path <- do_przerobienia) {
        //val miesiac=path.split("/")(1).substring(0,7)
        wszystkie.doczytaj(path)

      }
      wszystkie.print_do_pliku_czysto("wyniki_bez_exchange.txt")

    }

    def analiza_w_euro(): Unit = {
      var wszystkie = new InfoPanstwaDanyOkres()
      val do_przerobienia = Wstepne_przetwarzanie.ls("wyniki")
      for (path <- do_przerobienia) {
        val miesiac = path.split("/")(1).substring(0, 7)
        val nowy = new InfoPanstwaDanyOkres()
        nowy.doczytaj(path)
        nowy.zmien_walute(miesiac)
        wszystkie.add_panstwa(nowy)

      }
      wszystkie.print_do_pliku_czysto("wyniki_z_exchange.txt")
    }


  }
}
