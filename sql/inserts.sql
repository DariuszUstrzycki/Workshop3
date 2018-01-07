INSERT INTO user_group (`id`, `name`) VALUES ('1', 'students');
INSERT INTO user_group (`id`,`name`) VALUES ('2', 'teachers');
INSERT INTO user_group (`id`,`name`) VALUES ('3', 'admins');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('1', 'admin', 'admin@wp.pl', '$2a$10$/P5Ox7sGy.XOyYXKzwg6z.dVJ2NIb2HhPXR0xChJcupJBEXPEu1Xe', '3');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('2', 'darco', 'darco@wp.pl', '$2a$10$TQrU.5lSugohIOkMt6soY.LSZsxSGjagMdu1kKJQ6QZ/ADGNcNRfS', '1');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('3', 'jeff', 'jeff@onet.pl', '$2a$10$TQrU.5lSugohIOkMt6soY.LSZsxSGjagMdu1kKJQ6QZ/ADGNcNRfS', '2');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('4', 'maria', 'maria@gmail.com', '$2a$10$TQrU.5lSugohIOkMt6soY.LSZsxSGjagMdu1kKJQ6QZ/ADGNcNRfS', '2');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('5', 'thomas', 'thomas@gmail.com', '$2a$10$TQrU.5lSugohIOkMt6soY.LSZsxSGjagMdu1kKJQ6QZ/ADGNcNRfS', '1');

INSERT INTO `school`.`user` (`id`, `username`, `email`, `password`, `user_group_id`) 
VALUES ('6', 'bobby', 'bobby@gmail.com', '$2a$10$TQrU.5lSugohIOkMt6soY.LSZsxSGjagMdu1kKJQ6QZ/ADGNcNRfS', '1');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('1', 'ZADANIE 5.93', 'Samochód ciężarowy jechał ze stałą prędkością 54 km/h. W chwili gdy mijał stojący na sąsiednim pasie ruchu samochód  osobowy, ten ruszył za samochodem ciężarowym ruchem jednostajnie przyspieszonym z przyspieszeniem  4 m/s.

a)  Po jakim czasie samochód osobowy dogoni samochód ciężarowy?
b)  Jaką drogę przejechał samochód osobowy?
c)  Z jaką prędkością poruszał się samochód osobowy w chwili, gdy mijał samochód ciężarowy?', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('1', '2018-01-06 12:55:35', '2018-01-06 12:55:35', 'Tego typu zadania stają się przejrzyste, gdy używamy wykresu; w naszym przypadku pomocny będzie wykres drogi od czasu. Radzimy, aby przy rozwiązywaniu zadań, stosować wykresy, jeśli to możliwe - rozwiązanie staje się bardziej czytelne.
W niniejszym zadaniu wszystkie liczby są w jednostkach podanych w treści, dlatego pomijamy ich wypisywanie. 
Jeśli dystans jest w podany w jednostkach km a czas w h, to umawiamy się, że szybkości będą wyrażone w km/h i pomijamy w równaniach jednostki.
Od momentu mijania się dwóch samochodów, łącznie muszą przebyć cały dystans A-B, czyli:
Pamiętając, że S0B oraz S0A są to brakujące dystanse dla kierowców A i B do dotarcia do odpowiednio B i A w chwili mijania, możemy napisać: Odpowiedź: Samochód osobowy dogoni samochód ciężarowy po 7,5 sekundach ruchu.', '1', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('2', '2018-01-07 14:02:44', '2018-01-07 14:02:44', 'Przypadku pomocny będzie wykres drogi od czasu. Radzimy, aby przy rozwiązywaniu zadań, stosować wykresy, jeśli to możliwe - rozwiązanie staje się bardziej czytelne.
W niniejszym zadaniu wszystkie liczby są w jednostkach podanych w treści, dlatego pomijamy ich wypisywanie. 
Jeśli dystans jest w podany w jednostkach km a czas w h, to umawiamy się, że szybkości będą wyrażone w km/h i pomijamy w równaniach jednostki.
Od momentu mijania się dwóch samochodów, łącznie muszą przebyć cały dystans A-B, czyli:
Pamiętając, że S0B oraz S0A są to brakujące dystanse dla kierowców A i B do dotarcia do odpowiednio B i A w chwili mijania, możemy napisać: Odpowiedź: Samochód osobowy dogoni samochód ciężarowy po 7,5 sekundach ruchu.', '1', '4');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('3', '2018-01-07 18:12:23', '2018-01-07 18:12:23', 'Wykres drogi od czasu. Radzimy, aby przy rozwiązywaniu zadań, stosować wykresy, jeśli to możliwe - rozwiązanie staje się bardziej czytelne.
W niniejszym zadaniu wszystkie liczby są w jednostkach podanych w treści, dlatego pomijamy ich wypisywanie. 
Jeśli dystans jest w podany w jednostkach km a czas w h, to umawiamy się, że szybkości będą wyrażone w km/h i pomijamy w równaniach jednostki.
Od momentu mijania się dwóch samochodów, łącznie muszą przebyć cały dystans A-B, czyli:
Pamiętając, że S0B oraz S0A są to brakujące dystanse dla kierowców A i B do dotarcia do odpowiednio B i A w chwili mijania, możemy napisać: Odpowiedź: Samochód osobowy dogoni samochód ciężarowy po 7,5 sekundach ruchu.', '1', '5');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('2', 'PAST SIMPLE', 'Last week we  sell our old car and  buy a new one.\n1. I  see Robert a couple of days ago and he  look well.\n2. He  want to help me but he  not/know how and he actually  hurt me.\n3. Why  you/let her go? You really  let me down.\n4. Mary  break her arm and  twist her ankle in the accident last night.\n5. As soon as Kate  come out of the house, Joe  run into her room,  sit on the chair,  open a drawer and  take out all her letters.\nThey  be busy working so we  not/disturb them.\n6. She  not/behave normally yesterday so I  ask her if she  feel all right but she  not/answer.\n7. When  you/make that decision and why  you/choose the second option?\n8. She  not/shut the door quietly but  slam it', '5');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('4', '2018-01-01 22:15:45', '2018-01-07 22:15:45','I  see Robert a couple of days ago and he  look well. n2. He  want to help me but he  not know how and he actually  hurt me. n3. Why  you let her go? You really  let me down. n4. Mary  break her arm and  twist her ankle in the accident last night. n5. As soon as Kate  come out of the house, Joe  run into her room,  sit on the chair,  open a drawer and  take out all her letters. nThey  be busy working so we  not disturb them. n6. She  not behave normally yesterday so I  ask her if she  feel all right but she  not answer. n7. When  you make that decision and why  you choose the second option? n8. She  not shut the door quietly but  slam it ', '2', '5');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('5', '2017-12-07 10:33:21', '2017-12-07 10:33:21','This is my answer: I  see Robert a couple of days ago and he  look well. n2. He  want to help me but he  not know how and he actually  hurt me. n3. Why  you let her go? You really  let me down. n4. Mary  break her arm and  twist her ankle in the accident last night. n5. As soon as Kate  come out of the house, Joe  run into her room,  sit on the chair,  open a drawer and  take out all her letters. nThey  be busy working so we  not disturb them. n6. She  not behave normally yesterday so I  ask her if she  feel all right but she  not answer. n7. When  you make that decision and why  you choose the second option? n8. She  not shut the door quietly but  slam it ', '2', '6');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('3', 'Zadanie psychotechniczne', 'Zadanie poniższe powinno być rozwiązane w przeciągu 15 minut. Proponujemy przygotować papier i ołówek, położyć zegarek na stole, uważnie przeczytać treść zadania i ... rozwikłać zagmatwany problem. A oto jego treść:
     Obsługa samolotu pasażerskiego składa się z trzech osób: pilota, nawigatora i stewardessy. Nazwiska ich (kolejność jak to z dalszej treści wynika jest obojętną) brzmią: Góra, Ptak i Wróbel. Ptak zarabia 2000 zł, Góra mieszka w Warszawie, a w Aninie - pasażer o nazwisku stewardessy. Na połowie drogi między Warszawą i Aninem mieszka stewardessa. Najbliższy sąsiad stewardessy, pasażer, zarabia prawie dwa razy tyle co stewardessa. Wróbel przed odlotem wygrał u pilota w warcaby 30 złotych.

     Pytanie: Jak nazywa się nawigator, jeżeli stewardessa zarabia 2 tysiące złotych?

     Zadanie to nie jest pozbawione sensu, jakby się to na pierwszy rzut oka wydawało. Jest ono zbudowane najzupełniej prawidłowo z punktu widzenia logiki. Przekonacie się o tym zresztą sami po 15 minutach, a jeżeli po upływie tego czasu nie rozwiążecie go - uznajcie się za pokonanych i zajrzyjcie do rozwiązania', '4');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('6', '2017-12-15 16:43:22', '2017-12-15 16:43:22','Nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '6');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('7', '2017-12-16 16:43:22', '2017-12-16 16:43:22','Nie Ptak wygrał u pilota - tym samym nie może już być pilotem, jest więc nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '2');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('8', '2017-12-21 16:43:22', '2017-12-21 16:43:22','Ponieważ pilota - tym samym nie może już być pilotem, jest więc nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('9', '2017-12-24 16:43:22', '2017-12-24 16:43:22','U pilota - tym samym nie może już być pilotem, jest więc nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '5');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('10', '2017-12-30 16:43:22', '2017-12-30 16:43:22','Tylko i jednak Wróbel wygrał u pilota - tym samym nie może już być pilotem, jest więc nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '4');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('11', '2017-12-01 16:43:22', '2017-12-01 16:43:22','Pilotem, jest więc nawigatorem. Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '3', '6');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('4', 'Żarówka', 'Mamy dwa pomieszczenia połączone dość długim korytarzem. Oba pomieszczenia są niewielkie - nie można się nawet wyprostować. Znajdujemy się w pierwszym pomieszczeniu, w którym są trzy włączniki. W drugim pomieszczeniu zwisa z sufitu żarówka. Możemy przejść z pierwszego pomieszczenia do drugiego, ale wrócić się nie możemy (np. drzwi się zamykają po przejściu i już nie otwierają).', '4');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('12', '2017-01-05 12:51:22', '2017-01-05 12:51:22','Wobec tego, że Ptak zarabia 2000 złotych (tyle, ile zarabia stewardessa), więc nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '4', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('13', '2017-01-05 15:08:22', '2017-01-05 15:08:22','Nie może być najbliższym jego sąsiadem. Ptak musi przeto mieszkać w Aninie. Tam zaś mieszka pasażer o nazwisku stewardessy, czyli stewardessą jest Ptak. Stąd więc nawigatorem jest Wróbel, a pilotem Góra. Logiczne, prawda?', '4', '5');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('5', 'Ludożercy i misjonarze', 'Trzech misjonarzy podróżuje po Afryce w towarzystwie trzech ludożerców. Ludożercy oczywiście już nie są ludożercami, ale przestali nimi być stosunkowa niedawno.
     Całe towarzystwo przybywa nad brzeg rzeki. Mają ze sobą jedynie niewielką nadmuchiwaną łódkę, która mieści dwie osoby, i jedno wiosło. Wszyscy trzej misjonarze i jeden ludożerca potrafią wiosłować. Misjonarze zdają sobie sprawę, że nie wolno zostawić na żadnym brzegu jednocześnie więcej ludożerców niż misjonarzy, bo to się rnoże źle skończyć.

     Pytanie: W jaki sposób udało się misjonarzom przeprawić przez rzekę nie narażając się na zjedzenie?

     Do rozwiązywania najlepiej jest używać zapałek. Trzy zapałki z główkami - to misjonarze, dwie połówki bez główek - to ludożercy i wreszcie połówka z główką - wiosłujący ludożerca.', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('14', '2017-01-10 11:08:22', '2017-01-10 11:08:22','Wiosłujący ludożerca zabiera na drugi brzeg drugiego ludożercę i wraca po trzeciego. Teraz dwóch ludożerców znajduje się na drugim brzegu rzeki. Z kolei wiosłujący ludożerca powraca i wysiada na tym brzegu. Dwóch misjonarzy wsiada do łódki i przejeżdża na drugą stronę rzeki. Jeden zostaje tam, a drugi powraca z jednym ludożercą. Następnie jeden misjonarz zabiera na drugi brzeg ludożercę', '5', '4');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('15', '2017-01-10 12:28:22', '2017-01-10 12:28:22','Dwóch ludożerców znajduje się na drugim brzegu rzeki. Z kolei wiosłujący ludożerca powraca i wysiada na tym brzegu. Dwóch misjonarzy wsiada do łódki i przejeżdża na drugą stronę rzeki. Jeden zostaje tam, a drugi powraca z jednym ludożercą. Następnie jeden misjonarz zabiera na drugi brzeg ludożercę', '5', '6');

-- exercise folllowed by its solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('6', 'Jak podzielić?', 'Pewien ojciec sześciu synów dał z okazji Nowego Roku najstarszemu synowi 60 zł i rzekł:
     - Rozdziel je pomiędzy braci w ten sposób, by każdy starszy miał od młodszego o złotówkę więcej.

     Pytanie: W jaki sposób najstarszy syn rozdzielił powyższą sumę, by wypełnić dokładnie życzenie ojca, a nie dokonywać trudnych rachunków?', '3');

INSERT INTO `school`.`solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `user_id`) 
VALUES ('16', '2017-01-02 14:28:22', '2017-01-02 14:28:22','synów dał z okazji  synowi 60 zł i rzekł cokolwiek jako odpowiedz', '6', '6');

-- exercise without solutions

INSERT INTO `school`.`exercise` (`id`, `title`, `description`, `user_id`) 
VALUES ('7', 'Dlaczego zabiła?', 'Historia pewnej dziewczyny. Na pogrzebie swojej matki spotyka chłopaka którego nigdy
wcześniej nie widziała. Nieoczekiwanie dostrzega w nim mężczyznę swojego życia i
zakochuje się w nim. Kilka dni później dziewczyna zabija swoją siostrę.
Jaki jest powód zabicia siostry?

Podpowiedź znajdziesz poniżej. Zanim zajrzysz pomyśl...', '2');



