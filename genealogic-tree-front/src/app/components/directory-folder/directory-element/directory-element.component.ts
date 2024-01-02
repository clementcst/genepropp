import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-directory-element',
  templateUrl: './directory-element.component.html',
  styleUrls: ['./directory-element.component.css']
})
export class DirectoryElementComponent {
  @Input()
   
   user!:any;
   constructor() { }

   ngOnInit() : void{ }


}
