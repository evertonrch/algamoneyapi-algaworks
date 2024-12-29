import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-message',
  template: `
      <div *ngIf="temError()" class="p-message p-message-error">
        {{ text }}
      </div>
  `,
  styles: [`
      .p-message-error {
        margin: 0;
        margin-top: 4px;
        padding: 3px;
      }
  `]
})
export class MessageComponent {

  @Input() control: any
  @Input() error = ""
  @Input() text = ""

  temError(): boolean {
    return  (<FormControl> this.control).hasError(this.error as string) && this.control.dirty
  }

}
