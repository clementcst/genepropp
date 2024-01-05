import { Component } from '@angular/core';
import FamilyTree from "@balkangraph/familytree.js";
import { TreeService } from '../../../services/tree/tree.service';
import { UserService } from '../../../services/user/user.service';
import { CookieService } from 'ngx-cookie-service';
import { MatDialog } from '@angular/material/dialog';
import { NodeCreationRulesComponent } from '../../PopUps/node-creation-rules/node-creation-rules.component'
import { ErrorMergeTreePopupComponent } from '../../PopUps/error-merge-tree-popup/error-merge-tree-popup.component'
import { SpecialSuccessPopupComponent } from '../../PopUps/special-success-popup/special-success-popup.component' 

import { ActivatedRoute } from '@angular/router';

import { LinkedHashMap } from '../linked-hashmap/linked-hashmap.component';


@Component({
  selector: 'app-tree-content',
  templateUrl: './tree-content.component.html',
  styleUrls: ['./tree-content.component.css']
})


export class TreeContentComponent {
  TempTreeFromDB: any ={};
  treeFromDB: any[] = [];

  tabForSaveUnknowID: any[] = [];
  errorMessages: string[] = [];

  treeTab: any[] = [];
  treeTabCopy: any[] = [];
  tempTreeTab: any[] = [];
  treeMergeForDB: any[] = [];

  isMyTree: boolean = true;

  linkedHashMap = new LinkedHashMap<number, string>();
  loading: boolean = false;
  isReadyToDisplay: boolean = false;

  TempUserData: any ={};
  myIDNode: any = {};
  myParam: string = '';
  partnerTab: any[] = [];
  myID = this.cookieService.get('userId')
  myTreeId: any = {};
  InfoChangementTab: object[] = [];
  

  constructor(private route: ActivatedRoute, private treeService : TreeService,private userService : UserService, private cookieService: CookieService, public dialog: MatDialog ) 
  { 
    this.treeService = treeService;
    this.userService = userService;
    this.cookieService = cookieService;
    

  }

  openNodeCreationRulesPopup() {
    const dialogRef = this.dialog.open(NodeCreationRulesComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }


  openErrorMergeTreePopupComponent(errorMessage: any) {
    const dialogRef = this.dialog.open(ErrorMergeTreePopupComponent, {
      data: { data: errorMessage },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  openSpecialSuccessPopupComponent(response: any) {
    const dialogRef = this.dialog.open(SpecialSuccessPopupComponent, {
      //@ts-ignore
      data: { data: response },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  //****************************************************************************************************
  // PERSONNALS FUNCTIONS 
  //****************************************************************************************************
  
  
  IDlist(tableau1: any[], tableau2: any[]): void {
   // console.log("id dans le tableau de la db")
    tableau1.forEach((node) => {
      //console.log(node.id)
    });
   // console.log("id dans le tableau de le merge tab")
    tableau2.forEach((node) => {
     // console.log(node.id)
    });
  }

  trouverIdsManquants(tableau1: any[], tableau2: any[]): void {
    // Extraire les IDs de chaque tableau
    const idsTableau1 = tableau1.map(item => item.id);
    const idsTableau2 = tableau2.map(item => item.id);

    // Trouver les IDs qui sont dans le tableau1 mais pas dans le tableau2
    const idsManquants = idsTableau1.filter(id => !idsTableau2.includes(id));

    idsManquants.forEach(idManquant => {
      this.linkedHashMap.putOrAdd(idManquant, "DELETE");
      const indexElementCorrespondant = this.treeMergeForDB.findIndex(item => item.id === idManquant);
    // Vérifier si l'élément a été trouvé
      if (indexElementCorrespondant !== -1) {
        // Supprimer l'élément du tableau this.treemergefromDB
        this.treeMergeForDB.splice(indexElementCorrespondant, 1);
      }
    });
  }


  prependNumberToArray(number: number, array: number[] | null): number[] {
    return array ? [number, ...array] : [number];
  }

  transformToPartnerArrays(pids: any[], divorced: any[] | null): { partner: any, exPartnerIds: any[] | null} {

    if (pids == null || pids.length == 0) {
      return { partner: null, exPartnerIds: null };
    }

    if (divorced == null) {
      return { partner: pids[0], exPartnerIds: null };
    }

    let partner: any | null = null;
    let exPartnerIds: any[] | null = [];

    for (const pid of pids) {
      if (!divorced.includes(pid)) {
        if (partner == null) {
          partner = pid;
        } else {
          exPartnerIds.push(pid);
        }
      } else {
        exPartnerIds.push(pid);
      }
    }
    return { partner, exPartnerIds };
  }

  updateTempTreeTabForMerge(Tab: any[]): any[] {
    // Créer une copie indépendante du tableau d'origine
    const tabCopy = this.deepCopy(Tab);
  
    tabCopy.forEach((node) => {
      node.parent1Id = node.mid;
      node.parent2Id = node.fid;

  
      const result = this.transformToPartnerArrays(node.pids, node.divorced);
      node.partnerId = result.partner
      node.exPartnersId = result.exPartnerIds
  
      delete node.fid;
      delete node.mid;
      delete node.pids;
      delete node.tags;
      delete node.divorced;
      delete node.fullName;
    });
  
    return tabCopy;
  }
  
  // Ajouter une méthode pour la copie profonde
  deepCopy(obj: any[]): any[] {
    return JSON.parse(JSON.stringify(obj));
  }

  isEqual<T>(a: T, b: T): boolean {
    if (typeof a !== 'object' || a === null || typeof b !== 'object' || b === null) {
      return a === b;
    }

    const keysA = Object.keys(a);
    const keysB = Object.keys(b);

    if (keysA.length !== keysB.length) {
      return false;
    }

    for (const key of keysA) {
      if (!this.isEqual((a as any)[key], (b as any)[key])) {
        return false;
      }
    }

    return true;
  }

  convertToString(array: number[]): string {
    return array.join(',');
}

  updateDatabaseData(treeFromDB: any[], treeTab: any[]): any[] {
    const idMap = new Map(treeFromDB.map((item) => [item.id, item]));

    treeTab.forEach((createdItem) => {

      const TempLinkedHashMap = new LinkedHashMap<string, number>();
      const existingItem = idMap.get(createdItem.id);
  
      if (existingItem) {
        let ParentsKeyHaveChanged = false
        let KeyHaveChanged = false;
        let KeyNotDisplayChanges = false;
        Object.keys(createdItem).forEach((key) => {
            if(!this.isEqual(existingItem[key],createdItem[key])){
              KeyHaveChanged = true
              console.log("le param "+key+" de "+existingItem.id+" a changé !"+existingItem[key]+"-->"+createdItem[key])
              if(key == 'parent1Id' || key == 'parent2Id') { ParentsKeyHaveChanged = true; } 
              if(key == 'partnerId' || key == "exPartnerIds" ){ KeyNotDisplayChanges = true; }
              //console.log("le param "+key+" de "+existingItem.id+" a changé !"+existingItem[key]+"-->"+createdItem[key])
              if( key == "exPartnersId" || key == "treesId") {
                existingItem[key] = this.convertToString(createdItem[key] )
              }else{
                existingItem[key] = createdItem[key]
              }
            } 
        });
        if(KeyHaveChanged  && ParentsKeyHaveChanged) {this.linkedHashMap.putOrAdd(existingItem.id, "PARENT"); }
        //console.log("voici l'état des variable : "+KeyHaveChanged+"  "+ParentsKeyHaveChanged+"   "+KeyNotDisplayChanges)
        if(KeyHaveChanged  && ParentsKeyHaveChanged == false && KeyNotDisplayChanges == false )
        {
         
          this.linkedHashMap.putOrAdd(existingItem.id, "UPDATE");
        } 
        
      } else { //nouveau élement 

        if(createdItem.partnerId != null){
          this.linkedHashMap.putOrAdd(createdItem.id, "PARTNER");
        } 

        if(createdItem.exPartnersId != null){
          this.linkedHashMap.putOrAdd(createdItem.id, "PARTNER");
        } 

        if(createdItem.parent2Id != null){
          if(createdItem.parent2Id < 0){
            this.linkedHashMap.putOrAdd(createdItem.id, "PARENT");
          }else{
            this.linkedHashMap.putOrAdd(createdItem.id, "CHILD");
          }
        } 
        if(createdItem.parent1Id != null){
          if(createdItem.parent1Id < 0){
            this.linkedHashMap.putOrAdd(createdItem.id, "PARENT");
          }else{
            this.linkedHashMap.putOrAdd(createdItem.id, "CHILD");
           }
        }
        
        treeFromDB.push(createdItem)
      }

    });

    return treeFromDB;
  }
  
  GetFirst(treeTab: any[]): void {

    treeTab.forEach((node: any) => {
      Object.keys(node).forEach((key) => {

              if(Array.isArray(node[key])){
              
                const arrayValue = node[key]; // Récupérer le tableau
                const firstElement = arrayValue[0]; // Récupérer le premier élément du tableau
            
                // Vérifier si le premier élément est un nombre avant la conversion
                if (typeof firstElement === 'number') {
                    // Convertir le premier élément en nombre
                    const convertedNumber = Number(firstElement);
                    // Attribuer la valeur convertie à la clé dans l'objet
                    node[key] = convertedNumber;
                }
              }
      });
    });
  }

  fixInvalidIds(treeTab: any[]): void {
    const invalidIdPattern = /[^0-9]/; // Pattern pour vérifier si l'id est un entier

    let nextNegativeId = -10;
    let TempStringId = "";
  
    this.treeTabCopy.forEach((node: any) => {

      let NewId = nextNegativeId;
      // Vérifier l'id de chaque nœud
      if (invalidIdPattern.test(node.id)) {
        // Remplacer l'id invalide par un nouvel id négatif
        TempStringId = node.id;
        node.id = NewId;
        nextNegativeId--;


        // Mettre à jour tous les champs contenant l'id invalide dans tout le tableau
        this.treeTabCopy.forEach((node: any) => {
          
          if(node.mid == TempStringId){node.mid = NewId}

          if(node.fid == TempStringId){node.fid = NewId}

          node.pids.forEach((part: any, index: number) => {
            if (part == TempStringId) {

                node.pids[index] = NewId;
            }
        });
         
        });

      }
    });
  }
  


    getPartnerIds(partner: number, exPartner: number[]): number[] {

        this.partnerTab = [];

        if(partner){this.partnerTab.push(partner);}

        if(exPartner != null)
        {
            exPartner.forEach((num) => {
              this.partnerTab.push(num);
          });
        } 
        
        return this.partnerTab;
    }


    getTags(gender: number, id: String): String [] {

        if(id == this.myIDNode)
        {
            return ['red']
        }else
        {
            if(gender == 1)
            {
                return ['blue']
            }else{
                return ['purple']
            }
        }
    }

  //****************************************************************************************************
  // NG ONINIT PART
  //****************************************************************************************************

    ngOnInit() 

    {
      
      this.isReadyToDisplay = true;

      //affectation de la relatednode dans l'instance myNodeID + affectation de treeID
      this.userService.getUser(this.myID).subscribe((data) => {

        this.TempUserData = data.value;
        this.myIDNode = this.TempUserData.relatedNodeId;
        this.myTreeId = this.TempUserData.myTreeId

        
        this.route.queryParams.subscribe(params => {

          // Récupérez la valeur du paramètre 'myParam' de l'URL
          if(params['treeId']){
            this.myTreeId = params['treeId']
            this.isMyTree = false  
          }
        });

        const tree = document.getElementById('tree');
        if (tree) {
          let familyConfig: any = {

            showXScroll: FamilyTree.scroll.visible,
            showYScroll: FamilyTree.scroll.visible,
            mouseScrool: FamilyTree.action.zoom,
            nodeTreeMenu: false,
            nodeMenu: {
                details: { text: "Details" },
                edit: { text: "Edit" }
            },
            nodeBinding: {
            field_0: "fullName",
            img_0: "profilPictureUrl"
            },
            searchFiledsAbbreviation: {        
            },
            editForm: {
              titleBinding: "fullName",
              photoBinding: "profilPictureUrl",
              generateElementsFromFields: false,
              elements: [     
                  { type: 'textbox', label: 'FirstName', binding: 'firstName' },
                  { type: 'textbox', label: 'LastName', binding: 'lastName' },
                  { type: 'textbox', label: 'gender', binding: 'gender' },
                  { type: 'textbox', label: 'privacy', binding: 'privacy' },
                  { type: 'textbox', label: 'Photo Url', binding: 'profilPictureUrl' },
                  { type: 'date', label: 'Birth Date', binding: 'dateOfBirth' },
                  { type: 'date', label: 'Death Date', binding: 'dateOfDeath' },
                  { type: 'textbox', label: 'City of birth', binding: 'cityOfBirth' },
                  { type: 'textbox', label: 'Country of birth', binding: 'countryOfBirth' },
                  { type: 'textbox', label: 'adress', binding: 'adress' },
                  { type: 'textbox', label: 'postalCode', binding: 'postalCode' },
                  { type: 'textbox', label: 'nationality', binding: 'nationality' },

              ],
                buttons:  {
                    edit: {
                        icon: FamilyTree.icon.edit(24,24,'#fff'),
                        text: 'Edit',
                        hideIfEditMode: true,
                        hideIfDetailsMode: false
                    },
                    remove: {
                    icon: FamilyTree.icon.remove(24,24,'#fff'),
                    text: 'remove',
                    hideIfEditMode: true,
                    hideIfDetailsMode: false

                    },
                    pdf:null,
                    share:null
                }
            }  

        }
          if (!this.isMyTree) {
                  delete familyConfig['nodeTreeMenu'];
           }

            var family = new FamilyTree(tree,familyConfig);

            family.on('render-link', function (sender, args) {
              var cnodeData = family.get(args.cnode.id);
              var nodeData = family.get(args.node.id);
              // @ts-ignore
              if (cnodeData.divorced != undefined && nodeData.divorced != undefined &&
                // @ts-ignore
                  cnodeData.divorced.includes(args.node.id) && nodeData.divorced.includes(args.cnode.id)) {
                  args.html = args.html.replace("path", "path stroke-dasharray='3, 2'");
              }
          });

         
          
    
            //Chargement du tableau de l'arbre depuis base de donnée
            this.treeService.getTree(this.myTreeId).subscribe((data) => {

              this.TempTreeFromDB = data.value;

              //cas ou arbre est privé
              if(this.TempTreeFromDB.privacy == 1 && !this.isMyTree){
                this.openErrorMergeTreePopupComponent("This family tree is set to private. Click below to be redirected to the main menu.")
              }else{

                  this.treeFromDB =  this.TempTreeFromDB.nodes;

                  console.log("FROM DB");
                  console.log(this.treeFromDB)

                  this.createTabForSaveUnknowID(this.treeFromDB,this.getAllIds(this.treeFromDB))

                  //console.log(this.tabForSaveUnknowID)

                  //remplissage depuis la database
                  this.treeFromDB.forEach((node: any) => {
                      let tempNode = {
                          firstName: node.firstName,
                          lastName: node.lastName,
                          privacy: node.privacy == 0 ? 'private' : node.privacy == 2 ? 'public' : 'restricted',
                          fullName: node.firstName + " " + node.lastName + (node.isAUserNode ? " (user)" : ""),
                          id: node.id, 
                          pids:this.getPartnerIds(node.partnerId,node.exPartnersId),
                          divorced: node.exPartnersId,
                          tags: this.getTags(node.gender,node.id),
                          
                          gender : node.gender == 0 ? 'male' : node.gender == 1 ? 'female' : 'other',
                          profilPictureUrl: node.profilPictureUrl,
                          dateOfBirth : node.dateOfBirth,
                          dateOfDeath : node.dateOfDeath,

                          cityOfBirth: node.cityOfBirth,
                          countryOfBirth: node.countryOfBirth,

                          adress: node.adress,
                          postalCode: node.postalCode,
                          nationality: node.nationality,

                          mid: node.parent1Id == null ? null :  node.parent1Id,
                          fid: node.parent2Id == null ? null :  node.parent2Id,

                      };

                      this.treeTab.push(tempNode);   
                  });
                  //fin
                  if(!this.isMyTree){
                    this.treeService.addView(this.myTreeId).subscribe()
                  } 
                  family.load(this.treeTab);
                this.isReadyToDisplay = false;
                }
           });  
        }// fin if tree
      });
    }//fin ng oninit


      createTabForSaveUnknowID(tableauObjets: any[], tableauId: any[]) {
    
        tableauObjets.forEach(objet => {
          // Vérifiez le champ parent1Id
          objet.parent1Id = this.verifierRelation(objet.parent1Id, "parent1Id", objet.id,tableauId);
          // Vérifiez le champ parent2Id
          objet.parent2Id = this.verifierRelation(objet.parent2Id, "parent2Id", objet.id,tableauId );
          // Vérifiez le champ partnerId
          objet.partnerId =  this.verifierRelation(objet.partnerId, "partnerId",objet.id,tableauId );
    
          // Vérifiez le tableau exPartnersId[]
          if (Array.isArray(objet.exPartnersId)) {
            // @ts-ignore
            objet.exPartnersId.forEach((exPartnerId, index) => {
              objet.exPartnersId[index] = this.verifierRelation(exPartnerId, "exPartnersId", objet.id, tableauId);
            });
          }
        });
      }

      AddUnknowIdIfNoChanges(tableauObjets: any[]) {
        this.tabForSaveUnknowID.forEach(obj => {
          //console.log("obj id : "+obj.id+" obj relation : "+obj.relation+" obj valeur :"+obj.valeur)
          const objetTrouve = tableauObjets.find(temp => temp.id === obj.id.toString());
          //console.log(objetTrouve)

          if (objetTrouve[obj.relation]) {
            // Mettre à jour la propriété dans le premier tableau avec la valeur du deuxième tableau
            console.log("on met la jour la node "+obj.id+" la valeur de "+obj.relation+" a  "+obj.valeur)
            objetTrouve[obj.relation] = obj.valeur.toString();
         }
        });
      }
      

      getAllIds(tableauObjets: { id: number }[]): number[] {
        return tableauObjets.map(objet => objet.id);
      }
    
      verifierRelation(champ: number | null, nomRelation: string, idNode: number, tableauId: any[]): number | null {
    
        if (champ !== null && !tableauId.includes(champ)) {
          this.tabForSaveUnknowID.push({ id: idNode, relation: nomRelation, valeur: champ });
          //console.log("on push "+idNode+" relation "+nomRelation+" valeur"+champ);
          return null;
        }  
        return champ;
      }

    
    

    //fonction qui test les nodes pour Jordan + set les par défault, et remet en forme les champs pour le back
    validateNode(node: any): boolean {
      const requiredProperties = ['lastName', 'firstName', 'id', 'countryOfBirth', 'cityOfBirth', 'dateOfBirth'];
    
      // Vérifier si le nœud existe
      if (!node) {
        console.log("Error: Node is null");
        return false;
      }
    
      // Boucle à travers les propriétés requises
      
      for (const property of requiredProperties) {
        if (!node[property]) {
          const errorMessage = `Property ${property} is missing`;
          console.log(errorMessage);

          // Ajouter l'erreur à la liste des messages
          this.errorMessages.push(errorMessage);
        }
      }
      const errorMessagesElement = document.getElementById('errorMessages');
      
      if (errorMessagesElement && this.errorMessages.length > 0) {
        // Ajouter un message d'erreur pour chaque nœud manquant
        this.errorMessages.unshift(`<p>Error on node "${node.firstName}"</p>`);
      
        // Mettre à jour le contenu de l'élément d'erreur avec des messages formatés en HTML
        errorMessagesElement.innerHTML = this.errorMessages.map(message => `<div class="error-message">${message}</div>`).join('');
      
        // Indiquer qu'il y a eu une erreur
        return false;
      }
      

      switch(node.privacy){
        case 'private':
          node.privacy = 0;
        break;
        case 'restricted':
          node.privacy = 1;
        break;
          case 'public':
          node.privacy = 2;
        break;
        default:       
          node.privacy = 2;
        break;
      }

      switch(node.gender){
        case 'female':
          node.gender = 1;
        break;
        case 'male':
          node.gender = 0;
        break;
        case 'other':
          node.gender = 2;
        break;
        default:       
          node.gender = 2;
        break;
      }
    
      console.log("Node validated");
      return true;
    }

     convertToStrings(data: any[]): any[] {
      return data.map(obj => {
          const newObj = {};
          for (const key in obj) {
              if (obj.hasOwnProperty(key)) {
                if(obj[key] != null){
                  //@ts-ignore
                  newObj[key] = String(obj[key]);
                }else{
                  //@ts-ignore
                  newObj[key] = null;
                }
                  
              }
          }
          return newObj;
      });
  }

      saveTree(): boolean {

        console.log("on lance le save")
        const errorMessagesElement = document.getElementById('errorMessages');
        if(errorMessagesElement) errorMessagesElement.innerHTML = '';
        this.errorMessages = []

        let value = true;

        this.treeTabCopy = this.deepCopy(this.treeTab);


        this.treeTabCopy.forEach((node) => {
          if (!this.validateNode(node)) {
            value = false; // Sortir de la boucle forEach dès qu'une invalidité est détectée
          }
          
        });
      
        if (value){

          //change id + update les dependance dans l'abre + créé le tableau pour jordan 
          this.fixInvalidIds(this.treeTabCopy);

          //modifie les champs du tableau pour permettre un envoie simple à la db
          this.tempTreeTab = this.updateTempTreeTabForMerge(this.treeTabCopy);

          //fusionne les tableau pour générer celui comme a la reception depuis la db
          this.treeMergeForDB = this.updateDatabaseData(this.treeFromDB, this.tempTreeTab);

          //supprimer les élément supprimer dans l'interface
          this.trouverIdsManquants(this.treeFromDB, this.treeTabCopy);

          //change les tableau par leur premier élément dans les champs des nodes du tableau
          this.GetFirst(this.treeMergeForDB);

          this.treeMergeForDB = this.convertToStrings(this.treeMergeForDB);

          console.log("Remet les valeur qui on été retiré car proviennet d'un autre arbe de cette node");
          this.AddUnknowIdIfNoChanges(this.treeMergeForDB);

          console.log("Voici le tableau envoyé à la db");
          console.log(this.treeMergeForDB);

          console.log("Voici la linkedhasmap", JSON.stringify(this.linkedHashMap, null, 2));
          
          this.loading = true; // Afficher l'indicateur de chargement

          this.treeService.postTree(this.treeMergeForDB, this.linkedHashMap.getMap(), parseInt(this.myID), parseInt(this.myTreeId))
            .subscribe(
              response => {
                console.log("Réponse reçue avec succès :", response);
                // Autres actions après une réponse réussie, si nécessaire
                this.loading = false; // Cacher l'indicateur de chargement en cas de réponse

                // @ts-ignore
                if(!response.success){
                  // @ts-ignore
                  this.openErrorMergeTreePopupComponent(response.message)

                }else{
                  // @ts-ignore
                  if (response.value && response.value.hasOwnProperty("specialSuccess")) {
                       console.log("on est en spécial success")
                       // @ts-ignore
                      this.openSpecialSuccessPopupComponent(response)
                  }else{
                      location.reload();
                  }
                  
                }
                
              },
              error => {
                console.error("Erreur lors de la requête :", error);
                // Autres actions en cas d'erreur, si nécessaire
                this.loading = false; // Cacher l'indicateur de chargement en cas d'erreur           
                location.reload();
                //console.log("reload")
                
              }
            );
          }
        return value;
      }

}//fin classe



