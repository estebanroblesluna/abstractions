green='\033[0;32m';
yellow='\033[0;33m';
red='\033[0;31m';
reset='\033[0m';

echo -e "${yellow}Please wait, it may take a few minutes...${reset}";
files/recreateFiles.sh;
schema/recreateSchema.sh;
echo -e "${green}Done!${reset}";