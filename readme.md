# Pour commit depuis git : 

## Clone le repo Github

```bash
git clone https://github.com/clementcst/J2E_Genealogy-Tree.git
```
Normalement, vous serez invités à lier votre git avec votre compte Github

## Créer une branche

```bash
git checkout -b mybranch
```


Pour visualiser les branches existentes :
```bash
git branch
```

Pour switcher entre les branches :
```bash
git checkout nom_de_la_branche_ou_je_veux_aller
```

<sub>Les branches dans votre git local ne sont pas les mêmes que ceux dans le repository Github</sub>

## Push les modifications vers le repo Github

Ajouter les fichiers au tracker git : 
```bash
git add mon_nouveau_fichier # Mettre * pour add tous les fichiers untracked
```

Le status de votre git local peut être vu avec 
```bash
git status
```

Commit tous les fichiers add, le commit sera effectué sur votre branch local
```bash
git commit mon_nouveau_fichier # Mettre * pour commit tous les fichiers untracked
```

Push votre branch sur le repo Github
```
git push --set-upstream origin nom_de_la_branche_a_crée/modifié_sur_Github
```

## Créer la pull request

La nom_de_la_branche_a_crée/modifié_sur_Github a été crée/modifié sur Github et est maintenant en avance par rapport au main (ou autre branche). On peut demander un tirage de notre nouvelle branche vers la branche main (avec potentiellement des parametres à mettre, surtout pour les branches secondaires).
