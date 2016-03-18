curl -s "https://raw.githubusercontent.com/MeteoGroup/weather-api/master/README.md" > README.md
aglio --theme-template ./template/index.jade --theme-style ./template/layout-default.less --theme-style ./template/meteogroup.less -i README.md -o index.html
rm README.md