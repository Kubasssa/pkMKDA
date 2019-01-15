Projekt kompetencyjny. 

# TODO
  - ~~Połączyć dane z progress barami~~
  - ~~Poprawić kalkulator żywnościowy: chce przybać +/- na wadzę, prawdopodobnie trzeba zamienić znaki w algorytmie~~
  - ~~button 'removeAllEaten' w FragmentEaten~~
    ~~>desc: Szybkie czyszczenie wszystkich zjedzonych produktów~~
  - ~~Poprawić estetykę aplikacji~~
    ~~>desc: Button 'addFromDatabase' we fragmencie FragmentEaten; layouty; kolory backgroundcolor; czcionki~~
    - ~~Dodanie szczegółów zjedzonego produktu~~
  - ActivityEditProfile -> oprócz kalorii zmiana wszystkich wartości odżywczych + odświeżanie + zmienić nazwy tabów
  - ActivityEditProfile -> możliwość zmiany tylko niektórych pól
  - Dodawanie 'części' produktu
  - Dodawanie własnych produktów do bazy danych **dokończyć**
  - Usuwanie produktów z bazy danych? !! DUŻO ROBOTY !!
  - Bugfix z populateDatabse() (produkty Madzi crashują apke)
  - Napisać algorytm liczący węgle, białko, tłuszcz
    >desc: póki co zrobiłem liczenie takie jakie jest opisane w DatabaseHelper.kt w metodzie onCreate na samym dole w komentarzu
  - Optymalizacja zasobów procesora: guzik 'Remove All' w FragmentEaten
  - Optymalizacja kodu: funkcja FragmentEaten -> showEatenProductDetails(int) -> napisać clase zamiast bałaganu w metodzie 
  
  - _Propozycja:_ Zmiana przy kliknięciu wykresu kołowego procentów na wartości w gramach
  - _Propozycja:_ Zmiana preferencji użytkownika przez użytkownika (waga, przyrost wagi itd)
  - _Propozycja:_ Notyfikacja przy przekroczeniu limitu amam
  - _Propozycja:_ Dodać zasady tworzenia konta (8 znaków, mail musi mieć @, brak polskich znaków itd)
  - _Propozycja(dużo roboty):_ Licznik zjedzonych produktów danego rodzaju
    >desc: Na ten moment do bazy danych dodawane są produkty jako pojedyńcze itemy. Lepiej by wyglądało, gdyby item był wyświetlany jako "produkt zjedzony X razy" niż wylistowanie go kilkakrotnie.
    
    
    
    
    
    
    
    
    help:
    https://guides.github.com/features/mastering-markdown/
