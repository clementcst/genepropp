import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-element',
  templateUrl: './nav-element.component.html',
  styleUrls: ['./nav-element.component.css']
})
export class NavElementComponent implements OnInit {
   @Input()
   
   page!:any;
   constructor() { }

   ngOnInit() : void{ }

}
