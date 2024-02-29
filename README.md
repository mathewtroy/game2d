# :label: Semester project from the subject Programming in JAVA  (PJV)

**Code**: B0B36PJV <br>
**Name**: Programming in Java/ Programování v JAVA <br>
**Faculty**: Faculty of Electrical Engineering/ Fakulta elektrotechnická <br>
**Department**: 13136 - Department of Computer Science/ Katedra počítačů <br>


## :label: Assignment
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

# :label: Launch

**1.Download the source code of the application from the repository:** <br>
`git clone https://gitlab.fel.cvut.cz/krossale/java-pro.git`<br>

**2.Run a command in IntelliJ IDEA to build and package the project:** <br>
`mvn clean install`<br>

**3.Run Main.java:** <br>
`run java main`

## :label: Game controls


👨               main hero in the game <br>
:older_man:	            old man <br>
🧑               merchant <br>
🚂               tank <br>
👻               ghost <br>


⬆️ 		         go UP **button W** <br>
⬇️	             go DOWN **button S** <br>
➡️	             go LEFT **button A** <br>
⬅️		         go RIGHT **button D** <br>

🆗	    	     action **button ENTER** <br>
🏹	             shooting **button F** <br>

🗺️               open Map **button M**<br>
❌               hide mini Map **button Q**<br>


⏯️	             pause option **button P** <br>
⚙️               settings **button R**<br>
👜  		     inventory screen **button C** <br>

❤️	             hit points (HP) <br>
🔷	             mana points (MP) <br>
💊               first aid (recover HP) <br>
📦               ammunition (recover MP)<br>


🏏      	     ram item (cut trees)<br>
⚔               spike item (default weapon)<br>
🛡️               helmet item (default defense)<br>
🗝️	              key item (open the door)<br>
💰	             coin item (time is money)<br>
👢               boot item (increase speed)<br>

## Game plot

<details><summary> Show more </summary>

The goal of the game is for the player to collect keys and open the chest. To do this, he first needs to find a ram. Then he will be able to cut down the yellow trees. <br>

In the center of the map there is a teleport to the island. This teleport is hidden behind yellow trees. <br>

The hero needs to move to the island, where he will find prizes and a new teleport to the location FEL (map number 2). <br>

At the FEL, the main character will be able to change the location and get into the PJV office, where one of the keys is hidden. Also in PJV's office there will be a teleport to the new location Gold (map number 3).<br>

In this location, the player needs to fight 3 strong ghosts, after which he will be able to get the second key.<br>

There will also be gold as a reward. After that, the hero should return to the main map. <br>

The hero will open two doors. Then he will be able to find a third key, with which he will open the last door to the treasure. <br>


After the player opens the chest, they can continue to fight the enemies because the world is in danger. And only our hero can save the world.

</details>





## :label: Experience gained during Semester project

<details><summary> Show more </summary>

While working on a semester project, I gained a lot of valuable experience in the field of Java game development. Here are a few things I learned: <br>

**Working with external files:** I had to learn how to load data from files and save them back. This is a key skill for dynamically loading levels and saving game state. <br>

**Battle system:** Implementing the way to fight enemies was a challenging but interesting part of the project. I had to create a mechanism to handle player and enemy attacks, monitor health status and decide the outcome of the fight. <br>

**Interaction with objects:** The player could use various items in the inventory to interact with the game world. This included detecting whether the player has the correct item to open a door or fight an enemy. There was a lot to test and debug. <br>

**Graphical user interface (GUI):** I created a GUI for the game that included the inventory, player stats, and combat interface. This was important for the playability of the game.

</details>


## :label: Wiki
**[Go to Wiki](https://gitlab.fel.cvut.cz/krossale/java-pro/-/wikis/home)**

## :label: Documents
**[Go to Docs](https://docs.google.com/document/d/1_nWswo61mqfpcLLpSvPrqq7BsWZOZbq6HDDdP99xWVw/)**

## :label: Contacts

**Teacher:** Ladislav Serédi <br>
**Author:** Aleksandr Kross  <br>
:email: **[If you have questions text me](mailto:krossale@fel.czut.cz)**



