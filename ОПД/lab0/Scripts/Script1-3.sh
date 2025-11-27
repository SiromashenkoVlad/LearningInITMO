#!/usr/bin/bash



touch aipom5.txt
echo "Тип диеты Omnivore" > aipom5.txt
mkdir blissey1
mkdir bagon0
mkdir krabby9
touch musharna9.txt
touch seaking1.txt
echo "Возможности Overland=5 Surface=3 Sky=7 Jump=3" >> musharna9.txt
echo "Power3=0 Intelligence=6 Dream Smoke=0" >> musharna9.txt
echo "Telekinetic=0" >> musharna9.txt
echo "Способности Supersonic Horn Attack Water" >> seaking1.txt
echo "Pulse Flail Aqua Ring Fury Attack Waterfall Horn Drill Aqility Soak" >> seaking1.txt
echo "Megahorn" >> seaking1.txt
cd bagon0
echo "Тип диеты" >> tympole.txt
echo "Herbivore" >> tympole.txt
mkdir deerling
echo "satk=6 sdef=6 spd=4" >> piplup.txt
echo "Тип диеты" >> spinarak.txt
echo "Carnivore" >> spinarak.txt
cd ..
cd blissey1
mkdir pupitar
mkdir smoochum
mkdir meganium
mkdir chingling
echo "Способности Bind Harden Mud Sport Tackle Curse Rock" >> onix.txt
echo "Throw Rage Rock Tomb Stealth Rock Rock Polish Smack Down Dragonbreath" >> onix.txt
echo "Slam Screen Rock Slide Sand Tomb Iron Tail Dig Stone Edge Double-Edge" >> onix.txt
echo "Sandstorm" >> onix.txt

touch salamence.txt
echo "Ходы Air Cutter Aqua Tail Body Slam Defense Curl" >> salamence.txt
echo "Defog Draco Meteor Dragon Pulse Fire Fang Fury Cutter Heat Wave Hyper" >> salamence.txt
echo "Voice Iron Tail Mud-Slap Ominous Wind Outrage Rollout Roost Sleep Talk" >> salamence.txt
echo "Snore Steel Wing Swift Tailwood Thunder Fang Twister Zen" >> salamence.txt
echo "Headbutt" >> salamence.txt
cd ..

cd krabby9
echo "Тип диеты Omnivore" >> anorith.txt
echo "Тип диеты" >> magnezone.txt
echo "Ergovore" >> magnezone.txt
echo "Тип покемона GRASS NONE" >> tangela.txt
echo "Способности Leer" >> snorunt.txt
echo "Powder Snow Double Team Bite Icy Wind Headbutt Protect Ice Fang Crunch" >> snorunt.txt
echo "Ice Shard Hail Blizzard" >> snorunt.txt
echo "Тип покемона ICE" >> cubchoo.txt
echo "NONE" >> cubchoo.txt
cd ..


#script2
chmod u-rw aipom5.txt
chmod g-r aipom5.txt
chmod 764 bagon0
chmod 573 blissey1
chmod 751 krabby9
chmod u-rw musharna9.txt
chmod g+w musharna9.txt
chmod o-r musharna9.txt
chmod o+w musharna9.txt
chmod 400 seaking1.txt
cd bagon0
chmod u=,g=rw,o= tympole.txt
chmod u=rwx,g=rx,o=wx deerling
chmod u=rw,g=w,o= piplup.txt
chmod u=,g=r,o=r spinarak.txt
cd ..

cd blissey1
chmod 312 pupitar
chmod 736 smoochum
chmod 305 meganium
chmod 600 onix.txt
chmod 060 salamence.txt
chmod 550 chingling
cd ..

cd krabby9
chmod 044 anorith.txt
chmod 620 magnezone.txt
chmod 400 tangela.txt
chmod 044 snorunt.txt
chmod 664 cubchoo.txt
cd ..

#script3
cd bagon0
chmod 400 spinarak.txt
cd ..
cat bagon0/spinarak.txt blissey1/onix.txt > seaking1_76.txt
cd bagon0
chmod u=,g=r,o=r spinarak.txt
cd ..

ln -s musharna9.txt krabby9/snoruntmusharna.txt
ln seaking1.txt krabby9/tangelaseaking.txt

chmod 400 aipom5.txt
chmod 100 blissey1
cp aipom5.txt blissey1/meganium/
chmod 573 blissey1
chmod 004 aipom5.txt

ln -s blissey1 Copy_8.txt

chmod 400 aipom5.txt
cp aipom5.txt krabby9/anorithaipom.txt
chmod 004 aipom5.txt

cd blissey1
chmod 600 salamence.txt
chmod 700 meganium
chmod 700 pupitar
cd ..
cp -r blissey1 blissey1/meganium/
cd blissey1
chmod 060 salamence.txt
chmod 305 meganium
chmod 312 pupitar
cd ..