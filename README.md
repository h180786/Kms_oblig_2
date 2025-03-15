Test cases:
  * Generel info
    - Rule bestemmer hvor testen skal skje ved bruk av ActivityScenarioRule
    - Starter test med init
    - Avslutter test med release

  *MainActivityTest
    - Åpner opp main activity og trykker på gallery button
    - Blir dermed tatt videre til gallery button
    - Testen er en suksess

  *GalleryActivityTest
    - Henter adapter fra recyclerview, slik at jeg kan kalle på getItemCount() og få riktig antall bilder i galleriet når testen starter
    - Går inn i recyclerView, finner item nr 0(første i listen) og trykker på deletebutton
    - Deretter sjekker jeg med itemcount - 1 om expected er som forventet(at jeg har fjernet et eleement fra galleriet)
    - Testen er en suksess

  *AddImageTest
    - Henter adapter fra recyclerview, slik at jeg kan kalle på getItemCount() og få riktig antall bilder i galleriet når testen starter
    - Legger til en fil(Ikke 100% sikker på hvordan dette fungerer, men antar det er en dummy fil) 
    - Åpner add image fra filer og laster opp filen
    - Trykker på riktige knapper, og skriver inn et tilfelldig navn, her "Test" og legger bilde til i galleriet
    - Sjekker med itemcounten som jeg lagde tidligere at databasen har 1 bilde mer en før testen startet
    - Testen er en suksess

  *QuizActivityTest
    - Starter med å åpne quizactivity og trykke på 3 feile knapper og 2 riktige, i rekkefølgen f-r-f-f-r
    - Deretter sjekker jeg om poengsummen er som forventet (2 riktige, 5 totale)
    - Testen er en suksess
