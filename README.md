# Zadanie 3 - Eshop
B-OOP 2021

Vašou úlohou je naprogramovať aplikačný server v jazyku Java s pomocou frameworku Spring.

Aplikačný server umožňuje správu tovaru, skladu a nákupných košíkov.
Webové rozhranie (API), ako aj objekty, ktoré sa používajú na komunikáciu s vonkajším svetom sú definované v zadaní a musi byť použité na komunikáciu cez webové služby. Mimo webového rozhrania môžete použiť ďalšie objekty podľa vášho návrhu, ak to uznáte za vhodné.

Špecifikáciu webového rozhrania, ktoré má aplikácia poskytovať nájdete tu: https://app.swaggerhub.com/apis-docs/sk-stuba-fei-uim-oop/OOP_Zadanie_3/1.0.0

Pokiaľ je v API uvedené, že sa má vrátiť kód 404 a v popise nie je povedané kedy, je ho potrebné vrátiť v prípade, že poskytnuté ID v systéme neexistuje.

### Popis systému

Podrobný popis jednotlivých operácii je uvedený v špecifikácii API.

Systém umožňuje pridávanie a odoberanie produktov zo sortimentu obchodu. Ďalej umožňuje úpravu existujúcich produktov, ako aj navýšenie stavu produktov na sklade.

Systém umožňuje vytváranie a vymazávanie objednávok. Do objednávok je možné pridávať produkty v určenom množstve (pokiaľ je produktu na sklade dostatok). Systém ďalej umožňuje zaplatenie ešte nezaplatených objednávok. Do zaplatených objednávok nie je možné pridávať ďalšie položky.

## Automatizované testy

Projekt obsahuje automatizované testy. Testy sa **NESPÚŠŤAJÚ** automaticky pri git push. Pokiaľ si chcete overiť na koľko vaša implementácia spĺňa testy musíte si ich spustiť sami. Testy sa dajú spustiť 2 spôsobmi:
* cez Maven, spustením _test_ lifecycle-u (bočné menu > maven > _projekt_ > lifecycle > test)
* spustením testov rovno z triedy ktorá ich obsahuje (nachádza sa v src/test/sk/.../oop/)

## Hodnotenie

Zadanie je hodnotené 15 bodmi. **Odovzdaný program musí byť skompilovateľný, inak je
hodnotený 0 bodmi**. Skompilovateľnosť zadania kontroluje aj github pipeline. Hlavný dôraz v hodnotení sa kladie na objektový prístup a princípy OOP,
okrem iného:

* vhodné pomenovanie tried, metód a premenných v jednotnom jazyku (názvy tried s veľkým počiatočným písmenom, názvy metód s malým),
* vhodné použitie modifikátorov prístupu (public, private, poprípade protected) na obmedzenie prístupu k metódam a atribútom,
* využitie dedenia a polymorfizmu,
* použitie výnimiek na ošetrenie nedovoleného správania (nehádzať a nezachytávať všeobecnú triedu Exception),
* nepoužívajte nested classy,
* vo vašich triedach nevytvárajte statické metódy ani nekonštantné statické premenné (v zadaní nie sú potrebné),
* hlavná trieda (main) môže obsahovať len kód potrebný na inicializáciu aplikácie pomocou Spring frameworku,
* vo svojom riešení môžete použiť knižnicu lombok a jej anotácie. Potrebná dependencia je už pridaná v _pom.xml_ súbore.

## Odovzdávanie
Zadanie si naklonujte z repozitára zadania výhradne pomocou poskytnutej linky cez GitHub Classroom (pokiaľ si vygenerujete vlastný repozitár pomocou tlačidla "Use this template" z template repozitára, my váš repozitár neuvidíme a nebudeme ho hodnotiť!). Svoje vypracovanie nahrajte do pre vás vytvoreného repozitára pre toto zadanie pomocou programu Git (git commit + git push).
Skontrolujte si, či sa váš repozitár nachádza pod skupinov **Interes-Group**, inak nemáme prístup ku vášmu repozitáru a zadanie sa nepovažuje za odovzdané. Vypracovanie môžete "pusho-vať" priebežne. Hodnotiť sa bude iba _master_ branch. Zadanie je nutné vypracovať do **23.4.2021 23:00**.

V projekte upravujte iba súbory v priečinku _src/main_ a jeho podpriečinkoch. Ostatné súbory je upravovať zakázané (predovšetkým súbory _pom.xml_, súbory obsahujúce github pipeline-y a súbory obsahujúce automatizované testy).

Vo svojom github účte si nastavte svoje meno (settings > profile > name), aby bolo možné priradiť riešenie ku študentovy. **Pokiaľ nebude možné spárovať študenta s riešením je zadanie hodnotené 0 bodmi!**

[comment]: <> (TODO)
[comment]: <> (# Assignment 2 - Rook in a maze)

[comment]: <> (B-OOP 2021)

[comment]: <> (Your task is to create a java window application with the Swing library. The user interface of the application must consist of:)

[comment]: <> (* canvas)

[comment]: <> (* side menu &#40;you can choose the location of this side menu freely&#41;)

[comment]: <> (When the application starts, generate a maze using the [randomized depth first search]&#40;https://www.baeldung.com/cs/maze-generation#dfs-maze&#41; algorithm and draw it onto the canvas.)

[comment]: <> (The grid containing the maze must be at least 11x11 tiles large &#40;including wall tiles&#41;. The entire maze must be surrounded by walls &#40;no paths in the maze can touch the borders of the window&#41;.)

[comment]: <> (In the generated maze choose in any way a starting and a goal point. Place the player figure on the starting point. The player can move their figure in 3 ways:)

[comment]: <> (* by using the arrows keys on their keyboard. Always by one tile in the specified direction. They cannot enter a tile that contains a wall.)

[comment]: <> (* by using the mouse. When the player figure is clicked, it can be moved like a rook chess piece &#40;i.e. any number of tiles in the horizontal, or vertical directions&#41;.)

[comment]: <> (  The destination is chosen by a second mouse click. Tiles that can be moved to in this manner must be highlighted, when the mouse hovers over them. When moving in this manner, the player cannot jump over walls.)

[comment]: <> (* by using buttons on the side menu, that represent the arrow keys. The rules for moving in this manner are the same, as for the movement by the arrow keys on the keyboard.)

[comment]: <> (When the player reaches the goal point, the application should generate a new maze and the game starts over.)

[comment]: <> (The side menu must contain:)

[comment]: <> (* a counter of successfully completed mazes)

[comment]: <> (* a button that resets the counter and generates a new maze)

[comment]: <> (* buttons representing the arrow keys, which can be used to move the player. These buttons must be placed in the same way, they are placed on a regular keyboard.)



[comment]: <> (## Grading)

[comment]: <> (You can get 15 points for this assignment. **The program must be able to compile, otherwise 0 points are given for the assigment**.)

[comment]: <> (The github pipeline checks whether the program can be compiled. The main focus during grading is put on object-oriented approach and OOP principles used by the solution.)

[comment]: <> (Including, but not limited to:)

[comment]: <> (* appropriate naming of classes, methods and variables in a single language &#40;class names starting with a capital letter, method names starting with a lowercase letter&#41;,)

[comment]: <> (* appropriate use of access modifiers &#40;public, private, or protected&#41; when restricting access to class methods and attributes,)

[comment]: <> (* the use of inheritance and polymorphism,)

[comment]: <> (* usage of exceptions when handling undesired behavior &#40;do not catch or throw the instances of the generic Exception class&#41;,)

[comment]: <> (* don't use nested classes,)

[comment]: <> (* don't use static methods, or non-constant static variables &#40;you don't need them to complete the assignment&#41;,)

[comment]: <> (* don't put any logic into the main method and its class. The main method should only be used to create a new object,)

[comment]: <> (* you can use the lombok library and its annotations in your solution. The neccessary dependency is already present in the _pom.xml_ file.)

[comment]: <> (## Handing in the assigment)

[comment]: <> (Clone the assignment from the repository created from this template by the provided link trough GitHub Classroom &#40;if you create your own repository with the "use this template" button, we won't be able to see your repository and we won't be able to grade it!&#41;. Upload your solutions to your repository using the Git version control system &#40;git commit + git push&#41;.)

[comment]: <> (Make sure, that your repository was created under the **Interes-Group** group, otherwise we won't be able to access your repository, and the assignment will not be graded.)

[comment]: <> (You can push commits to the repository while you work - you don't have to push everything at once. Only the code in the _master_ branch will be graded. You have until **23.4.2021 23:00** to complete the assignment.)

[comment]: <> (Only edit files in the _src/main_ folder or its sub-folders. You mustn't change any other files in the repository &#40;especially the _pom.xml_ file, and the github pipeline files&#41;.)

[comment]: <> (You have to have your name set in your github account &#40;settings > profile > name&#41;, so that we can match students with their assignments. **If we are unable to match a student with their assignment, the student will receive 0 points for the assignment!**)
