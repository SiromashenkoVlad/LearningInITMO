mkdir -p grovyle/lotad
cat > sceptile.txt << EOF
Тип травяной
Способности Overgrow Unburden
Умения Leaf Storm Dragon Pulse Focus Blast
EOF
cd grovyle
cat > nuzleaf.txt << EOF
Тип травяной/тёмный
Способности Chlorophyll Early Bird Pickpocket
Эволюция Seedot -> Nuzleaf -> Shiftry
EOF
chmod 640 nuzleaf.txt
chmod u-w,g-r,o-r,o+w lotad/
cd ..
chmod g=wx,o=r grovyle/
chmod 214 sceptile.txt