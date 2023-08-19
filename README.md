# :label: Semestrální projekt z předmětu Programování v JAVA  (PJV)

**Code**: B0B36PJV <br>
**Name**: Programming in Java/ Programování v JAVA <br>
**Faculty**: Faculty of Electrical Engineering/ Fakulta elektrotechnická <br>
**Department**: 13136 - Department of Computer Science/ Katedra počítačů <br>


## :label: Zadani
<details><summary> Show more </summary>
Typická RPG hra, která je tímto tématem zamýšlena je například tato https://darkwalllke.itch.io/simple-rpg

Nezapomínejte, že vaším cílem není vytvořit hru jako takovou (hodně levelů, příběh atd.), ale engine na spuštění a odehrání levelů popsaných externími soubory.
Hra bude umět načítat seznam předmětů ze souboru. Tyto předměty bude mít hráč na začátku hry. Na konci hry bude umět hra uložit seznam předmětů ve stejném formátu.
Každý level bude popsaný v externím souboru v rozumném formátu – je na vás jaký formát si zvolíte. Pro demonstraci stačí vytvořet jeden až dva levely hry na kterých bude předvedena funkčnost všech prvků, inventáře a boje s nepřítelem.
Pokud soubory s levely nejsou human-readable, musí být vytvořen editor těchto souborů.
V rámci hry bude implementován způsob souboje s příšerami.
Hrdina bude umět pomocí sebraných předmětů interagovat s dalšími předměty (otevře dveře klíčem, rozbije truhlu palicí atd.).
Herní engine musí být vybaven GUI.

</details>

## :label: Game controls


:man:               main hero in the game <br>
:older_man:	        old man <br>
:mage:              merchant <br>
<br>

:baby: 	🧒 	:child: 	top
top 	👦 	:boy: 	👧 	:girl: 	top
top 	🧑 	:adult: 	👱 	:blond_haired_person: 	top
top 	👨 	:man: 	🧔 	:bearded_person: 	top
top 	🧔‍♂️ 	:man_beard: 	🧔‍♀️ 	:woman_beard: 	top
top 	👨‍🦰 	:red_haired_man: 	👨‍🦱 	:curly_haired_man: 	top
top 	👨‍🦳 	:white_haired_man: 	👨‍🦲 	:bald_man: 	top
top 	👩 	:woman: 	👩‍🦰 	:red_haired_woman: 	top
top 	🧑‍🦰 	:person_red_hair: 	👩‍🦱 	:curly_haired_woman:
<br>
:space_invader:	    monster <br>
:ghost:             ghost <br>


:arrow_up:	        go UP **button W** <br>
:arrow_down:	    go DOWN **button S** <br>
:arrow_left:	    go LEFT **button A** <br>
:arrow_right:		go RIGHT **button D** <br>

:ok:	    	    action **button ENTER** <br>
:gun:	            shooting **button F** <br>

:world_map:         open Map **button M**<br>
:x:                 hide mini Map **button Q**<br>


:pause_button:	    pause option **button P** <br>
:gear:              settings **button R**<br>
:handbag:		    inventory screen **button C** <br>

:heart:	            hit points (HP) <br>
:large_blue_diamond:	mana points (MP) <br>
:red_circle:        red potion (recover HP) <br>
:large_blue_circle: blue potion (recover MP)<br>


:hammer:	        axe item (cut trees)<br>
:dagger:	        sword item (default weapon)<br>
:shield:            shield item (default defense)<br>
:key:	            key item (open the door)<br>
:moneybag:	        coin item (time is money)<br>
:boot:              boot item (increase speed)<br>


## Game plot

<details><summary> Show more </summary>

The goal of the game is for the player to collect keys and open the chest. To do this, he first needs to find an axe. Then he will be able to cut down the yellow trees. <br>

In the center of the map there is a teleport to the island. This teleport is hidden behind yellow trees. <br>

The hero needs to move to the island, where he will find prizes and a new teleport to the location FEL (map number 2). <br>

At the FEL, the main character will be able to change the location and get into the PJV office, where one of the keys is hidden. Also in PJV's office there will be a teleport to the new location Gold (map number 3).<br>

In this location, the player needs to fight 3 strong ghosts, after which he will be able to get the second key.<br>

There will also be gold as a reward. After that, the hero should return to the main map. <br>

The hero will open two doors. Then he will be able to find a third key, with which he will open the last door to the treasure. <br>


After the player opens the chest, they can continue to fight the monsters because the world is in danger. And only our hero can save the world.

</details>





## :label: Zkušenosti získané během SP

<details><summary> Show more </summary>

Describe my experience.. Bla bla bla.

</details>


## :label: Dokumentace
## NEED TO ADD


## :label: Contacts

**Cvičící:** Ladislav Serédi <br>
**Zpracoval:** Aleksandr Kross  <br>
:email: [If you have questions text me](mailto:krossale@fel.czut.cz)



