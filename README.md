Projekt kompetencyjny. 

# TODO
  - ~~Połączyć dane z progress barami~~
  - ~~Poprawić kalkulator żywnościowy: chce przybać +/- na wadzę, prawdopodobnie trzeba zamienić znaki w algorytmie~~
  - Dodawanie 'części' produktu
  - Dodawanie własnych produktów do bazy danych
  - button 'removeAllEaten' w FragmentEaten
    >desc: Szybkie czyszczenie wszystkich zjedzonych produktów
  - Napisać algorytm liczący węgle, białko, tłuszcz
    >desc: póki co zrobiłem liczenie takie jakie jest opisane w DatabaseHelper.kt w metodzie onCreate na samym dole w komentarzu
  - Poprawić estetykę aplikacji
    >desc: Button 'addFromDatabase' we fragmencie FragmentEaten; layouty; kolory backgroundcolor; czcionki
  
  - _Propozycja:_ Zmiana przy kliknięciu wykresu kołowego procentów na wartości w gramach
  - _Propozycja:_ Zmiana preferencji użytkownika przez użytkownika (waga, przyrost wagi itd)
  - _Propozycja:_ Notyfikacja przy przekroczeniu limitu amam
  - _Propozycja:_ Dodać zasady tworzenia konta (8 znaków, mail musi mieć @, brak polskich znaków itd)
  - _Propozycja(dużo roboty):_ Licznik zjedzonych produktów danego rodzaju
    >desc: Na ten moment do bazy danych dodawane są produkty jako pojedyńcze itemy. Lepiej by wyglądało, gdyby item był wyświetlany jako "produkt zjedzony X razy" niż wylistowanie go kilkakrotnie.
