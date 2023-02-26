module socket {
    requires java.desktop;
    /**
     * Konrad Pempera
     * Celem projektu było oprogramowanie prototypu systemu obługującego proces reklamacji.
     * Projekt składa się z czterech aplikacji
     * 1) server
     * 2) client
     * 3) producent
     * 4) seller
     * Aplikacja klienta została napisana w Java Swing, umożliwia zalogowanie się do systemu, wprowadzenie danych, wysłanie danych na serwer, monitorowanie stanu reklamacji
     * Aplikacje seller, server, producent są aplikacjami konsolowymi, w przypadku sellera oraz producenta, przyjęto automatyczne podejmowanie decyzji, odbywają się one w losowych terminach, nie przekraczających 14 dni od momentu otrzymania infromacji
     * Można uruchomić dowolną ilość aplikacji clienta i aplikacji producenta (aplikacja sellera jest pojedyncza)
     * Aplikacje komunikują się przy pomocy technologii socketów
     * Wszystkie aplikacje wymieniają informacje między sobą przez serwer, na którym znajduje się baza danych z informacjami o reklamacjach
     * Czynności wykonywane przez poszczególne aplikacje synchronizowane są poprzez zegar, odczytywany przez aplikacje serwer, przyjmuje się, że jedna sekunda to jeden dzień symulacji
     * Aplikacja serwera i pozostałe aplikacje są aplikacjami wielowątkowymi umożliwiającymi jednoczesne wysyłanie i nasłuchiwanie komunikatów
     * Aplikacja clienta wczytuje na początku dane, które mogą być bezpośrednio wysłane do serwera
     * uruchomienie producenta wymaga podania nazwy użytkownika oraz hasła (przykładowo login: Samsung, hasło: Samsung123) pozostałe loginy i hasła znajdują się w pliku usernames.csv
     * W przypadku dopisywania nowych reklamacji (complaints) uwzględniane są tylko pola : idofcomplain, username, Product, Company, status, Description.
     * W celu wysłania wiadomości należy oznaczyć wiadomość statusem tosent
     */
}