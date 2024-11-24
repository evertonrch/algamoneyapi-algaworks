import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {CardModule} from 'primeng/card'
import {AvatarModule} from 'primeng/avatar';
import {TerminalModule, TerminalService} from 'primeng/terminal';

@NgModule({
  declarations: [
    AppComponent  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CardModule,
    AvatarModule,
    TerminalModule
  ],
  providers: [TerminalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
