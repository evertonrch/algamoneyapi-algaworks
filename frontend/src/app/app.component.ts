import { Component } from '@angular/core';
import { TerminalService } from 'primeng/terminal';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(private terminalService: TerminalService) {
    this.terminalService.commandHandler.subscribe((command: string) => {
        let response = ""
        if (command === 'date'){
          response = new Date().toDateString()
        } else if (command === "hello") {
          response = "world!!!!"
        } else {
          response = 'Unknown command: ' + command
        } 
        this.terminalService.sendResponse(response);
    });
  }
}
