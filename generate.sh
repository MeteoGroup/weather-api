curl -s "https://raw.githubusercontent.com/MeteoGroup/weather-api/master/WEATHER-API-BLUEPRINT.md" > API.md
aglio --theme-template ./template/index.jade --theme-style ./template/layout-default.less --theme-style ./template/meteogroup.less -i API.md -o index.html
rm API.md