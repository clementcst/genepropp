@echo off
setlocal

set "emplacement=%~dp0"
set "url=http://localhost:4200/"

rem BACK : Ouvrir une nouvelle instance de cmd à gauche et exécuter la commande clean puis bootJar puis lauch le .jar
start "CMD_SERVER_BACK" /D "%emplacement%genealogic-tree-back" cmd /c "title SERVER_BACK && mode con: cols=100 lines=40 && java -jar build\libs\genealogic-tree-back-1.0.0.jar --spring.profiles.active=production"

rem FRONT : Ouvrir une nouvelle instance de cmd à droite et exécuter les commandes d'installation uniquement si les packages ne sont pas déjà installés puis ouvrir la page du site
start "CMD_SERVER_FRONT" /D "%emplacement%genealogic-tree-front" cmd /c "title SERVER_FRONT && mode con: cols=100 lines=40 && ng serve"

rem Attendre que le serveur soit prêt avant d'ouvrir le navigateur
:waitLoop
curl --fail -s -o nul %url%
if %errorlevel% neq 0 (
    timeout /t 1 > nul
    goto waitLoop
)

rem Ouvrir le navigateur par défaut à l'URL http://localhost:4200/
start %url%