import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-profil-inputs',
  templateUrl: './profil-inputs.component.html',
  styleUrls: ['./profil-inputs.component.css']
})
export class ProfilInputsComponent {
  @Input() input: any;
  @Output() valueChanged = new EventEmitter<any>();

  isCursorNotAllowed(): boolean {
    return this.input.disabled === true;
  }

  onValueChanged(event: any) {
    this.valueChanged.emit({ label: this.input.label, value: event.target.value });
  }
}
