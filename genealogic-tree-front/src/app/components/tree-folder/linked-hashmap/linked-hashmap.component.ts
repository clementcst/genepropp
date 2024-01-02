import { Component } from '@angular/core';

@Component({
  selector: 'app-linked-hashmap',
  templateUrl: './linked-hashmap.component.html',
  styleUrls: ['./linked-hashmap.component.css']
})
export class LinkedHashMap<K extends string | number, V> {

    private map: Record<string, V> = {};
    private keyList: K[] = [];
  
    putOrAdd(key: K, value: V): void {
      const keyString = key.toString();
      if (this.map.hasOwnProperty(keyString)) {
        // Si la clé existe, appeler put pour mettre à jour la valeur
        this.put(key, value);
      } else {
        // Si la clé n'existe pas, appeler create pour ajouter la clé et la valeur
        this.create(key, value);
      }
    }

    private create(key: K, value: V): void {
      const keyString = key.toString();
      this.keyList.push(key);
      this.map[keyString] = value;
    }

    put(key: K, value: V): void {
      const keyString = key.toString();
      if (!this.map.hasOwnProperty(keyString)) {
        this.keyList.push(key);
      }
      this.map[keyString] = value;
    }
  
    get(key: K): V | undefined {
      const keyString = key.toString();
      return this.map.hasOwnProperty(keyString) ? this.map[keyString] : undefined;
    }
  
    remove(key: K): void {
      const keyString = key.toString();
      if (this.map.hasOwnProperty(keyString)) {
        delete this.map[keyString];
        this.keyList = this.keyList.filter(k => k !== key);
      }
    }

    has(key: K): boolean {
      const keyString = key.toString();
      return this.map.hasOwnProperty(keyString);
    }
  
    getKeys(): K[] {
      return this.keyList;
    }
  
    values(): V[] {
      return this.keyList.map(key => this.map[key.toString()]);
    }
  
    entries(): [K, V][] {
      return this.keyList.map(key => [key, this.map[key.toString()]]);
    }

    getMap(){
      return this.map
    }
  }


