green='\033[0;32m';
yellow='\033[0;33m';
red='\033[0;31m';
reset='\033[0m';

echo -e "${yellow}Please wait, it may take a few minutes...\n${reset}";

BASEDIR=$(dirname $0);

echo -e "${yellow}Running mvn clean install -DskipTests --quiet\n${reset}";
mvn clean install -DskipTests --quiet;
echo -e "${yellow}Running mvn eclipse:eclipse --quiet\n${reset}";
mvn eclipse:eclipse -Dwtpversion=2.0 --quiet;
cd config/schema;
echo -e "${yellow}Updating database (config/recreateSchema.sh)\n${reset}";
./recreateSchema.sh;
cd ../..;
echo -e "${green}Done!${reset}";

