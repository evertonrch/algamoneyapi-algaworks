import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-lancamentos-grid',
  templateUrl: './lancamentos-grid.component.html',
  styleUrls: ['./lancamentos-grid.component.css']
})
export class LancamentosGridComponent {

  @Input() lancamentos: any = []
  
  tipoValor(tipo: any): string {
    return tipo === 'DESPESA' ? 'red' : 'blue';
  }
}
